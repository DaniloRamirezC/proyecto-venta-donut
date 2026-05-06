package com.donutin.servicio_pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_pedido.model.Pedido;
import com.donutin.servicio_pedido.service.GestionService;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController 
{
    @Autowired
    private GestionService gestionService;

    @GetMapping 
    public List<Pedido> listar()
    {
        return gestionService.listarPedidos();
    }
    @PostMapping 
    public Pedido crearPedido(@RequestBody Pedido pedido)
    {
        return gestionService.guardarPedido(pedido);
    }
}
