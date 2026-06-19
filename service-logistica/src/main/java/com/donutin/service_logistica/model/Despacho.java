package com.donutin.service_logistica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa la logística de despacho de un pedido en el sistema")
public class Despacho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del despacho", example = "1")
    private Long idDespacho;

    @NotBlank(message = "Repartidor no puede estar vacío")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre completo del repartidor asignado", example = "Juan Pérez")
    private String repartidor;

    @NotBlank(message = "Estado no puede estar vacío")
    @Column(nullable = false, length = 50)
    @Schema(description = "Estado actual del envío (ej: EN_RUTA, ENTREGADO, PENDIENTE)", example = "EN_RUTA")
    private String estado;

    @Schema(description = "ID de referencia lógica correspondiente al microservicio de pedidos", example = "105")
    private Long pedidoId; //Referencia lógica al microservicio servicio-pedido

    @Schema(description = "ID de referencia lógica correspondiente al microservicio de clientes", example = "42")
    private Long clienteId; //Referencia lógica al microservicio servicio-clientes


    @Transient
    @Schema(description = "Objeto con la información detallada del pedido. Se carga en tiempo de ejecución de forma interna", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosPedido;

    @Transient
    @Schema(description = "Objeto con la información detallada del cliente. Se carga en tiempo de ejecución de forma interna", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosCliente;
}
