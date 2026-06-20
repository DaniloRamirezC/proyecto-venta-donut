package com.donutin.service_pagos.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
    private Long idPedido;
    private String descripcion;
    private Double total;
    private String estadoPedido;
}
