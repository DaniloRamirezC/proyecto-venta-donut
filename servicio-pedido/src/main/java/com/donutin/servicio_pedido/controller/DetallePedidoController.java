package com.donutin.servicio_pedido.controller;

import com.donutin.servicio_pedido.config.ErrorResponse;
import com.donutin.servicio_pedido.model.DetallePedido;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_pedido.service.GestionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/v1/pedidos/detalles")
@CrossOrigin(origins = "*")
public class DetallePedidoController 
{
    private final GestionService gestionService;
    public DetallePedidoController(GestionService gestionService)
    {
        this.gestionService = gestionService;
    }

    @GetMapping
    public List<DetallePedido> listar()
    {
        return gestionService.listarTodo();
    }
    @Operation(summary = "Obtener por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Detalle de pedido encontrado"),
        @ApiResponse(responseCode = "400", description = "No existe el detalle que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public DetallePedido verDetallePedido(@PathVariable Long id)
    {
        DetallePedido detalle = gestionService.obtenerDetallePedidoCompleto(id);
        if(detalle == null)
        {
            throw new RuntimeException("Detalle de pedido no encontrado");
        }
        return detalle;
    } 
    @PostMapping   
    public DetallePedido crearDetalle(@RequestBody DetallePedido detallePedido)
    {
        return gestionService.guardarDetalle(detallePedido);
    } 
}
