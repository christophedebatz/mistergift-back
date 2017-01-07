package com.gvstave.mistergift.api.configuration;

import com.gvstave.mistergift.api.Api;
import com.gvstave.mistergift.provider.Providers;
import com.gvstave.mistergift.service.Services;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * The Spring context initializer.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackageClasses = { Api.class, ApiConfiguration.class, Providers.class, Services.class})
@PropertySource("classpath:/WEB-INF/${server.role}.properties")
public class ApiWebConfiguration {

    @Bean
    public ApplicationContextInitializer contextInitializer() {
        return new ContextInitializerConfiguration();
    }

    @Bean
    public SwaggerConfig swaggerConfig() {
        return new SwaggerConfig();
    }


}