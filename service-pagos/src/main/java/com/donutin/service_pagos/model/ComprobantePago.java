package com.donutin.service_pagos.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
@Entity
@Table(name = "comprobante_pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa el comprobante tributario y desglose de impuestos de un pago")
public class ComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del comprobante de pago", example = "1")
    private Long idComprobante;

    @NotBlank(message = "Detalle comprobante no puede estar vacío")
    @Column(nullable = false, length = 50)
    @Schema(description = "Descripción corta o tipo de comprobante emitido", example = "Boleta Electrónica")
    private String detalle;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    @Schema(description = "Monto neto calculado del servicio (Monto Bruto / 1.19). Se calcula automáticamente de forma interna", example = "10000", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer montoNeto;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    @Schema(description = "Monto correspondiente únicamente al impuesto del IVA (Monto Bruto - Monto Neto). Se calcula automáticamente de forma interna", example = "1900", accessMode = Schema.AccessMode.READ_ONLY)
    private Integer montoIva;
    
    @OneToOne
    @JoinColumn(name = "IdPago")
    @JsonBackReference
    @Schema(description = "Referencia inversa al pago original dueño de este comprobante")
    private Pago pago;

    @Transient
    @Schema(description = "Referencia al impuesto IVA (19%)", example = "1.19", accessMode = Schema.AccessMode.READ_ONLY)
    private Double IVA = 1.19;
}
