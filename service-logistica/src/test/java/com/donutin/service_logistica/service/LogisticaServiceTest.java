package com.donutin.service_logistica.service;
import static org.junit.jupiter.api.Assertions.*;
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
import com.donutin.service_logistica.model.ClienteDTO;
import com.donutin.service_logistica.model.Despacho;
import com.donutin.service_logistica.model.PedidoDTO;
import com.donutin.service_logistica.repository.DespachoRepository;

@ExtendWith(MockitoExtension.class)
@SuppressWarnings("rawtypes") // Para eliminar las advertencias de RequestHeaders
class DespachoServiceTest {

    @Mock
    private DespachoRepository despachoRepository;

    @Mock
    private WebClient.Builder webClientBuilder;

    @InjectMocks
    private DespachoService despachoService; //

    @Test
    @SuppressWarnings("unchecked")
    void obtenerDespachoPorIdCompletaTest() {
        // --- 1. PREPARACIÓN ---
        Long despachoId = 1L;
        Long pedidoId = 105L;
        Long clientId = 42L;

        // Crear objeto Despacho base ficticio
        Despacho despachoMock = new Despacho();
        despachoMock.setIdDespacho(despachoId);
        despachoMock.setRepartidor("Carlos Donutero");
        despachoMock.setEstado("PENDIENTE");
        despachoMock.setPedidoId(pedidoId);
        despachoMock.setClienteId(clientId);

        // Instanciar DTOs simulados con datos explícitos de prueba
        PedidoDTO pedidoMock = new PedidoDTO();
        pedidoMock.setIdPedido(pedidoId);
        pedidoMock.setDescripcion("Caja de Donuts Surtidas x12");
        pedidoMock.setTotal(15990.0);
        pedidoMock.setEstadoPedido("PAGADO");

        ClienteDTO clienteMock = new ClienteDTO();
        clienteMock.setIdCliente(clientId);
        clienteMock.setNombre("Danilo Ramírez");
        clienteMock.setEmail("danilo@example.com");
        clienteMock.setTelefono("+56912345678");

        // Simular comportamiento del repositorio local
        when(despachoRepository.findById(despachoId)).thenReturn(Optional.of(despachoMock));

        // Mocks para simular la infraestructura de la cadena fluida de WebClient
        WebClient webClient = Mockito.mock(WebClient.class);
        WebClient.RequestHeadersUriSpec uriSpec = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        WebClient.RequestHeadersSpec headersSpec = Mockito.mock(WebClient.RequestHeadersSpec.class);
        WebClient.ResponseSpec responseSpec = Mockito.mock(WebClient.ResponseSpec.class);

        // Configuramos Mockito para soportar las dos llamadas consecutivas
        when(webClientBuilder.build()).thenReturn(webClient, webClient);
        when(webClient.get()).thenReturn(uriSpec, uriSpec);
        when(uriSpec.uri(anyString())).thenReturn(headersSpec, headersSpec);
        when(headersSpec.retrieve()).thenReturn(responseSpec, responseSpec);

        // Definimos las respuestas consecutivas: 1° llamada devuelve Pedido, 2° llamada devuelve Cliente
        when(responseSpec.bodyToMono(Object.class))
            .thenReturn(Mono.just(pedidoMock))
            .thenReturn(Mono.just(clienteMock));

        // --- 2. EJECUCIÓN ---
        Despacho resultado = despachoService.obtenerDespacho(despachoId);

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El despacho no debe ser nulo");

        assertNotNull(resultado.getDatosPedido(), "Los datos del pedido deben estar presentes");
        assertNotNull(resultado.getDatosCliente(), "Los datos del cliente deben estar presentes");

        PedidoDTO pedidoRetornado = (PedidoDTO) resultado.getDatosPedido();
        assertEquals("Caja de Donuts Surtidas x12", pedidoRetornado.getDescripcion(), "La descripción del pedido debe coincidir");
        assertEquals(pedidoId, pedidoRetornado.getIdPedido());

        ClienteDTO clienteRetornado = (ClienteDTO) resultado.getDatosCliente();
        assertEquals("Danilo Ramírez", clienteRetornado.getNombre(), "El nombre del cliente debe coincidir");
        assertEquals(clientId, clienteRetornado.getIdCliente());

        verify(despachoRepository, times(1)).findById(despachoId);
    }
}
