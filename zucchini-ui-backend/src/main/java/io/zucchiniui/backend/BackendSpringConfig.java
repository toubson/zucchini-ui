package io.zucchiniui.backend;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.setup.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackageClasses = BackendSpringConfig.class)
public class BackendSpringConfig {

    @Autowired
    private Environment dropwizardEnvironment;

    @Bean
    public ObjectMapper reportObjectMapper() {
        return dropwizardEnvironment.getObjectMapper()
            .copy()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    }

}
