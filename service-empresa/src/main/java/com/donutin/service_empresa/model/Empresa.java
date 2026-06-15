package com.donutin.service_empresa.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa a una empresa en el sistema")
public class Empresa 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    private Long idEmpresa;
    @Column(nullable = false, unique = true)
    @NotBlank(message = "El RUT es obligatorio")
    @Size(min = 9, max = 12, message = "Debe tener entre 9 y 12 caracteres")
    @Schema(description = "RUT de la empresa con guión y sin puntos", example = "73987654-3", requiredMode = Schema.RequiredMode.REQUIRED)
    private String rutEmpresa;

    @NotBlank(message = "La razón social no puede estar vacía")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre oficial de la empresa", example = "Comercializadora La Atalaya S.A")
    private String razonSocial;

    @NotBlank(message = "El giro no puede estar vacío")
    @Column(nullable = false, length = 50)
    @Schema(description = "Giro de la empresa", example = "Distribuidora de alimentos")
    private String giro;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false, length = 100)
    @Schema(description = "Dirección de la empresa", example = "Avenida Siempreviva 742")
    private String direccionEmpresa;
}
