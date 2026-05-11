package com.donutin.service_pagos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_pagos.model.ComprobantePago;
public interface ComprobantePagoRepository extends JpaRepository<ComprobantePago,Long> {
    
}
