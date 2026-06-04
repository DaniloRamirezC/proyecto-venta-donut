package com.donutin.service_catalogo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.donutin.service_catalogo.model.Categoria;
import com.donutin.service_catalogo.repository.CategoriaRepository;


@RestController
@RequestMapping("/api/v1/tipos")
public class CategoriaController 
{
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public List<Categoria> listar()
    {
        return categoriaRepository.findAll();
    }
    @PostMapping 
    public Categoria crear(@RequestBody Categoria tipo)
    {
        return categoriaRepository.save(tipo);
    }
}