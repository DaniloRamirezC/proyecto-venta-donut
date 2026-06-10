package com.donutin.servicio_clientes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig 
{
    @Bean
    public OpenAPI costumOpenApi()
    {
        return new OpenAPI()
                .info(new Info()
                        .title("API Donutin - Servicio de Clientes")
                        .version("1.0")
                        .description("Documentación centralizada de Sabores Felices Donutin"));
    }
}
