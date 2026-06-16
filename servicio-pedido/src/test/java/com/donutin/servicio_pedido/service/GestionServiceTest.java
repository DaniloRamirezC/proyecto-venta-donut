package com.donutin.servicio_pedido.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.servicio_pedido.model.DetallePedido;
import com.donutin.servicio_pedido.model.PedidoDTO;
import com.donutin.servicio_pedido.repository.DetallePedidoRepository;

import reactor.core.publisher.Mono;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("rawtypes") // Para eliminar las advertencias de RequestHeaders
public class GestionServiceTest 
{
    // Haremos una prueba unitaria con Pedido
    @Mock
    private DetallePedidoRepository detallePedidoRepository;
    @Mock
    private WebClient.Builder webClientBuilder;
    @InjectMocks
    private GestionService gestionService;
    @Test
    @SuppressWarnings("unchecked") // Para eliminar advertencia de uriSpec
    void obtenerPedidoCompletoTest(){
        Long pedidoDetalleId = 1L;
        Long donutId = 100L;

        DetallePedido pedidoMock = new DetallePedido();
        pedidoMock.setIdDetalle(pedidoDetalleId);
        pedidoMock.setDonutId(donutId);

        // Para PedidoDTO usaremos la variable donutMock
        PedidoDTO donutMock = new PedidoDTO();
        donutMock.setId(donutId);
        donutMock.setNombreDonut("Donut Crema");

        String empresaMock = "Donutin Feliz";

        //Mock del Repositorio
        when(detallePedidoRepository.findById(pedidoDetalleId)).thenReturn(Optional.of(pedidoMock));

        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);

        when(responseSpec.bodyToMono(Object.class))
                .thenReturn(Mono.just(donutMock)) // Primera llamada: Donut
                .thenReturn(Mono.just(empresaMock)); // Segunda llamada: Empresa

        //Ejecución
        DetallePedido resultado = gestionService.obtenerDetallePedidoCompleto(pedidoDetalleId);

        assertNotNull(resultado, "El detalle del pedido no puede estar nulo");
        assertNotNull(resultado.getDatosDonuts(), "Los datos de la donut deben estar presentes");

        PedidoDTO datosRetorno = (PedidoDTO) resultado.getDatosDonuts();
        //Verificar datos del donut
        assertEquals("Donut Crema", datosRetorno.getNombreDonut());
        assertEquals(donutId, datosRetorno.getId());

        //Verificar datos de la empresa
        assertNotNull(resultado.getDatosEmpresa(), "Los datos de la empresa deben estar presentes");
        assertEquals("Donutin Feliz", resultado.getDatosEmpresa());

        //Verificamos que se llamó al repositorio
        verify(detallePedidoRepository, times(1)).findById(pedidoDetalleId);
    }
}
