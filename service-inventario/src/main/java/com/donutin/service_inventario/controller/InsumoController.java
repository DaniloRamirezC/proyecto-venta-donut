package com.donutin.service_inventario.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.donutin.service_inventario.config.ErrorResponse;
import com.donutin.service_inventario.model.Insumo;
import com.donutin.service_inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/inventario/insumos")
@CrossOrigin(origins = "*")
@Tag(name = "2. Gestión de Insumos", description = "Endpoints para controlar el stock y costos de las materias primas")
public class InsumoController {

    private final InventarioService inventarioService;
    public InsumoController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo insumo", description = "Crea una materia prima vinculándola a un ID de proveedor existente")
    public ResponseEntity<Insumo> crear(@Valid @RequestBody Insumo i) {
        return new ResponseEntity<>(inventarioService.guardarInsumo(i), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar materias primas", description = "Retorna el catálogo completo de insumos con su stock actual")
    public ResponseEntity<List<Insumo>> listar() {
        return ResponseEntity.ok(inventarioService.listarInsumos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar insumo por ID", description = "Obtiene los datos de una materia prima e incluye a su proveedor")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Insumo encontrado con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID del insumo buscado no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Insumo> ver(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerInsumoPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un insumo por ID", description = "Elimina una materia prima del inventario mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Insumo eliminado con éxito"),
        @ApiResponse(responseCode = "404", description = "El insumo a eliminar no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarInsumo(id);
        return ResponseEntity.noContent().build();
    }
}
