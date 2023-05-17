package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {
    Page<Proveedor> findAllByEliminado(Boolean eliminado, Pageable pageable);
}
