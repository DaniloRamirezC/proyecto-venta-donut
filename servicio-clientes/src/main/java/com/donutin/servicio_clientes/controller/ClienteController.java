package com.donutin.servicio_clientes.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.servicio_clientes.model.Cliente;
import com.donutin.servicio_clientes.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Operaciones relacionadas con la gestión de clientes")
@CrossOrigin(origins = "*")
public class ClienteController 
{
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService)
    {
        this.clienteService = clienteService;
    }
    @Operation(summary = "Obtener todos los clientes", description = "Retorna una lista completa de clientes registrados")
    @GetMapping
    public List<Cliente> listar()
    {
        return clienteService.listarTodos();
    }
    @Operation(summary = "Obtener por Id")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Paciente encontrado"),
        @ApiResponse(responseCode = "400", description = "No existe el cliente que busca", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtenerPorId(@PathVariable Long id)
    {
        return clienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
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
    @Operation(summary = "Crear un nuevo cliente", description = "Guarda un cliente en la base de datos bd_clientes")
    @PostMapping
    public ResponseEntity<Cliente> crear(@Valid @RequestBody Cliente cliente)
    {
        Cliente nuevo = clienteService.guardar(cliente);
        return new ResponseEntity<>(nuevo, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id)
    {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
