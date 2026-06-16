package com.donutin.service_reportes.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reporte_ventas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reporte 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Schema(description = "Fecha del reporte (formato AAAA-MM-DD)", example = "2025-04-30")
    private LocalDate fecha;

    @Schema(description = "Total de pedidos del día")
    private Integer totalPedidos;
    @Schema(description = "Total de ingresos recaudados del día")
    private Long totalRecaudado;
}
