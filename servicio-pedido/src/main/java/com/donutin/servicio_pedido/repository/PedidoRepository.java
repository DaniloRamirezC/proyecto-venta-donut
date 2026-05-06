package com.donutin.servicio_pedido.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.servicio_pedido.model.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido,Long> 
{
    
}
