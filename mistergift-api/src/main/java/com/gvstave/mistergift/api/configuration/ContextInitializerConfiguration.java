package com.gvstave.mistergift.api.configuration;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * The Spring context initializer.
 */
@Configuration
public class ContextInitializerConfiguration extends WebMvcConfigurerAdapter implements ApplicationContextInitializer<ConfigurableWebApplicationContext> {

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureDefaultServletHandling(
        DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("MM-dd-yyyy hh:mm:ss"));
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
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