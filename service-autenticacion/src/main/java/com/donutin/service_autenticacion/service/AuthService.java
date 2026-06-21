package com.donutin.service_autenticacion.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.donutin.service_autenticacion.dto.AuthRequest;
import com.donutin.service_autenticacion.model.Rol;
import com.donutin.service_autenticacion.model.Usuario;
import com.donutin.service_autenticacion.repository.RolRepository;
import com.donutin.service_autenticacion.repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService 
{
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository,
                    RolRepository rolRepository,
                    JwtService jwtService,
                    PasswordEncoder passwordEncoder)
    {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public String registrar(AuthRequest request)
    {
        if(usuarioRepository.findByNombreUsuario(request.getNombreUsuario()).isPresent()){
            throw new RuntimeException("El nombre de usuario ya existe");
        }

        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombreUsuario(request.getNombreUsuario());
        nuevoUsuario.setCorreo(request.getCorreo());
        nuevoUsuario.setContrasena(passwordEncoder.encode(request.getContrasena()));

        if(request.getRoles() == null || request.getRoles().isEmpty()){
            Rol rolPorDefecto = rolRepository.findByNombreRol("CLIENTE")
                .orElseThrow(()-> new RuntimeException("Error: El rol por defecto no existe en la BD"));
            nuevoUsuario.getRoles().add(rolPorDefecto);       
        } else {
            for(String nombreRol : request.getRoles()){
                Rol rolEncontrado = rolRepository.findByNombreRol(nombreRol.toUpperCase())
                    .orElseThrow(()-> new RuntimeException("Error: El rol " + nombreRol + " no existe."));
                nuevoUsuario.getRoles().add(rolEncontrado);    
            }
        }
        usuarioRepository.save(nuevoUsuario);
        return "Usuario registrado con éxito";
    }
    @Transactional(readOnly = true)
    public String login(String username, String password)
    {
        Usuario user = usuarioRepository.findByNombreUsuario(username)
                .orElseThrow(()-> new RuntimeException("Credenciales inválidas"));
        
        if(!passwordEncoder.matches(password, user.getContrasena())){
            throw new RuntimeException("Credenciales inválidas");
        }
        List<String> roles = user.getRoles().stream()
                    .map(Rol::getNombreRol)
                    .collect(Collectors.toList());
        return jwtService.generarToken(username, roles);
    }
}
