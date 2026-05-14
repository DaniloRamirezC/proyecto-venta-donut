package com.donutin.servicio_pedido.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.servicio_pedido.model.DetallePedido;
import com.donutin.servicio_pedido.model.Pedido;
import com.donutin.servicio_pedido.repository.DetallePedidoRepository;
import com.donutin.servicio_pedido.repository.PedidoRepository;

@Service
public class GestionService 
{
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;

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
        return pedidoRepository.save(pedido);
    }
    public List<Pedido> listarPedidos()
    {
        return pedidoRepository.findAll();
    }

    //Método para evitar repetir código
    private DetallePedido enriquecerConDonut(DetallePedido detallePedido)
    {
        if(detallePedido.getDonutId()!=null)
        {
            try {
                Object donut = webClientBuilder.build()
                .get()
                .uri("http://localhost:8081/donuts/" + detallePedido.getDonutId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            detallePedido.setDatosDonuts(donut);
            } catch (Exception e) {
                detallePedido.setDatosDonuts("Información de donut no disponible");
            }
        }
        return detallePedido;
    }
}
