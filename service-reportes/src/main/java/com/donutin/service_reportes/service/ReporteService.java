package com.donutin.service_reportes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.service_reportes.model.Reporte;
import com.donutin.service_reportes.repository.ReporteRepository;

@Service
@SuppressWarnings("unchecked")
public class ReporteService 
{
    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    public List<Reporte> listarHistorico()
    {
        return reporteRepository.findAll();
    }

    public Reporte reporteRendimientoDia(LocalDate fecha)
    {
        int cantidadPedidosEntregados = 0;
        long totalIngresos=0L;

        List<Map<String, Object>> listaPedidos = null; 
        try{
            Object respuestaPedido = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8083/api/v1/pedidos?fecha=" + fecha)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            if(respuestaPedido instanceof List)
            {
                listaPedidos = (List<Map<String, Object>>) respuestaPedido;
            }
        } catch (Exception ex){
            System.out.println("Error al consultar pedido: Pedido no disponible");
        }
        if(listaPedidos!=null)
        {
            for(Map<String, Object> pedido : listaPedidos){
                try{
                    Object pedidoObj = pedido.get("idPedido");
                    if(pedidoObj!=null)
                    {
                        Long idPedido = ((Number) pedidoObj).longValue();
                        Object respuestaLogistica = webClientBuilder.build()
                                .get()
                                .uri("http://localhost:8085/api/v1/logistica/pedido" +idPedido)
                                .retrieve()
                                .bodyToMono(Object.class)
                                .block();
                        if(respuestaLogistica instanceof Map)
                        {
                            Map<String, Object> datosLogistica = (Map<String, Object>) respuestaLogistica;
                            if("Entregado".equalsIgnoreCase(String.valueOf(datosLogistica.get("estado"))))
                            {
                                cantidadPedidosEntregados++;
                            }
                        }
                    }
                } catch (Exception ex) {
                    System.out.println("No se pudo verificar el estado en logística para el pedido");
                }
            }
        }
        try{
            Object respuestaPagos = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8084/api/v1/pagos?fecha=" +fecha)
                    .retrieve()
                    .bodyToMono(Object.class)
                    .block();
            if(respuestaPagos instanceof List)
            {
                List<Map<String, Object>> listaPagos = (List<Map<String, Object>>) respuestaPagos;
                for(Map<String, Object> pago: listaPagos)
                {
                    if("Aprobado".equalsIgnoreCase(String.valueOf(pago.get("estadoTransaccion"))))
                    {
                        Object montoObj = pago.get("monto");
                        if(montoObj instanceof Number)
                        {
                            totalIngresos += ((Number) montoObj).longValue();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Error: Servicio de pagos no disponible");
        }
        Reporte reporteDiario = reporteRepository.findByFecha(fecha)
                .orElse(new Reporte());
        reporteDiario.setFecha(fecha);
        reporteDiario.setTotalPedidos(cantidadPedidosEntregados);
        reporteDiario.setTotalRecaudado(totalIngresos);

        return reporteRepository.save(reporteDiario);
    }
}
