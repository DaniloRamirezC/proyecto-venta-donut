package com.donutin.servicio_pedido.controller;

import com.donutin.servicio_pedido.model.DetallePedido;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_pedido.service.GestionService;

@RestController
@RequestMapping("/api/v1/pedidos/detalles")
public class DetallePedidoController 
{
    @Autowired
    private GestionService gestionService;
    @GetMapping
    public List<DetallePedido> listar()
    {
        return gestionService.listarTodo();
    }
    @GetMapping("/{id}")
    public DetallePedido verDetallePedido(@PathVariable Long id)
    {
        return gestionService.obtenerDetallePedidoCompleto(id);
    } 
    @PostMapping   
    public DetallePedido crearDetalle(@RequestBody DetallePedido detallePedido)
    {
        return gestionService.guardarDetalle(detallePedido);
    } 
}
