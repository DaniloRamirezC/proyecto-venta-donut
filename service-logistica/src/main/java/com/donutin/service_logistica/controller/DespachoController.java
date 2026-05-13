package com.donutin.service_logistica.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_logistica.model.Despacho;
import com.donutin.service_logistica.service.DespachoService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/despachos")
public class DespachoController {
    @Autowired
    private DespachoService despachoService;

    @PostMapping
    public Despacho crear(@RequestBody Despacho despacho){
        return despachoService.guardarDespacho(despacho);
    }

    @GetMapping("/{id}")
    public Despacho verDespacho(@PathVariable Long id){
        return despachoService.obtenerDespacho(id);
    }

    @GetMapping
    public List<Despacho> listarDespachos(){
        return despachoService.listarDespachos();
    }

    @PutMapping("/{id}/estado")
    public Despacho actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado){
        return despachoService.actualizarEstado(id, nuevoEstado);
    }
}
