package com.donutin.service_promociones.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "cupon")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa un cupón de descuento aplicable en el sistema")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del cupón", example = "1")
    private Long idCupon;

    @NotBlank(message = "El código no puede estar vacío")
    @Column(nullable = false, unique = true, length = 20)
    @Schema(description = "Código alfanumérico único para validar el descuento", example = "DONUT2026")
    private String codigo;

    @NotNull(message = "El porcentaje de descuento no puede ser nulo")
    @Min(value = 1, message = "El descuento mínimo es 1%")
    @Max(value = 100, message = "El descuento máximo no puede superar el 100%")
    @Column(nullable = false)
    @Schema(description = "Porcentaje de descuento entero que restará al total del pedido", example = "15")
    private Integer porcentajeDesc;

    @NotNull(message = "El estado no puede ser nulo")
    @Column(nullable = false)
    @Schema(description = "Indica si el cupón está activo o ya caducó/fue deshabilitado", example = "true")
    private Boolean estado;
}
