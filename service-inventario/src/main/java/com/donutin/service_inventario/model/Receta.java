package com.donutin.service_inventario.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "receta")
@Data 
@AllArgsConstructor 
@NoArgsConstructor
@Schema(description = "Modelo que define los ingredientes necesarios para la preparación de una Donut")
public class Receta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la fórmula o ingrediente de la receta", example = "1")
    private Long idReceta;

    @NotNull
    @Min(0)
    @Schema(description = "Monto exacto de materia prima requerida", example = "250.5")
    private Double cantidadNeta;

    @NotBlank(message = "La unidad de medida es obligatoria")
    @Column(nullable = false, length = 50)
    @Schema(description = "Métrica utilizada para el pesaje", example = "gramos")
    private String unidadMedida;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idInsumo", nullable = false)
    @Schema(description = "Materia prima asociada de la base de datos local")
    private Insumo insumo;

    @NotNull(message = "El ID de la Donut asociada es obligatorio")
    @Schema(description = "ID de la Donut (Referencia lógica al microservicio service-donut)", example = "4")
    private Long idDonut; 

    @Transient
    @Schema(description = "Datos detallados de la Donut. Se cargan en tiempo de ejecución vía WebClient", accessMode = Schema.AccessMode.READ_ONLY)
    private Object datosDonut;
}
