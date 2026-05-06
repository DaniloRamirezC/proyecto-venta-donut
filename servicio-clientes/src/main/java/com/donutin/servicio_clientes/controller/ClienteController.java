package com.donutin.servicio_clientes.controller;

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

import com.donutin.servicio_clientes.model.Cliente;
import com.donutin.servicio_clientes.service.ClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController 
{
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> listar()
    {
        return clienteService.listarTodos();
    }
    @GetMapping
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id)
    {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Cliente>> buscarPorNombre(@PathVariable String nombre)
    {
        List<Cliente> clienteNom = clienteService.buscarPorNombre(nombre);
        if(clienteNom.isEmpty())
        {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clienteNom);
    }
    @GetMapping("/total")
    public ResponseEntity<Integer> totalClientes()
    {
        int total = clienteService.totalClientes();
        return ResponseEntity.ok(total);
    }
    @PostMapping
    public ResponseEntity<Cliente> crear(@RequestBody Cliente cliente)
    {
        return ResponseEntity.ok(clienteService.guardar(cliente));
    }
    @DeleteMapping
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
