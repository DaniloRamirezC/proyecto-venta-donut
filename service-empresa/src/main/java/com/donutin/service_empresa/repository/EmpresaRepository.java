package com.donutin.service_empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_empresa.model.Empresa;
import java.util.Optional;

public interface EmpresaRepository extends JpaRepository<Empresa,Long>
{
    Optional<Empresa> findByIdEmpresa(Long idEmpresa);
    Optional<Empresa> findByRutEmpresa(String rutEmpresa);
}
