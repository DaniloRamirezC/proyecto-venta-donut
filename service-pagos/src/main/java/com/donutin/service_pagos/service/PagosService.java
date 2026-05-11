package com.donutin.service_pagos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.donutin.service_pagos.model.Pago;
import com.donutin.service_pagos.model.ComprobantePago;
import com.donutin.service_pagos.repository.PagoRepository;
import com.donutin.service_pagos.repository.ComprobantePagoRepository;

@Service
public class PagosService {
    @Autowired
    private PagoRepository pagoRepository;
    @Autowired
    private WebClient.Builder webClientBuilder;
    @Autowired
    private ComprobantePagoRepository comprobantePagoRepository;

    // Metodos de Pago
    public Pago guardarPago(Pago pago){
        Pago guardado = pagoRepository.save(pago);
        return enriquecerConPedido(guardado);
    }

    public Pago obtenerPago(Long id){
        Pago pago = pagoRepository.findById(id).orElse(null);
        if(pago != null){
            return enriquecerConPedido(pago);
        }
        return null;
    }

    public List<Pago> listarPagos(){
        return pagoRepository.findAll();
    }

    //Metodos para ComprobantePago

    public ComprobantePago guardarComprobantePago(ComprobantePago comprobantePago){
        ComprobantePago guardado = comprobantePagoRepository.save(comprobantePago);
        if(guardado.getPago() != null && guardado.getPago().getIdPago() !=null){
            pagoRepository.findById(guardado.getPago().getIdPago()).ifPresent(guardado::setPago);
        }
        ComprobantePago completo = comprobantePagoRepository.findById(guardado.getIdComprobante()).orElse(guardado);
        return completo;
    }

    public List<ComprobantePago> listarComprobantePagos(){
        return comprobantePagoRepository.findAll();
    }

    //Metodo para evitar repetir codigo
    private Pago enriquecerConPedido(Pago pago){
        if(pago.getPedidoId() != null){
            try{
                Object pedido = webClientBuilder.build()
                .get()
                .uri("http://localhost:8083/pedido/" + pago.getPedidoId())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            pago.setDatosPedido(pedido);
            } catch(Exception e){
                pago.setDatosPedido("Informacion de pedido no disponible");
            }
        }
        return pago;
    }
}
