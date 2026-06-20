package com.donutin.service_inventario.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;


@Entity
@Table(name = "insumo")
@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Schema(description = "Modelo que representa una materia prima disponible en bodega")
public class Insumo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del insumo", example = "1")
    private Long idInsumo;

    @NotBlank(message = "El nombre del insumo es obligatorio")
    @Column(nullable = false, length = 50)
    @Schema(description = "Nombre o descripción de la materia prima", example = "Azúcar Flor")
    private String nomInsumo;

    @NotNull
    @Min(0)
    @Schema(description = "Cantidad de existencias en el inventario actual", example = "50")
    private Integer stockActual;

    @NotNull
    @Min(0)
    @Schema(description = "Costo unitario del insumo", example = "1200")
    private Integer precioCompra;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProveedor", nullable = false)
    @Schema(description = "Datos completos del proveedor que abastece este insumo")
    private Proveedor proveedor;
}
