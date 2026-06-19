package com.donutin.service_inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_inventario.model.Insumo;

public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}
