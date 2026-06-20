package com.donutin.service_inventario.controller;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import com.donutin.service_inventario.config.ErrorResponse;
import com.donutin.service_inventario.model.Receta;
import com.donutin.service_inventario.service.InventarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/inventario/recetas")
@CrossOrigin(origins = "*")
@Tag(name = "3. Fórmulas y Recetas (WebClient)", description = "Endpoints para enlazar materias primas locales con Donuts externas")
public class RecetaController {

    private final InventarioService inventarioService;

    public RecetaController(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @PostMapping
    @Operation(summary = "Asociar insumo a una Donut", description = "Registra la cantidad exacta de un insumo que requiere una Donut específica")
    public ResponseEntity<Receta> crear(@Valid @RequestBody Receta r) {
        return new ResponseEntity<>(inventarioService.guardarReceta(r), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todas las recetas", description = "Retorna el listado de asociaciones de ingredientes en el sistema")
    public ResponseEntity<List<Receta>> listar() {
        return ResponseEntity.ok(inventarioService.listarRecetas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener receta detallada por ID", description = "Busca la fórmula y consume 'service-donut' vía WebClient para inyectar los datos de la Donut en tiempo real")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Receta obtenida con éxito"),
        @ApiResponse(responseCode = "404", description = "El ID de la receta solicitada no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Receta> ver(@PathVariable Long id) {
        return ResponseEntity.ok(inventarioService.obtenerRecetaPorId(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ingrediente de una receta", description = "Remueve la asociación del insumo con la Donut eliminando el registro")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Registro de receta eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "La receta especificada no existe", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        inventarioService.eliminarReceta(id);
        return ResponseEntity.noContent().build();
    }
}