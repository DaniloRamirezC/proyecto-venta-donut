package com.donutin.service_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_pagos.model.Pago;
import java.util.List;

public interface PagoRepository extends JpaRepository<Pago,Long>  
{
    List<Pago> findByPedidoId(Long pedidoId);
}
