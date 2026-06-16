package com.donutin.servicio_pedido.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.servicio_pedido.model.DetallePedido;
import com.donutin.servicio_pedido.model.Pedido;
import com.donutin.servicio_pedido.repository.DetallePedidoRepository;
import com.donutin.servicio_pedido.repository.PedidoRepository;

@Service
public class GestionService 
{
    private final PedidoRepository pedidoRepository;
    private final WebClient.Builder webClientBuilder;
    private final DetallePedidoRepository detallePedidoRepository;

    public GestionService(PedidoRepository pedidoRepository, WebClient.Builder webClientBuilder, DetallePedidoRepository detallePedidoRepository)
    {
        this.pedidoRepository = pedidoRepository;
        this.webClientBuilder = webClientBuilder;
        this.detallePedidoRepository = detallePedidoRepository;
    }

    public DetallePedido guardarDetalle(DetallePedido detallePedido)
    {
        DetallePedido guardado = detallePedidoRepository.save(detallePedido);
        if(guardado.getPedido()!=null && guardado.getPedido().getIdPedido()!=null)
        {
            pedidoRepository.findById(guardado.getPedido().getIdPedido())
                .ifPresent(guardado::setPedido);
        }
        DetallePedido completo = detallePedidoRepository.findById(guardado.getIdDetalle())
            .orElse(guardado);
        return enriquecerConDonut(completo);
    }
    public DetallePedido obtenerDetallePedidoCompleto(Long id)
    {
        DetallePedido detallePedido = detallePedidoRepository.findById(id).orElse(null);
        if(detallePedido!=null)
        {
            return enriquecerConDonut(detallePedido);
        }
        return null;
    }
    public List<DetallePedido> listarTodo()
    {
        return detallePedidoRepository.findAll();
    }
    // Para Pedido
    public Pedido guardarPedido(Pedido pedido)
    {
        if(pedido.getDetallePedido()!=null)
        {
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                detalle.setPedido(pedido);
            }
        }

        Pedido guardado = pedidoRepository.save(pedido);

        if(guardado.getDetallePedido()!=null)
        {
            for (DetallePedido detalle : guardado.getDetallePedido()) {
                enriquecerConDonut(detalle);
            }
        }
        return guardado;
    }
    public List<Pedido> listarPedidos()
    {
        return pedidoRepository.findAll();
    }

    public Pedido obtenerPedidoPorId(Long id)
    {
        Pedido pedido = pedidoRepository.findById(id).orElse(null);

        if(pedido!=null && pedido.getDetallePedido()!=null)
        {
            for (DetallePedido detalle : pedido.getDetallePedido()) {
                enriquecerConDonut(detalle);
            }
        }
        return pedido;
    }

    //Método para evitar repetir código
    private DetallePedido enriquecerConDonut(DetallePedido detallePedido)
    {
        //1. Viaje por red a buscar la Donut (puerto 8081)
        if(detallePedido.getDonutId()!=null)
        {
            try {
                Object donut = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/api/v1/donuts/" + detallePedido.getDonutId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            detallePedido.setDatosDonuts(donut);
            } catch (Exception e) {
                detallePedido.setDatosDonuts("Información de donut no disponible");
            }
        }
        //2. Viaje por red a buscar la Empresa (puerto 8087)
        try {
            Object empresaMatriz = webClientBuilder.build()
            .get()
            .uri("http://localhost:8087/api/v1/empresas/1")
            .retrieve()
            .bodyToMono(Object.class)
            .block();
        detallePedido.setDatosEmpresa(empresaMatriz);
        } catch (Exception e) {
            detallePedido.setDatosEmpresa("Información de la pyme no disponible");
        }
        return detallePedido;
    }
}
