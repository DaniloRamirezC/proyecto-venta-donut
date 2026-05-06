package com.donutin.service_catalogo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.donutin.service_catalogo.model.Donut;

@Repository
public interface DonutRepository extends JpaRepository<Donut, Long>
{
    Donut findByNombreDonut(String nombre);
    Donut findByNombreDonutIgnoreCase(String nombre);
    @Query("""
            SELECT d 
            FROM Donut d 
            WHERE d.categoria.idCategoria = :idCategoria
            """)
        List<Donut> buscarDonasPorCategoria(Long idCategoria);
    
    @Query("""
            SELECT d.Categoria.nombre as nomCategoria,
            COUNT(d) as cantidad
            FROM Donut d
            GROUP BY d.Categoria.nombre
            """)
        List<Object[]> conteoPorNombreCategoria();
    
    @Query("""
            SELECT d
            FROM Donut d
            WHERE d.stock = :stock
            """)
        List<Donut>buscarDonasPorStock(Integer stock);
}
