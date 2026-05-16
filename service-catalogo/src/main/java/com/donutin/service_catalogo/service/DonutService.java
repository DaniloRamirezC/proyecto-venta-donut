package com.donutin.service_catalogo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.donutin.service_catalogo.model.Categoria;
import com.donutin.service_catalogo.model.Donut;
import com.donutin.service_catalogo.repository.CategoriaRepository;
import com.donutin.service_catalogo.repository.DonutRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class DonutService 
{
    @Autowired
    private DonutRepository donutRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public List<Donut> listarTodos()
    {
        return donutRepository.findAll();
    }
    public Optional<Donut> buscarPorId(Long id)
    {
        return donutRepository.findById(id);
    }
    @Transactional
    public Donut guardar(@Valid Donut donut)
    {
        if(donut.getCategoria()!=null && donut.getCategoria().getIdCategoria()!=null)
        {
            Categoria categoriaCompleta = categoriaRepository.findById(donut.getCategoria().getIdCategoria()).orElse(null);
            donut.setCategoria(categoriaCompleta);
        }
        return donutRepository.save(donut);
    }
    public void eliminar(Long id)
    {
        donutRepository.deleteById(id);
    }
    public List<Donut> buscarPorCategoria(Long idCategoria)
    {
        return donutRepository.buscarDonasPorCategoria(idCategoria);
    }
    public List<Object[]> conteoPorNombreCategoria()
    {
        return donutRepository.conteoPorNombreCategoria();
    }
    public List<Donut> buscarDonasPorStock(Integer stock)
    {
        return donutRepository.buscarDonasPorStock(stock);
    }
}
