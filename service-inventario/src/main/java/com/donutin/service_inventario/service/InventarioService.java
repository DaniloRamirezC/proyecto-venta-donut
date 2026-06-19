package com.donutin.service_inventario.service;

import java.util.List;
import org.springframework.web.reactive.function.client.WebClient;
import com.donutin.service_inventario.model.Insumo;
import com.donutin.service_inventario.model.Proveedor;
import com.donutin.service_inventario.model.Receta;
import com.donutin.service_inventario.repository.InsumoRepository;
import com.donutin.service_inventario.repository.ProveedorRepository;
import com.donutin.service_inventario.repository.RecetaRepository;

public class InventarioService {
    private final ProveedorRepository proveedorRepository;
    private final InsumoRepository insumoRepository;
    private final RecetaRepository recetaRepository;
    private final WebClient.Builder webClientBuilder;

    public InventarioService(ProveedorRepository proveedorRepository, InsumoRepository insumoRepository, RecetaRepository recetaRepository, WebClient.Builder webClientBuilder){
        this.proveedorRepository = proveedorRepository;
        this.insumoRepository = insumoRepository;
        this.recetaRepository = recetaRepository;
        this.webClientBuilder = webClientBuilder;
    }

    // Metodos para Proveedor
    public Proveedor guardarProveedor(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }

    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findAll();
    }

    public Proveedor obtenerProveedorPorId(Long id) {
        return proveedorRepository.findById(id).orElseThrow(() -> new RuntimeException("Proveedor no encontrado con el ID: " + id));
    }

    public void eliminarProveedor(Long id) {
        if (!proveedorRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Proveedor no encontrado con el ID: " + id);
        }
        proveedorRepository.deleteById(id);
    }

    // Metodos para Insumo
    public Insumo guardarInsumo(Insumo insumo) {
        return insumoRepository.save(insumo);
    }

    public List<Insumo> listarInsumos() {
        return insumoRepository.findAll();
    }

    public Insumo obtenerInsumoPorId(Long id) {
        return insumoRepository.findById(id).orElseThrow(() -> new RuntimeException("Insumo no encontrado con el ID: " + id));
    }

    public void eliminarInsumo(Long id) {
        if (!insumoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Insumo no encontrado con el ID: " + id);
        }
    insumoRepository.deleteById(id);
    }

    // Metodos para Receta
    public Receta guardarReceta(Receta receta) {
        Receta guardada = recetaRepository.save(receta);
        return enriquecerConDonut(guardada);
    }

    public Receta obtenerRecetaPorId(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada con el ID: " + id));
        
        return enriquecerConDonut(receta);
    }

    public List<Receta> listarRecetas() {
        List<Receta> recetas = recetaRepository.findAll();
        return recetas;
    }

    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar. Receta no encontrada con el ID: " + id);
        }
        recetaRepository.deleteById(id);
    }

    //Metodo para evitar repetir codigo
    private Receta enriquecerConDonut(Receta receta) {
        if (receta.getIdDonut() != null) {
            try {
                Object donut = webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/api/v1/donuts/" + receta.getIdDonut())
                .retrieve()
                .bodyToMono(Object.class)
                .block();
            receta.setDatosDonut(donut);
            } catch (Exception e) {
                receta.setDatosDonut("Información de donut no disponible");
            }
        }
        return receta;
    }
}
