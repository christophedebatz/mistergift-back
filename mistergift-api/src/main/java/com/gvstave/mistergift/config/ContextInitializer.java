package com.gvstave.mistergift.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;

import java.io.IOException;

public class ContextInitializer implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    /**
     * {@inheritDoc}
     */
    public void initialize(ConfigurableWebApplicationContext ctx) {
        ResourcePropertySource ps = null;
        try {
            ps = new ResourcePropertySource(new ClassPathResource("WEB-INF/mg-admin.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ctx.getEnvironment().getPropertySources().addFirst(ps);
    }
}