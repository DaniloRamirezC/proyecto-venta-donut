package com.donutin.service_autenticacion.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_autenticacion.dto.AuthRequest;
import com.donutin.service_autenticacion.service.AuthService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticacion", description = "Endpoints para registro e inicio de sesión de usuarios")
public class AutenticacionController 
{
    private final AuthService authService;
    public AutenticacionController(AuthService authService)
    {
        this.authService = authService;
    }
    @Operation(summary = "Registrar nuevo usuario", description = "Guarda el usuario con contraseña encriptada")
    @PostMapping("/registrar")
    public ResponseEntity<String> registrarUsuario(@RequestBody AuthRequest request)
    {
        return ResponseEntity.ok(authService.registrar(request));
    }
    @Operation(summary = "Iniciar sesión", description = "Retorna el Token JWT si las credenciales son válidas")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest request)
    {
        try{
            String token = authService.login(request.getNombreUsuario(), request.getContrasena());
            return ResponseEntity.ok(token);
        }
        catch(RuntimeException ex ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }

}
