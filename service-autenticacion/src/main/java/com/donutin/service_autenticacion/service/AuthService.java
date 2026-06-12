package com.donutin.service_autenticacion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.donutin.service_autenticacion.model.Rol;
import com.donutin.service_autenticacion.model.Usuario;
import com.donutin.service_autenticacion.repository.UsuarioRepository;

@Service
public class AuthService 
{
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                    JwtService jwtService,
                    PasswordEncoder passwordEncoder)
    {
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    public String registrar(Usuario usuario)
    {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        usuarioRepository.save(usuario);
        return "Usuario registrado";
    }
    public String login(String username, String password)
    {
    System.out.println("Intentando inicio de sesión para: " + username);
    Usuario user = usuarioRepository.findByNombreUsuario(username)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    System.out.println("Usuario encontrado en la base de datos: " + user.getNombreUsuario());

    if(passwordEncoder.matches(password, user.getContrasena()))
    {
        List<String> roles = user.getRoles().stream()
                    .map(Rol::getNombreRol)
                    .collect(Collectors.toList());
        return jwtService.generarToken(username, roles);
    }
    throw new RuntimeException("Credenciales inválidas");
    }
}
