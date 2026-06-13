package com.donutin.service_empresa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_empresa.model.Empresa;
import com.donutin.service_empresa.service.EmpresaService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/empresa")
public class EmpresaController 
{
    @Autowired
    private EmpresaService empresaService;

    @GetMapping
    public List<Empresa> listarTodo() 
    {
        return empresaService.obtenerTodos();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id)
    {
        return empresaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping 
    public ResponseEntity<Empresa> crearEmpresa(@RequestBody Empresa empresa)
    {
        return ResponseEntity.ok(empresaService.guardarEmpresa(empresa));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id)
    {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
