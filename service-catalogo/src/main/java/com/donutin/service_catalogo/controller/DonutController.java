package com.donutin.service_catalogo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_catalogo.exception.ErrorResponse;
import com.donutin.service_catalogo.model.Donut;
import com.donutin.service_catalogo.service.DonutService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/donuts")
@Tag(name = "Donuts", description = "Operaciones relacionadas con la gestión de las donuts")
@CrossOrigin(origins = "*") //Permite que Swagger lo llame desde cualquier puerto
public class DonutController 
{
    private final DonutService donutService;

    public DonutController(DonutService donutService)
    {
        this.donutService = donutService;
    }
    
    @Operation(summary = "Obtener todas las donuts", description = "Retorna una lista completa de donuts registradas")
    @GetMapping
    public List<Donut> listar()
    {
        return donutService.listarTodos();
    }
    @Operation(summary = "Obtener por ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Donut encontrada"),
        @ApiResponse(responseCode = "404", description = "No existe la donut que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Donut> obtenerPorId(@PathVariable Long id)
    {
        return donutService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Donut no encontrada"));
    }
    @GetMapping("/categoria/{id}")
    public ResponseEntity<List<Donut>> buscarPorCategoria(@PathVariable Long id)
    {
        List<Donut> donut = donutService.buscarPorCategoria(id);
        if(donut.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(donut);
    }
    @GetMapping("/conteo-categoria")
    public ResponseEntity<List<Object[]>> conteoPorCategoria()
    {
        List<Object[]> contar = donutService.conteoPorNombreCategoria();
        if(contar.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(contar);
    }
    @GetMapping("/stock/{stock}")
    public ResponseEntity<List<Donut>> buscarDonaPorStock(@PathVariable Integer stock)
    {
        List<Donut> donaStock = donutService.buscarDonasPorStock(stock);
        if(donaStock.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(donaStock);
    }
    @Operation(summary = "Crear una nueva donut", description = "Guardar una donut en la base de datos bd_catalogo") 
    @PostMapping
    public ResponseEntity<Donut> crear(@Valid @RequestBody Donut donut)
    {
        Donut nuevo = donutService.guardar(donut);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        donutService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}