package com.donutin.servicio_clientes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.donutin.servicio_clientes.model.Cliente;
import com.donutin.servicio_clientes.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceTest 
{
    @Mock
    private ClienteRepository clienteRepository; //Simulamos el repositorio
    @InjectMocks
    private ClienteService clienteService; //Inyectamos el mock en el servicio real
    @Test
    @DisplayName("Debería guardar un cliente correctamente")
    void guardarClienteTest(){
        Cliente cliente = new Cliente();
        cliente.setNombreCliente("Juan");
        cliente.setRutCliente("12345678-9");

        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> {
            Cliente c = invocation.getArgument(0);
            c.setIdCliente(1L);
            return c;
        });

        Cliente resultado = clienteService.guardar(cliente);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdCliente());
        assertEquals("Juan",resultado.getNombreCliente());
        verify(clienteRepository, times(1)).save(cliente);
    }
}
