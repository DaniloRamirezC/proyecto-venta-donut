package com.donutin.service_pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
    public Pago crear(@RequestBody Pago pago){
        return pagosService.guardarPago(pago);
    }

    @GetMapping("/{id}")
    public Pago verPago(@PathVariable Long id){
        return pagosService.obtenerPago(id);
    }

    @GetMapping
    public List<Pago> listar(){
        return pagosService.listarPagos();
    }

}
