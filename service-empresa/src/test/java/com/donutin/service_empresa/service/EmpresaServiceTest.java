package com.donutin.service_empresa.service;

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

import com.donutin.service_empresa.model.Empresa;
import com.donutin.service_empresa.repository.EmpresaRepository;

@ExtendWith(MockitoExtension.class)
public class EmpresaServiceTest 
{
    @Mock
    private EmpresaRepository empresaRepository;
    @InjectMocks
    private EmpresaService empresaService;
    @Test
    @DisplayName("Debería guardar una empresa correctamente")

    void guardarEmpresaTest(){
        Empresa empresa = new Empresa();
        empresa.setRazonSocial("Panadería y Cafetería");
        empresa.setRutEmpresa("75123456-8");

        when(empresaRepository.save(any(Empresa.class))).thenAnswer(invocation ->{
            Empresa em = invocation.getArgument(0);
            em.setIdEmpresa(1L);
            return em;
        });

        Empresa resultado = empresaService.guardarEmpresa(empresa);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getIdEmpresa());
        assertEquals("Panadería y Cafetería", resultado.getRazonSocial());
        verify(empresaRepository, times(1)).save(empresa);
    }
}
