package com.donutin.service_empresa.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_empresa.exception.ErrorResponse;
import com.donutin.service_empresa.model.Empresa;
import com.donutin.service_empresa.service.EmpresaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/empresas")
@Tag(name = "Empresas", description = "Operaciones relacionadas con la gestión de la empresa")
@CrossOrigin(origins = "*")
public class EmpresaController 
{
    private final EmpresaService empresaService;
    public EmpresaController(EmpresaService empresaService)
    {
        this.empresaService = empresaService;
    }
    @Operation(summary = "Obtener todas las empresas", description = "Retorna una lista completa de empresas registradas")
    @GetMapping
    public List<Empresa> listarTodo() 
    {
        return empresaService.obtenerTodos();
    }
    @Operation(summary = "Obtener por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Empresa encontrada"),
        @ApiResponse(responseCode = "400", description = "No existe la empresa que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Empresa> obtenerPorId(@PathVariable Long id)
    {
        return empresaService.obtenerPorId(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new RuntimeException("Empresa no encontrada"));
    }
    @Operation(summary = "Crear una nueva empresa", description = "Guarda la empresa en la base de datos bd_empresa")
    @PostMapping 
    public ResponseEntity<Empresa> crearEmpresa(@Valid @RequestBody Empresa empresa)
    {
        Empresa nuevaEmp = empresaService.guardarEmpresa(empresa);
        return new ResponseEntity<>(nuevaEmp, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpresa(@PathVariable Long id)
    {
        empresaService.eliminarEmpresa(id);
        return ResponseEntity.noContent().build();
    }
}
