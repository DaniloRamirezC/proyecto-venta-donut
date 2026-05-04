package com.donutin.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_catalogo.model.Donut;
import com.donutin.service_catalogo.service.DonutService;

@RestController
@RequestMapping("/donuts")
public class DonutController 
{
    @Autowired
    private DonutService donutService;

    @GetMapping
    public List<Donut> listar()
    {
        return donutService.listarTodos();
    }
    @GetMapping 
    public ResponseEntity<Donut> obtenerPorId(@PathVariable Long id)
    {
        return donutService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Donut> crear(@RequestBody Donut donut)
    {
        return ResponseEntity.ok(donutService.guardar(donut));
    }
    @DeleteMapping
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        donutService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}