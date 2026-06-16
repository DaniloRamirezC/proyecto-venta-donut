package com.donutin.servicio_clientes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.donutin.servicio_clientes.model.Cliente;
import com.donutin.servicio_clientes.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
@Validated
public class ClienteService 
{
    private ClienteRepository clienteRepository;
    public ClienteService(ClienteRepository clienteRepository)
    {
        this.clienteRepository = clienteRepository;
    }

    public List<Cliente> listarTodos()
    {
        return clienteRepository.findAll();
    }
    public Optional<Cliente> buscarPorId(Long id)
    {
        return clienteRepository.findById(id);
    }
    @Transactional
    public Cliente guardar(@Valid Cliente cliente)
    {
        return clienteRepository.save(cliente);
    }
    public void eliminar(Long id)
    {
        clienteRepository.deleteById(id);
    }
    public List<Cliente> buscarPorNombre(String nombre)
    {
        return clienteRepository.buscarPorNombre(nombre);
    }
    public int totalClientes()
    {
        List<Cliente> listaClientes = clienteRepository.listarTodosLosClientes();
        return listaClientes.size();
    }
}
