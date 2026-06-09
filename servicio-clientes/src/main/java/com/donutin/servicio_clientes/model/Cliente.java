package com.donutin.servicio_clientes.model;

import io.swagger.v3.oas.annotations.media.Schema;
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

@Entity
@Table(name = "clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo que representa a un cliente en el sistema")
public class Cliente 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único autoincremental", example = "1")
    private Long idCliente;
    
    @NotBlank(message = "Rut del cliente no puede estar vacío")
    @Column(nullable = false, unique = true)
    @Schema(description = "RUT del cliente con guión", example = "12345678-9")
    private String rutCliente;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 100)
    @Schema(description = "Nombre completo del cliente", example = "Juan Pedro")
    private String nombreCliente;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false, length = 100)
    @Schema(description = "Apellidos del cliente", example = "Pérez Cotapos")
    private String apellidoCliente;

    @NotBlank(message = "El email no puede estar vacío")
    @Column(nullable = false, length = 100)
    @Schema(description = "Email del cliente", example = "jperezcotapos@example.com")
    private String email;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false, length = 150)
    @Schema(description = "Dirección particular del cliente", example = "Avenida Las Lilas 123")
    private String direccion;
}
