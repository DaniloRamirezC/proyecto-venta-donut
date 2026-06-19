package com.donutin.service_inventario.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.donutin.service_inventario.model.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

}
