package com.donutin.service_inventario.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.donutin.service_inventario.config.ErrorResponse;
import com.donutin.service_inventario.model.Proveedor;
import com.donutin.service_inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/inventario/proveedores")
@CrossOrigin(origins = "*")
@Tag(name = "1. Gestión de Proveedores", description = "Endpoints para administrar las empresas proveedoras de materias primas")
public class ProveedorController {

    private final InventarioService inventarioService;
    public ProveedorController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    @Operation(summary = "Registrar un proveedor", description = "Guarda una nueva empresa proveedora en la base de datos local")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Proveedor creado exitosamente")
    })
    public ResponseEntity<Proveedor> crear(@Valid @RequestBody Proveedor p) {
        return new ResponseEntity<>(inventarioService.guardarProveedor(p), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar proveedores", description = "Retorna una lista con la totalidad de los proveedores registrados")
    public ResponseEntity<List<Proveedor>> listar() {
        return ResponseEntity.ok(inventarioService.listarProveedores());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar proveedor por ID", description = "Obtiene los detalles completos de un proveedor mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Proveedor encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del proveedor no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Proveedor> ver(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerProveedorPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un proveedor por ID", description = "Elimina físicamente un proveedor si no tiene insumos asociados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Proveedor eliminado correctamente (No Content)"),
        @ApiResponse(responseCode = "404", description = "El proveedor a eliminar no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarProveedor(id);
        return ResponseEntity.noContent().build();
    }
}
