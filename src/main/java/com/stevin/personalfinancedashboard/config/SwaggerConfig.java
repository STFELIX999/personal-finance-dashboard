package com.stevin.personalfinancedashboard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI personalFinanceOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Personal Finance Dashboard API")
                                .description("REST API documentation for the Personal Finance Dashboard Project.")
                                .version("1.0")
                                .contact(
                                        new Contact()
                                                .name("Stevin Felix")
                                                .email("stevin.felix9@gmail.com")
                                )
                );
    }
}