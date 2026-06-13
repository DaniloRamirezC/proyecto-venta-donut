package com.donutin.service_empresa.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donutin.service_empresa.model.Empresa;
import com.donutin.service_empresa.repository.EmpresaRepository;

@Service
public class EmpresaService 
{
    @Autowired
    private EmpresaRepository empresaRepository;

    public Optional<Empresa> obtenerPorId(Long id)
    {
        return empresaRepository.findById(id);
    }
    public List<Empresa> obtenerTodos()
    {
        return empresaRepository.findAll();
    }
    public Empresa guardarEmpresa(Empresa empresa)
    {
        if(empresa.getIdEmpresa()==null)
        {
            if(empresaRepository.findByRutEmpresa(empresa.getRutEmpresa()).isPresent())
                throw new RuntimeException("Ya existe una empresa con ese RUT registrado");
        }
        return empresaRepository.save(empresa);
    }
    public void eliminarEmpresa(Long id)
    {
        empresaRepository.deleteById(id);
    }

}
