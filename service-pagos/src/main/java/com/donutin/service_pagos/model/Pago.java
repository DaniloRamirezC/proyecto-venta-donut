package com.donutin.service_pagos.model;

import java.util.List;

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
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPago;

    @NotBlank(message = "Metodo de pago no puede estar vacío")
    @Column(nullable = false, length = 25)
    private String metodoPago;

    @NotNull(message = "La cantidad no puede ser nulo")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Column(nullable = false)
    private Integer monto;

    @NotBlank(message = "Estado transaccion no puede estar vacío")
    @Column(nullable = false, length = 25)
    private String estadoTransaccion;
    
    private Long pedidoId; //Referencia lógica al microservicio servicio-pedido

    @Transient
    private Object datosPedido; 

    @OneToOne(mappedBy = "pago", cascade = CascadeType.ALL)
    private List<ComprobantePago> comprobantePago;

    
}
