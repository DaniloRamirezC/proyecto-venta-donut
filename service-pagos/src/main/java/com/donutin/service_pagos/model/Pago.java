package com.donutin.service_pagos.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa la información general de una transacción de pago en el sistema")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del pago", example = "1")
    private Long idPago;

    @NotBlank(message = "Metodo de pago no puede estar vacío")
    @Column(nullable = false, length = 25)
    @Schema(description = "Medio utilizado para procesar la transacción (ej: WEBPAY, TRANSFERENCIA, EFECTIVO)", example = "WEBPAY")
    private String metodoPago;

    @NotNull(message = "La cantidad no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    @Schema(description = "Monto total bruto pagado por el cliente", example = "11900")
    private Integer monto;

    @NotNull(message = "Estado transaccion no puede estar vacío")
    @Column(nullable = false)
    @Schema(description = "Indica si la pasarela de pago aprobó o rechazó la transacción", example = "true")
    private Boolean estadoTransaccion;

    @Schema(description = "ID de referencia lógica correspondiente al microservicio de pedidos", example = "501")
    private Long pedidoId; //Referencia lógica al microservicio servicio-pedido

    @Transient
    @Schema(description = "Objeto con los detalles complementarios del pedido asociado (No mapeado en BD)", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosPedido; 

    @OneToOne(mappedBy = "pago", cascade = CascadeType.ALL)
    @JsonManagedReference
    @Schema(description = "Comprobante asociado a este pago")
    private ComprobantePago comprobantePago;

    
}
