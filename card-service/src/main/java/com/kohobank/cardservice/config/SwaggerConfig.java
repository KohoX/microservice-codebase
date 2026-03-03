package com.kohobank.cardservice.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    /**
     * Access swagger: http://localhost:8082/swagger-ui.html
     */
    @Bean
    public OpenAPI rewardsApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("Card-Service Microservice REST API Documentation")
                        .description("APIs for the card-service microservices.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Koho")
                                .name("kohobank@email.com")
                                .url("https://www.kohobank.org")
                        )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.koho.org")
                        )
                ).externalDocs(new ExternalDocumentation()
                        .description("Card microservice REST API Documentation")
                        .url("https://www.eazybytes.com/swagger-ui.html")
                );
    }
}
