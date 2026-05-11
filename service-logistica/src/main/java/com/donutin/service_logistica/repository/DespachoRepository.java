package com.donutin.service_logistica.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_logistica.model.Despacho;
public interface DespachoRepository extends JpaRepository<Despacho,Long> 
{
    List<Despacho> findByPedidoId(Long pedidoId);
}
