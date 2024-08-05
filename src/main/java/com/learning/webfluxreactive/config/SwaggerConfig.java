package com.learning.webfluxreactive.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
                .title("Reactive Webflux API")
                .version("1.0")
                .description("Reactive Webflux API with Spring Boot security");
        return new OpenAPI()
                .info(info);
    }
}
