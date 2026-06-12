package com.donutin.service_autenticacion.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_autenticacion.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario,Long>
{
    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
