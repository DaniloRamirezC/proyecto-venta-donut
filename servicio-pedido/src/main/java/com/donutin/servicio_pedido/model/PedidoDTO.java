package com.donutin.servicio_pedido.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO 
{
    private Long id;
    private String nombreDonut;
}
