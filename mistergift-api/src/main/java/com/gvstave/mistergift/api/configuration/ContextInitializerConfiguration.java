package com.gvstave.mistergift.api.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;

/**
 * The Spring context initializer.
 */
@Configuration
@EnableWebMvc
public class ContextInitializerConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    @Override
    public void configureDefaultServletHandling(
        DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * {@inheritDoc}
     */
    public void initialize(ConfigurableWebApplicationContext ctx) {
        ResourcePropertySource ps = null;

        try {
            ps = new ResourcePropertySource(new ClassPathResource("WEB-INF/" + ctx.getEnvironment().getProperty("env", "dev") + ".properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ctx.getEnvironment()
                .getPropertySources()
                .addFirst(ps);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("**/**")
                .addResourceLocations("classpath:/WEB-INF/resources/");

    }

}