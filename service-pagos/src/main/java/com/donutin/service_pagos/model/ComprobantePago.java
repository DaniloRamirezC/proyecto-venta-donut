package com.donutin.service_pagos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
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

    private Double IVA = 1.19;
    
    @OneToOne
    @JoinColumn(name = "IdPago")
    private Pago pago;

}
