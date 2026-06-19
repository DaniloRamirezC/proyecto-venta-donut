package com.donutin.service_promociones.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_promociones.model.Cupon;
import java.util.Optional;

public interface CuponRepository extends JpaRepository<Cupon, Long> {
    Optional<Cupon> findByCodigo(String codigo);
}
