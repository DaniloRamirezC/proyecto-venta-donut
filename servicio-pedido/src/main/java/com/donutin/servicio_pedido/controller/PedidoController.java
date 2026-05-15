package com.donutin.servicio_pedido.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_pedido.model.Pedido;
import com.donutin.servicio_pedido.service.GestionService;

import jakarta.validation.Valid;

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

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> obtenerPorId(@PathVariable Long id)
    {
        Pedido pedido = gestionService.obtenerPedidoPorId(id);
        if(pedido!=null)
        {
            return ResponseEntity.ok(pedido);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping 
    public Pedido crearPedido(@Valid @RequestBody Pedido pedido)
    {
        return gestionService.guardarPedido(pedido);
    }
}
