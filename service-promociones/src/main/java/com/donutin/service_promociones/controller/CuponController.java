package com.donutin.service_promociones.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import com.donutin.service_promociones.config.ErrorResponse;
import com.donutin.service_promociones.model.Cupon;
import com.donutin.service_promociones.service.CuponService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/promociones")
@Tag(name = "Cupones de Promoción", description = "Endpoints autónomos para la creación y validación de cupones de descuento")
@CrossOrigin(origins = "*")
public class CuponController {

    private final CuponService cuponService;

    public CuponController(CuponService cuponService) {
        this.cuponService = cuponService;
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo cupón", description = "Registra un cupón de descuento en la base de datos db_cupon")
    public ResponseEntity<Cupon> crear(@Valid @RequestBody Cupon cupon) {
        Cupon nuevoCupon = cuponService.guardarCupon(cupon);
        return new ResponseEntity<>(nuevoCupon, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Listar todos los cupones", description = "Retorna una lista con la totalidad de cupones del sistema")
    public ResponseEntity<List<Cupon>> listar() {
        return ResponseEntity.ok(cuponService.listarCupones());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar cupón por ID", description = "Obtiene los detalles de un cupón usando su llave primaria")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cupón encontrado"),
        @ApiResponse(responseCode = "404", description = "ID de cupón inexistente", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Cupon> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuponService.obtenerPorId(id));
    }

    @GetMapping("/validar/{codigo}")
    @Operation(summary = "Validar cupón por Código string", description = "Endpoint estratégico para que el Frontend o las compras consulten si un código alfanumérico existe")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Código de cupón válido"),
        @ApiResponse(responseCode = "404", description = "Código de cupón inválido o inexistente", 
                     content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<Cupon> obtenerPorCodigo(@PathVariable String codigo) {
        return ResponseEntity.ok(cuponService.obtenerPorCodigo(codigo));
    }
}
