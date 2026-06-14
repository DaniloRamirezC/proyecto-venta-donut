package com.donutin.service_reportes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_reportes.model.Reporte;
import java.time.LocalDate;


public interface ReporteRepository extends JpaRepository<Reporte, Long>
{
    Optional<Reporte> findByFecha(LocalDate fecha);
}
