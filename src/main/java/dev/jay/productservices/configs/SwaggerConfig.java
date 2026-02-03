package dev.jay.productservices.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 ======================================================
 Swagger Configuration
 ======================================================

 Customizes:
 - Title
 - Version
 - Description

 Shows professional API documentation.
*/
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Product Services API")
                                .version("1.0")
                                .description("Spring Boot Product Management System with Redis Cache, Soft Delete, Pagination, Reports & Scheduler")
                );
    }
}
