package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;

import java.util.List;

public interface ProveedorService {
    List<Proveedor> findAll();
    Proveedor findById(Long id);
    Long save(Proveedor proveedor);
    Long update(Proveedor proveedor, Long id);
    void deleteById(Long id);
}
