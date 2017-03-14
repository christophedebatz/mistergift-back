package com.gvstave.mistergift.data.configuration;

import com.gvstave.mistergift.data.service.DataServices;
import com.gvstave.mistergift.service.Services;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import javax.inject.Inject;

/**
 * .
 */
@Configuration
@ComponentScan(basePackageClasses = { DataJpaConfiguration.class, Services.class, DataServices.class })
@Import({ DataRedisConfiguration.class, DataJpaConfiguration.class, DataMongoConfiguration.class })
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
        return new StandardServletMultipartResolver();
    }

}