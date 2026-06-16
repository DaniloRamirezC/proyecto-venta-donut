package com.donutin.service_reportes.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_reportes.config.ErrorResponse;
import com.donutin.service_reportes.model.Reporte;
import com.donutin.service_reportes.service.ReporteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/reportes")
@CrossOrigin(origins = "*")
@Tag(name = "Reportes de venta", description = "Operaciones relacionadas con la gestión de los reportes")
public class ReporteController 
{
    private final ReporteService reporteService;
    public ReporteController(ReporteService reporteService)
    {
        this.reporteService = reporteService;
    }
    @Operation(summary = "Crear un nuevo reporte",description = "Guarda un reporte en la base de datos bd_reportes")
    @PostMapping("/crear")
    public ResponseEntity<Reporte> crearReporte(@RequestBody Reporte solicitud)
    {
        LocalDate fechaReporte = solicitud.getFecha();
        Reporte reporteCreado = reporteService.crearReporteDia(fechaReporte);
        return new ResponseEntity<>(reporteCreado, HttpStatus.CREATED);
    }
    
    @GetMapping 
    public ResponseEntity<List<Reporte>> obtenerHistorial()
    {
        List<Reporte> historial = reporteService.listarHistorico();
        return ResponseEntity.ok(historial);
    }
    @Operation(summary = "Obtener reporte por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reporte encontrado"),
        @ApiResponse(responseCode = "400", description = "No se encontró el reporte que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Reporte> obtenerReporteId(@PathVariable Long id)
    {
        return reporteService.obtenerReportePorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> new RuntimeException("Reporte no encontrado"));
    }
}
