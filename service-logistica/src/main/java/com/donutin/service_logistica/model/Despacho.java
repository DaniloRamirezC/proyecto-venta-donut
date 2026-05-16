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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despacho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDespacho;

    @NotBlank(message = "Repartidor no puede estar vacío")
    @Column(nullable = false, length = 100)
    private String repartidor;

    @NotBlank(message = "Estado no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String estado;

    private Long pedidoId; //Referencia lógica al microservicio servicio-pedido

    private Long clienteId; //Referencia lógica al microservicio servicio-clientes

    @Transient
    private Object datosPedido;

    @Transient
    private Object datosCliente;
}
