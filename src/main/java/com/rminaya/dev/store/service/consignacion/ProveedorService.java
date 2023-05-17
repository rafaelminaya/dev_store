package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> findAll();
    Page<Proveedor> findAll(Integer page);
    Proveedor findById(Long id);
    Long save(Proveedor proveedor);
    Long update(Proveedor proveedor, Long id);
    void deleteById(Long id);
}
