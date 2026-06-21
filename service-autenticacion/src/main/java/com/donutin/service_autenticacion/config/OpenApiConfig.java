package com.donutin.service_autenticacion.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig 
{
    @Bean
    public OpenAPI customOpenApi(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Donutin - Servicio de Autenticación")
                        .version("1.0")
                        .description("Documentación encargada de la gestión de usuarios, roles y contraseñas"));
    }
}
