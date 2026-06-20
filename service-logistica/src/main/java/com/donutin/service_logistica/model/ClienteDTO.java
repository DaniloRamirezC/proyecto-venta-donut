package com.donutin.service_logistica.model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String nombre;
    private String email;
    private String telefono;
}
