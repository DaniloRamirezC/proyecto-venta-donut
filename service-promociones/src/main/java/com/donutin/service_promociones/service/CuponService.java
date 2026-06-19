package com.donutin.service_promociones.service;
import java.util.List;
import org.springframework.stereotype.Service;
import com.donutin.service_promociones.model.Cupon;
import com.donutin.service_promociones.repository.CuponRepository;

@Service
public class CuponService {

    private final CuponRepository cuponRepository;

    public CuponService(CuponRepository cuponRepository) {
        this.cuponRepository = cuponRepository;
    }

    public Cupon guardarCupon(Cupon cupon) {
        return cuponRepository.save(cupon);
    }

    public List<Cupon> listarCupones() {
        return cuponRepository.findAll();
    }

    public Cupon obtenerPorId(Long id) {
        return cuponRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cupón no encontrado con el ID: " + id));
    }

    public Cupon obtenerPorCodigo(String codigo) {
        return cuponRepository.findByCodigo(codigo)
                .orElseThrow(() -> new RuntimeException("El código de cupón '" + codigo + "' no es válido o no existe"));
    }

}
