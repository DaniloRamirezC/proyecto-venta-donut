package com.donutin.servicio_pedido.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.servicio_pedido.model.DetallePedido;

public interface DetallePedidoRepository extends JpaRepository<DetallePedido,Long>
{
    List<DetallePedido> findByIdDonut(Long idDonut);
}
