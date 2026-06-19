package com.donutin.service_pagos.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.donutin.service_pagos.config.ErrorResponse;
import com.donutin.service_pagos.model.ComprobantePago;
import com.donutin.service_pagos.service.PagosService;

@RestController
@RequestMapping("/api/v1/pagos/comprobantes")
@Tag(name = "Comprobantes de Pago", description = "Operaciones de consulta y emisión de documentos tributarios (desglose de Netos e IVA)")
@CrossOrigin(origins = "*")
public class ComprobantePagoController {

    private final PagosService pagosService;

    ComprobantePagoController(PagosService pagosService) {
        this.pagosService = pagosService;
    }

    @Operation(summary = "Emitir un nuevo comprobante", description = "Crea un documento directamente asociado a un ID de pago existente")
    @PostMapping
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comprobante creado con éxito"),
        @ApiResponse(
            responseCode = "404", 
            description = "No se pudo crear el comprobante porque el ID del Pago asociado no existe", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<ComprobantePago> crearComprobante(@Valid @RequestBody ComprobantePago comprobantePago) {
        ComprobantePago nuevoComprobante = pagosService.guardarComprobantePago(comprobantePago);
        return new ResponseEntity<>(nuevoComprobante, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar todos los comprobantes emitidos", description = "Retorna la lista completa de boletas o facturas con desglose impositivo")
    @GetMapping
    public ResponseEntity<List<ComprobantePago>> listar() {
        List<ComprobantePago> comprobantes = pagosService.listarComprobantePagos();
        return ResponseEntity.ok(comprobantes);
    }
}
