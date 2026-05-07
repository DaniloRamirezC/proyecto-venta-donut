package com.donutin.servicio_clientes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.donutin.servicio_clientes.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>
{
    Cliente findByRutCliente(String rut);

    @Query("""
            SELECT c
            FROM Cliente c
            WHERE c.nombreCliente = :nombre
            """)
    List<Cliente> buscarPorNombre(String nombre);
    @Query("""
            SELECT c
            FROM Cliente c
            """)
    List<Cliente> listarTodosLosClientes();
    
}
