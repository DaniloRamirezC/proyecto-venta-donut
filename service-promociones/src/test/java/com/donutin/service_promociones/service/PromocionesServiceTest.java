package com.donutin.service_promociones.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.donutin.service_promociones.model.Cupon;
import com.donutin.service_promociones.repository.CuponRepository;

@ExtendWith(MockitoExtension.class)
class PromocionesServiceTest {

    @Mock
    private CuponRepository cuponRepository;

    @InjectMocks
    private CuponService cuponService;

    @Test
    void obtenerPorIdExitoTest() {
        // --- 1. PREPARACIÓN ---
        Long cuponId = 1L;

        Cupon cuponMock = new Cupon();
        cuponMock.setIdCupon(cuponId);
        cuponMock.setCodigo("DONUT2026");
        cuponMock.setPorcentajeDesc(15);
        cuponMock.setEstado(true);

        // Simulamos que el repositorio encuentra el cupón por ID
        when(cuponRepository.findById(cuponId)).thenReturn(Optional.of(cuponMock));

        // --- 2. EJECUCIÓN ---
        Cupon resultado = cuponService.obtenerPorId(cuponId); //

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado, "El cupón no debe ser nulo");
        assertEquals("DONUT2026", resultado.getCodigo()); //
        assertEquals(15, resultado.getPorcentajeDesc());
        assertTrue(resultado.getEstado());

        verify(cuponRepository, times(1)).findById(cuponId);
    }

    @Test
    void obtenerPorIdInexistenteTest() {
        // --- 1. PREPARACIÓN ---
        Long idInexistente = 99L;

        when(cuponRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // --- 2. EJECUCIÓN Y 3. VERIFICACIÓN ---
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cuponService.obtenerPorId(idInexistente);
        });

        assertEquals("Cupón no encontrado con el ID: " + idInexistente, exception.getMessage());

        verify(cuponRepository, times(1)).findById(idInexistente);
    }

    @Test
    void obtenerPorCodigoExitoTest() {
        // --- 1. PREPARACIÓN ---
        String codigoValido = "DONUT2026";

        Cupon cuponMock = new Cupon(); //
        cuponMock.setIdCupon(1L);
        cuponMock.setCodigo(codigoValido);
        cuponMock.setPorcentajeDesc(15);
        cuponMock.setEstado(true);

        when(cuponRepository.findByCodigo(codigoValido)).thenReturn(Optional.of(cuponMock));

        // --- 2. EJECUCIÓN ---
        Cupon resultado = cuponService.obtenerPorCodigo(codigoValido); //

        // --- 3. VERIFICACIÓN ---
        assertNotNull(resultado);
        assertEquals(codigoValido, resultado.getCodigo());
        assertEquals(15, resultado.getPorcentajeDesc());

        verify(cuponRepository, times(1)).findByCodigo(codigoValido);
    }

    @Test
    void obtenerPorCodigoInexistenteTest() {
        // --- 1. PREPARACIÓN ---
        String codigoFalso = "CUPON_FALSO";

        when(cuponRepository.findByCodigo(codigoFalso)).thenReturn(Optional.empty()); //

        // --- 2. EJECUCIÓN Y 3. VERIFICACIÓN ---
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            cuponService.obtenerPorCodigo(codigoFalso);
        });

        assertEquals("El código de cupón '" + codigoFalso + "' no es válido o no existe", exception.getMessage());

        verify(cuponRepository, times(1)).findByCodigo(codigoFalso);
    }
}