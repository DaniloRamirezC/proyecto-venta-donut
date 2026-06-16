package com.donutin.servicio_pedido.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_pedido.config.ErrorResponse;
import com.donutin.servicio_pedido.model.Pedido;
import com.donutin.servicio_pedido.service.GestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/pedidos")
@CrossOrigin(origins = "*")
public class PedidoController 
{
    private final GestionService gestionService;
    public PedidoController(GestionService gestionService)
    {
        this.gestionService = gestionService;
    }

    @GetMapping 
    public List<Pedido> listar()
    {
        return gestionService.listarPedidos();
    }

    @Operation(summary = "Obtener por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "400", description = "No existe el pedido que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id)
    {
        Pedido pedido = gestionService.obtenerPedidoPorId(id);
        if(pedido==null)
        {
            throw new RuntimeException("Pedido no encontrado");
        }
        return ResponseEntity.ok(pedido);
    }

    @PostMapping 
    public Pedido crearPedido(@Valid @RequestBody Pedido pedido)
    {
        return gestionService.guardarPedido(pedido);
    }
}
