package com.donutin.service_pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_pagos.model.Pago;
import com.donutin.service_pagos.service.PagosService;

@RestController
@RequestMapping("/api/v1/pagos")
public class PagoController {
    @Autowired
    private PagosService pagosService;
    
    @PostMapping
    public ResponseEntity<Pago> crear(@RequestBody Pago pago){
        Pago nuevoPago = pagosService.guardarPago(pago);
        return new ResponseEntity<>(nuevoPago, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> verPago(@PathVariable Long id){
        Pago pago = pagosService.obtenerPago(id);
        return ResponseEntity.ok(pago);
    }

    @GetMapping
    public ResponseEntity<List<Pago>> listar(){
        List<Pago> pagos = pagosService.listarPagos();
        return ResponseEntity.ok(pagos);
    }

}
