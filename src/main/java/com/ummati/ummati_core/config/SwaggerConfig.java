package com.ummati.ummati_core.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Volunteer Event Service API")
                        .version("1.0")
                        .description("API documentation for Volunteer Event Service")
                        .contact(new Contact().name("Masdoua Manil").email("manil.masdoua@gmail.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("*")
                .pathsToMatch("/api/**")
                .build();
    }

}
