package com.donutin.service_reportes.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig 
{
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("API Donutin - Sistema de Gestión de Reportes")
                        .version("1.0")
                        .description("Documentación centralizada de Reportes de Donutin"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor a través de Gateway")
                ));
    }

}
