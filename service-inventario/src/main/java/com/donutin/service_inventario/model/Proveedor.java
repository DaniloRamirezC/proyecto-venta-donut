package com.donutin.service_inventario.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "proveedor")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa a un proveedor externo de materias primas")
public class Proveedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental del proveedor", example = "1")
    private Long idProveedor;

    @NotBlank(message = "El RUT es obligatorio")
    @Column(nullable = false, unique = true, length = 12)
    @Schema(description = "RUT o identificación tributaria de la empresa", example = "12345678-9")
    private String rutProveedor;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false, length = 60)
    @Schema(description = "Nombre o razón social de la empresa proveedora", example = "Distribuidora de Harinas S.A.")
    private String nomProveedor;

    @Column(length = 9)
    @Schema(description = "Teléfono de contacto del proveedor", example = "987654321")
    private String telefono;
}
