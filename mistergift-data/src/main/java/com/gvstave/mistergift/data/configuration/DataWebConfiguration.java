package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.Data;
import com.gvstave.mistergift.service.Services;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.inject.Inject;

/**
 * .
 */
@Configuration
@ComponentScan(basePackageClasses = { Data.class, Services.class })
@PropertySource("classpath:/WEB-INF/${server.role}.properties")
public class DataWebConfiguration {

    @Inject
    private Environment env;

    @Bean
    public PasswordEncoder passwordEncoder() {
         return new BCryptPasswordEncoder(11);
    }

    @Bean
    public MultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }

}