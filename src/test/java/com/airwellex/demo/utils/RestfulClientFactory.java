package com.airwellex.demo.utils;

import com.airwellex.demo.utils.fastjson.FastJsonFeature;

import javax.net.ssl.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Created by houjiagang on 2018/10/30.
 */
public class RestfulClientFactory {
    private static Client client;

    public static Client getClient() {
        try {
            if (client == null) {
                client = ClientBuilder.newBuilder()
                        .register(FastJsonFeature.class)
                        .build();
            }
            return client;
        } catch (Exception e) {
            throw new RuntimeException("Oops...");
        }
    }

    public static Client getIgnoreSSLClient() {

        TrustManager[] trustMgr = new TrustManager[]{new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
                // TODO Auto-generated method stub

            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1)
                    throws CertificateException {
                // TODO Auto-generated method stub

            }
        }};

        SSLContext context = null;
        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustMgr, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        Client client = ClientBuilder.newBuilder().sslContext(context)
                .hostnameVerifier(new HostnameVerifier() {

                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        // TODO Auto-generated method stub
                        return true;
                    }
                }).build();

        client.register(FastJsonFeature.class);
        return client;

    }

}
