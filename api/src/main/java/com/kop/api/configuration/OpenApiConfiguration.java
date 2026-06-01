package com.kop.api.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfiguration {
    
    @Value("${server.port:8081}")
    private String serverPort;
    
    @Bean
    public OpenAPI moneyTrackerOpenAPI() {
        final String securitySchemeName = "X-API-Key";
        
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("API Key authentication")))
                .info(new Info()
                        .title("Money Tracker API")
                        .description("RESTful API for Money Tracker - Personal Finance Management System")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Money Tracker Team")
                                .email("support@moneytracker.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server().url("http://localhost:" + serverPort).description("Development Server"),
                        new Server().url("https://api.moneytracker.com").description("Production Server")
                ));
    }
}