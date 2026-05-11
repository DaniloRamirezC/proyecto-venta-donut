package com.donutin.service_pagos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_pagos.model.ComprobantePago;
import com.donutin.service_pagos.service.PagosService;

@RestController
@RequestMapping("/api/v1/pagos/comprobantes")
public class ComprobantePagoController {

    @Autowired
    private PagosService pagosService;

    @PostMapping
    public ComprobantePago crearComprobante(@RequestBody ComprobantePago comprobantePago){
        return pagosService.guardarComprobantePago(comprobantePago);
    }

    @GetMapping
    public List<ComprobantePago> listar(){
        return pagosService.listarComprobantePagos();
    }
}
