package com.donutin.service_pagos.config;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.List;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Donutin API Servicio de Pagos")
                        .version("1.0")
                        .description("Documentación centralizada en los pagos y comprobantes de pago"))
                .servers(List.of(
                        new Server().url("http://localhost:8080").description("Servidor a través del API Gateway")
                ));
    }
}
