package com.donutin.service_reportes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_reportes.model.Reporte;
import com.donutin.service_reportes.service.ReporteService;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController 
{
    private final ReporteService reporteService;
    public ReporteController(ReporteService reporteService)
    {
        this.reporteService = reporteService;
    }

    @PostMapping("/publicar")
    public ResponseEntity<Reporte> publicarRendimiento(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha)
    {
        Reporte reporteFinal =reporteService.reporteRendimientoDia(fecha);
        return ResponseEntity.ok(reporteFinal);
    }
    
    @GetMapping 
    public ResponseEntity<List<Reporte>> obtenerHistorial()
    {
        List<Reporte> historial = reporteService.listarHistorico();
        return ResponseEntity.ok(historial);
    }
}
