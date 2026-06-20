package com.donutin.service_inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_inventario.model.Receta;
import java.util.List;

public interface RecetaRepository extends JpaRepository<Receta, Long> {
    List<Receta> findByIdDonut(Long idDonut);
}