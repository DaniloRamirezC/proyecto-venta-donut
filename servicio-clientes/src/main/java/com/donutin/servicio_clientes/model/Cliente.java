package com.donutin.servicio_clientes.model;

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
public class Cliente 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    
    @NotBlank(message = "Rut del cliente no puede estar vacío")
    @Column(nullable = false, unique = true)
    private String rutCliente;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String nombreCliente;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String apellidoCliente;

    @NotBlank(message = "El email no puede estar vacío")
    @Column(nullable = false, length = 50)
    private String email;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(nullable = false, length = 50)
    private String direccion;
}
