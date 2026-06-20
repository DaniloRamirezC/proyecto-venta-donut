package com.donutin.service_logistica.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.service_logistica.model.Despacho;
import com.donutin.service_logistica.repository.DespachoRepository;

@Service
public class DespachoService {
    private final DespachoRepository despachoRepository;
    private final WebClient.Builder webClientBuilder;

    DespachoService(DespachoRepository despachoRepository, WebClient.Builder webClientBuilder) {
        this.despachoRepository = despachoRepository;
        this.webClientBuilder = webClientBuilder;
    }

    public Despacho guardarDespacho(Despacho despacho){
        Despacho guardado = despachoRepository.save(despacho);
        return enriquecerConPedido(guardado);
    }

    public Despacho obtenerDespacho(Long id){
        Despacho despacho = despachoRepository.findById(id).orElse(null);
        if(despacho != null){
            return enriquecerConPedido(despacho);
        }
        return null;
    }

    public List<Despacho> listarDespachos(){
        return despachoRepository.findAll();
    }

    //METODO PARA ACTUALIZAR ESTADO DE PEDIDO
    public Despacho actualizarEstado(Long id, String nuevoEstado){
        Despacho despacho = despachoRepository.findById(id).orElse(null);

        if (despacho != null) {
            despacho.setEstado(nuevoEstado);
            Despacho actualizado = despachoRepository.save(despacho);
            return enriquecerConPedido(actualizado);
        }
        return null;
    }

    private Despacho enriquecerConPedido(Despacho despacho){
        if(despacho.getPedidoId() != null){
            try{
                Object pedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/api/v1/pedidos/" + despacho.getPedidoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            despacho.setDatosPedido(pedido);
            } catch(Exception e){
                despacho.setDatosPedido("Informacion de pedido no disponible");
            }
        }
        
        if (despacho.getClienteId() != null) {
            try {
                Object cliente = webClientBuilder.build()
                .get()
                .uri("http://localhost:8082/clientes/" + despacho.getClienteId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            despacho.setDatosCliente(cliente);
            } catch (Exception e) {
                despacho.setDatosCliente("Información de cliente no disponible");
            }
        }

        return despacho;
    }  
}
