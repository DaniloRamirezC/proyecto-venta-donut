package com.donutin.service_autenticacion.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest 
{
    private String nombreUsuario;
    private String contrasena;
    private String correo;

    //Para que Jackson pueda leer el ["CLIENTE"] de Postman
    private Set<String> roles;
}
