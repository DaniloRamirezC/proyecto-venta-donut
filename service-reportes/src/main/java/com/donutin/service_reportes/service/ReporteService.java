package com.donutin.service_reportes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.service_reportes.model.Reporte;
import com.donutin.service_reportes.repository.ReporteRepository;

import lombok.NonNull;

@Service
@SuppressWarnings("unchecked")
public class ReporteService 
{
    private final ReporteRepository reporteRepository;
    private final WebClient.Builder webClientBuilder;
    public ReporteService(ReporteRepository reporteRepository, WebClient.Builder webClientBuilder)
    {
        this.reporteRepository = reporteRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public List<Reporte> listarHistorico()
    {
        return reporteRepository.findAll();
    }

    public Reporte crearReporteDia(LocalDate fecha) {
    int cantidadPedidosEntregados = 0;
    long totalIngresos = 0L;

    // 1. CONSUMIR TODOS LOS PEDIDOS SIN FILTRAR POR FECHA
    try {
        Object respuestaPedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/pedidos/pedido") 
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        if (respuestaPedido instanceof List) {
            List<Map<String, Object>> listaPedidos = (List<Map<String, Object>>) respuestaPedido;
            
            for (Map<String, Object> pedido : listaPedidos) {
                try {
                    Object idObj = pedido.get("idPedido");
                    if (idObj != null) {
                        Long idPedido = ((Number) idObj).longValue();
                        
                        // Consultamos a logística para ver si este pedido ya fue entregado
                        Object respuestaLogistica = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8085/api/v1/despachos/" + idPedido)
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block();

                        if (respuestaLogistica instanceof Map) {
                            Map<String, Object> despacho = (Map<String, Object>) respuestaLogistica;
                            if ("Entregado".equalsIgnoreCase(String.valueOf(despacho.get("estado")))) {
                                cantidadPedidosEntregados++;
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("No se pudo verificar logística para un pedido individual.");
                }
            }
        }
    } catch (Exception ex) {
        System.out.println("Error al consultar service-pedido: " + ex.getMessage());
    }

    // 2. CONSUMIR TODOS LOS PAGOS GENERALES
    try {
        Object respuestaPagos = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/pagos") // Trae todo el historial de la BD
                .retrieve()
                .bodyToMono(Object.class)
                .block();

        if (respuestaPagos instanceof List) {
            List<Map<String, Object>> listaPagos = (List<Map<String, Object>>) respuestaPagos;
            for (Map<String, Object> pago : listaPagos) {
                Object estado = pago.get("estadoTransaccion");
                // Como no hay fecha, sumamos ABSOLUTAMENTE TODO lo que esté aprobado en el sistema
                if (estado != null && "Aprobado".equalsIgnoreCase(String.valueOf(estado))) {
                    Object montoObj = pago.get("monto");
                    if (montoObj instanceof Number) {
                        totalIngresos += ((Number) montoObj).longValue();
                    }
                }
            }
        }
    } catch (Exception ex) {
        System.out.println("Error al consultar el servicio de pagos: " + ex.getMessage());
    }

    // 3. GUARDAR EL INSTANTÁNEO ACUMULADO
    // Nota: Guardará los totales históricos asociados a la fecha del día en que se ejecutó
    Reporte reporteDiario = reporteRepository.findByFecha(fecha)
            .orElse(new Reporte());

    reporteDiario.setFecha(fecha);
    reporteDiario.setTotalPedidos(cantidadPedidosEntregados);
    reporteDiario.setTotalRecaudado(totalIngresos);

    return reporteRepository.save(reporteDiario);
}

    public Optional<Reporte> obtenerReportePorId(@NonNull Long id)
    {
        return reporteRepository.findById(id);
    }

    public Optional<Reporte> buscarPorFecha(LocalDate fecha)
    {
        return reporteRepository.findByFecha(fecha);
    }
}
