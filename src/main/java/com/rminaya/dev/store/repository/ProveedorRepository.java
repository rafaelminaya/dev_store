package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
}
