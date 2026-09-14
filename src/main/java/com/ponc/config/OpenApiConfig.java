package com.ponc.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI celugramaOpenAPI(){
        return new OpenAPI().info(new Info().title("Celugrama MCI API").description("API para la gestion de C'elulas, "+
        "asistencia y miembros de Célula MCI").version("0.0.1"));
    }
}
