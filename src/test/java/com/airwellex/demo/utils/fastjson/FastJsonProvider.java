package com.airwellex.demo.utils.fastjson;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;


import com.google.common.io.CharStreams;
import org.apache.commons.lang3.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
/**
 * Created by houjiagang on 2018/10/30.
 */
@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
@Consumes({MediaType.APPLICATION_JSON, MediaType.WILDCARD})
public class FastJsonProvider  implements MessageBodyReader<Object>, MessageBodyWriter<Object> {
    private static final String CHARSET = "charset";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String JSON_TYPE = "json";
    private static final String PLUS_JSON_TYPE = "+json";

    @Override
    public boolean isReadable(Class<?> type,
                              Type genericType,
                              Annotation[] annotations,
                              MediaType mediaType) {
        return supportsMediaType(mediaType);
    }

    @Override
    public Object readFrom(Class<Object> type,
                           Type genericType,
                           Annotation[] annotations,
                           MediaType mediaType,
                           MultivaluedMap<String, String> httpHeaders,
                           InputStream entityStream) throws IOException, WebApplicationException {
        try (InputStreamReader reader = new InputStreamReader(entityStream, getCharset(mediaType))) {
            String jsonString = CharStreams.toString(reader);
            if (StringUtils.isEmpty(jsonString)) {
                return null;
            }

            return JSON.parseObject(jsonString, type);
        }
    }

    @Override
    public boolean isWriteable(Class<?> type,
                               Type genericType,
                               Annotation[] annotations,
                               MediaType mediaType) {
        return supportsMediaType(mediaType);
    }

    protected boolean supportsMediaType(MediaType mediaType) {
        if (mediaType == null) {
            return true;
        }

        String subtype = mediaType.getSubtype();
        return subtype.equals(JSON_TYPE) || subtype.endsWith(PLUS_JSON_TYPE);
    }

    protected String getCharset(MediaType mediaType) {
        Map<String, String> mediaTypeParameters = null;
        String charSet = DEFAULT_CHARSET;

        if (mediaType != null) {
            mediaTypeParameters = mediaType.getParameters();
        }

        if (mediaTypeParameters != null && mediaTypeParameters.containsKey(CHARSET)) {
            charSet = mediaTypeParameters.get(CHARSET);
        }

        return charSet;
    }

    @Override
    public void writeTo(Object object,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        try (SerializeWriter writer = new SerializeWriter()) {
            JSONSerializer serializer = new JSONSerializer(writer);
            serializer.write(object);
            String jsonString = writer.toString();

            if (StringUtils.isNotEmpty(jsonString)) {
                entityStream.write(jsonString.getBytes(getCharset(mediaType)));
            }
        }
    }

    @Override
    public long getSize(Object object,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType) {
        return -1;
    }
}
