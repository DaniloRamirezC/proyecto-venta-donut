package com.donutin.service_autenticacion.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_autenticacion.model.Rol;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol,Long>
{
    Optional<Rol> findByNombreRol(String nombreRol);
}
