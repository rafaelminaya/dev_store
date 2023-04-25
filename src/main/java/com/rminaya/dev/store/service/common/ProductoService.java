package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Producto;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Producto findById(Long id);
    Long save(Producto producto);
    Long update(Producto producto, Long id);
    void deleteById(Long id);

}
