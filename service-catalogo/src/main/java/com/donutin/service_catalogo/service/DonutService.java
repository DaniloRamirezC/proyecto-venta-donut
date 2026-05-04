package com.donutin.service_catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.donutin.service_catalogo.model.Donut;
import com.donutin.service_catalogo.repository.DonutRepository;

import jakarta.transaction.Transactional;

@Service
public class DonutService 
{
    @Autowired
    private DonutRepository donutRepository;
    public List<Donut> listarTodos()
    {
        return donutRepository.findAll();
    }
    public Optional<Donut> buscarPorId(Long id)
    {
        return donutRepository.findById(id);
    }
    @Transactional
    public Donut guardar(Donut donut)
    {
        if(donut.getPrecioUnitario()==null || donut.getPrecioUnitario()<=0)
        {
            throw new IllegalArgumentException("El precio de la dona debe ser mayor a cero");
        }
        if(donut.getStock()==null || donut.getStock()<0)
        {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        if(donut.getNombreDonut()==null || donut.getNombreDonut().isEmpty())
        {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        return donutRepository.save(donut);
    }
    public void eliminar(Long id)
    {
        donutRepository.deleteById(id);
    }
}
