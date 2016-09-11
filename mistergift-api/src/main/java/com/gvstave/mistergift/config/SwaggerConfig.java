package com.gvstave.mistergift.config;

import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The main Swagger documentation configuration.
 */
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Returns the Swagger documentation object (referenced by a Docket object).
     *
     * @return The Docket object.
     */
    @Bean
    public Docket createSwagger() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo());
    }

    /**
     * Returns the API information before documentation generation.
     *
     * @return The API info.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Mistergift API")
                .description("Back API to serve mistergift front-end.")
                .license("Gvstave ActionEventHelper.")
                .version("1.0")
                .contact("christophe.db@gmail.com")
                .build();
    }

}