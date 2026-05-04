package com.donutin.service_catalogo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.donutin.service_catalogo.model.Categoria;


public interface CategoriaRepository extends JpaRepository<Categoria, Long>
{
    Categoria findByNombre(String nombre);
    Categoria findByNombreIgnoreCase(String nombre);
}
