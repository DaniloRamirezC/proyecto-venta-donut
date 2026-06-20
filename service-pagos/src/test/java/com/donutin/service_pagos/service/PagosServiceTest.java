package com.donutin.service_pagos.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import com.donutin.service_pagos.model.Pago;
import com.donutin.service_pagos.model.PedidoDTO;
import com.donutin.service_pagos.repository.PagoRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("rawtypes") // Para eliminar las advertencias de RequestHeaders
class PagosServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private PagosService pagosService;

    @Test
    @SuppressWarnings("unchecked")
    void obtenerPagoPorIdCompletaTest() {
        // --- 1. PREPARACIÓN ---
        Long pagoId = 1L;
        Long pedidoId = 105L;

        // Instanciamos el Pago base simulado
        Pago pagoMock = new Pago();
        pagoMock.setIdPago(pagoId);
        pagoMock.setMonto(15990);
        pagoMock.setMetodoPago("TARJETA");
        pagoMock.setPedidoId(pedidoId);

        // Instanciamos el DTO del microservicio Pedidos
        PedidoDTO pedidoMock = new PedidoDTO();
        pedidoMock.setIdPedido(pedidoId);
        pedidoMock.setDescripcion("Caja de Donuts Especiales");
        pedidoMock.setTotal(15990.0);
        pedidoMock.setEstadoPedido("APROBADO");

        // Mock del repositorio local
        when(pagoRepository.findById(pagoId)).thenReturn(Optional.of(pagoMock));

        // Mocks de la cadena fluida de WebClient
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec);
        
        when(responseSpec.bodyToMono(Object.class)).thenReturn(Mono.just(pedidoMock));

        // --- 2. EJECUCIÓN ---
        Pago resultado = pagosService.obtenerPago(pagoId); 

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El pago no debe ser nulo");
        assertNotNull(resultado.getDatosPedido(), "Los datos del pedido deben estar presentes");

        PedidoDTO pedidoRetornado = (PedidoDTO) resultado.getDatosPedido();
        assertEquals("Caja de Donuts Especiales", pedidoRetornado.getDescripcion(), "La descripción del pedido debe coincidir");
        assertEquals(pedidoId, pedidoRetornado.getIdPedido());

        assertEquals(15990, resultado.getMonto());
        assertEquals("TARJETA", resultado.getMetodoPago());

        verify(pagoRepository, times(1)).findById(pagoId);
    }
}
