package com.gvstave.mistergift.service.configuration;

import com.gvstave.mistergift.service.Services;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

/**
 * .
 */
@Configuration
@ComponentScan(basePackageClasses = Services.class)
public class ServiceConfiguration {

    /**
     *
     * @return
     */
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource resourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
        bundle.setBasename("i18n.message");
        bundle.setDefaultEncoding("utf-8");
        return bundle;
    }

}
