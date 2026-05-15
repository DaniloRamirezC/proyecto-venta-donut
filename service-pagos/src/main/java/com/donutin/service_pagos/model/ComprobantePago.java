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
@Entity
@Table(name = "comprobante_pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComprobantePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComprobante;

    @NotBlank(message = "Detalle comprobante no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String detalle;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer montoNeto;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer montoIva;
    
    @OneToOne
    @JoinColumn(name = "IdPago")
    @JsonBackReference
    private Pago pago;

    @Transient
    private Double IVA = 1.19;
}
