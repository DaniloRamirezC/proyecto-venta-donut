package com.donutin.service_pagos.controller;

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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.donutin.service_pagos.config.ErrorResponse;
import com.donutin.service_pagos.model.Pago;
import com.donutin.service_pagos.service.PagosService;

@RestController
@RequestMapping("/api/v1/pagos")
@Tag(name = "Pagos", description = "Operaciones relacionadas con el procesamiento de transacciones financieras y pasarelas de pago")
@CrossOrigin(origins = "*")
public class PagoController {
    private final PagosService pagosService;

    PagoController(PagosService pagosService) {
        this.pagosService = pagosService;
    }
    
    @Operation(summary = "Registrar un nuevo pago", description = "Recibe una transacción y guarda el registro con su comprobante")
    @PostMapping
    public ResponseEntity<Pago> crear(@RequestBody Pago pago){
        Pago nuevoPago = pagosService.guardarPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener detalles de un pago por ID", description = "Busca en la base de datos un registro de pago específico y lo retorna enriquecido con sus datos de pedido")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pago encontrado con éxito"),
        @ApiResponse(
            responseCode = "404", 
            description = "No existe el pago que busca", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<Pago> verPago(@PathVariable Long id) {
        Pago pago = pagosService.obtenerPago(id);
        if (pago == null) {
            throw new RuntimeException("Pago no encontrado con el ID: " + id);
        }
        return ResponseEntity.ok(pago);
    }

    @Operation(summary = "Listar todos los pagos", description = "Retorna el historial completo de transacciones registradas en el sistema")
    @GetMapping
    public ResponseEntity<List<Pago>> listar(){
        List<Pago> pagos = pagosService.listarPagos();
        return ResponseEntity.ok(pagos);
    }

}
