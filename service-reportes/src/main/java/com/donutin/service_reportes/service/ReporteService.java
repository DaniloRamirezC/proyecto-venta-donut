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

    try {
        List<Map<String, Object>> listaPedidos = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/pedidos/pedido") 
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (listaPedidos != null) {
            System.out.println("-> Se encontraron " + listaPedidos.size() + " pedidos totales en el sistema.");
            
            for (Map<String, Object> pedido : listaPedidos) {
                try {
                    Object idObj = pedido.get("idPedido");
                    if (idObj != null) {

                        String idPedidoStr = String.valueOf(idObj);
                        
                        Map<String, Object> despacho = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8085/api/v1/despachos/" + idPedidoStr)
                                .retrieve()
                                .bodyToMono(Map.class)
                                .block();

                        if (despacho != null) {
                            Object estadoLogistica = despacho.get("estado");
                            System.out.println("-> Pedido ID " + idPedidoStr + " tiene estado en logística: " + estadoLogistica);
                            
                            if (estadoLogistica != null && 
                                "entregado".equals(String.valueOf(estadoLogistica).trim().toLowerCase())) {
                                cantidadPedidosEntregados++;
                            }
                        } else {
                            System.out.println("Logística devolvió un cuerpo vacío para el pedido: " + idPedidoStr);
                        }
                    }
                } catch (Exception ex) {
                    System.err.println("-Error buscando despacho de un pedido: " + ex.getMessage());
                }
            }
        } else {
            System.out.println("El servicio de pedidos devolvió una lista vacía.");
        }
    } catch (Exception ex) {
        System.err.println("Error crítico al consultar el servicio de pedido " + ex.getMessage());
    }

    try {
        List<Map<String, Object>> listaPagos = webClientBuilder.build()
                .get()
                .uri("http://localhost:8084/api/v1/pagos") 
                .retrieve()
                .bodyToMono(List.class)
                .block();

        if (listaPagos != null) {
            for (Map<String, Object> pago : listaPagos) {
                Object estadoObj = pago.get("estadoTransaccion");
                
               
                boolean estaAprobado = false;
                if (estadoObj instanceof Boolean) {
                    estaAprobado = (Boolean) estadoObj;
                } else if (estadoObj != null) {
                    estaAprobado = Boolean.parseBoolean(String.valueOf(estadoObj));
                }

                if (estaAprobado) {
                    Object montoObj = pago.get("monto");
                    if (montoObj instanceof Number) {
                        // Tolerancia a tipos de datos numéricos (Integer, Double, Float) de Jackson
                        totalIngresos += Math.round(((Number) montoObj).doubleValue());
                    }
                }
            }
        }
    } catch (Exception ex) {
        System.err.println("Error al consultar el servicio de pagos: " + ex.getMessage());
    }

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
