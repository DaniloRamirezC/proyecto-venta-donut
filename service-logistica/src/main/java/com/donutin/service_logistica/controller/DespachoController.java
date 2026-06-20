package com.donutin.service_logistica.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import com.donutin.service_logistica.config.ErrorResponse;
import com.donutin.service_logistica.model.Despacho;
import com.donutin.service_logistica.service.DespachoService;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/despachos")
@Tag(name = "Despachos", description = "Operaciones relacionadas con la logística y control de estados de envíos")
@CrossOrigin(origins = "*")
public class DespachoController {
    private final DespachoService despachoService;

    DespachoController(DespachoService despachoService) {
        this.despachoService = despachoService;
    }

    @Operation(summary = "Crear un nuevo despacho", description = "Registra un envío en el sistema de logística asociado a un pedido")
    @PostMapping
    public ResponseEntity<Despacho> crear(@Valid @RequestBody Despacho despacho) {
        Despacho nuevoDespacho = despachoService.guardarDespacho(despacho);
        return new ResponseEntity<>(nuevoDespacho, HttpStatus.CREATED);
    }

    @Operation(summary = "Obtener un despacho por ID", description = "Retorna los datos de logística de un envío específico junto con sus referencias")
    @GetMapping("/{id}")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Despacho encontrado con éxito"),
        @ApiResponse(
            responseCode = "404", 
            description = "No existe el despacho que busca", 
            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
        )
    })
    public ResponseEntity<Despacho> verDespacho(@PathVariable Long id) {
        // Asumiendo que tu service retorna un objeto y queremos validar su existencia para lanzar la excepción
        Despacho despacho = despachoService.obtenerDespacho(id);
        if (despacho == null) {
            throw new RuntimeException("Despacho no encontrado con el ID: " + id);
        }
        return ResponseEntity.ok(despacho);
    }

    @Operation(summary = "Listar todos los despachos", description = "Retorna una lista completa con la bitácora de todos los envíos registrados")
    @GetMapping
    public ResponseEntity<List<Despacho>> listarDespachos() {
        List<Despacho> despachos = despachoService.listarDespachos();
        return ResponseEntity.ok(despachos);
    }

    @Operation(summary = "Actualizar el estado del despacho", description = "Modifica la fase de entrega del paquete (ej: EN_RUTA, ENTREGADO)")
    @PutMapping("/{id}/estado")
    public ResponseEntity<Despacho> actualizarEstado(@PathVariable Long id, @RequestParam String nuevoEstado) {
        Despacho despachoActualizado = despachoService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.ok(despachoActualizado);
    }
}
