package com.airwellex.demo.utils.fastjson;

import org.glassfish.jersey.CommonProperties;
import org.glassfish.jersey.internal.InternalProperties;
import org.glassfish.jersey.internal.util.PropertiesHelper;

import javax.ws.rs.Priorities;
import javax.ws.rs.core.Configuration;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

/**
 * Created by houjiagang on 2018/10/30.
 */
public class FastJsonFeature implements Feature {
    private static final String JSON_FEATURE = FastJsonFeature.class.getSimpleName();

    @Override
    public boolean configure(final FeatureContext context) {
        final Configuration config = context.getConfiguration();

        final String jsonFeature = CommonProperties.getValue(
                config.getProperties(),
                config.getRuntimeType(),
                InternalProperties.JSON_FEATURE, JSON_FEATURE,
                String.class);

        // Other JSON providers registered.
        if (!JSON_FEATURE.equalsIgnoreCase(jsonFeature)) {
            return false;
        }

        // Disable other JSON providers.
        context.property(
                PropertiesHelper.getPropertyNameForRuntime(
                        InternalProperties.JSON_FEATURE,
                        config.getRuntimeType()),
                JSON_FEATURE);

        context.register(FastJsonProvider.class, Priorities.USER);

        return true;
    }
}
