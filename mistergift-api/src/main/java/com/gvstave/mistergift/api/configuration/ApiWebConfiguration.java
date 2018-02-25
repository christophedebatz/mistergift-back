package com.gvstave.mistergift.api.configuration;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.gvstave.mistergift.api.Api;
import com.gvstave.mistergift.data.configuration.DataWebConfiguration;
import com.gvstave.mistergift.service.Services;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * The Spring context initializer.
 */
@Configuration
@ComponentScan(basePackageClasses = { Api.class, ApiConfiguration.class, Services.class, DataWebConfiguration.class })
public class ApiWebConfiguration {

    @Bean
    public ApplicationContextInitializer contextInitializer() {
        return new ContextInitializerConfiguration();
    }

    /**
     * Jackson builder.
     *
     * @return the jackson 2 object mapper builder
     */
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        final Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.serializationInclusion(JsonInclude.Include.NON_EMPTY);
        builder.propertyNamingStrategy(PropertyNamingStrategy.LOWER_CAMEL_CASE);
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
        dateFormat.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));
        builder.indentOutput(true).dateFormat(dateFormat);
        return builder;
    }
}