package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Producto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductoService {
    List<Producto> findAll();
    Page<Producto> findAll(Integer page);
    Producto findById(Long id);
    List<Producto> findByFiltroVenta(String termino);
    Long save(Producto producto);
    Long update(Producto producto, Long id);
    void deleteById(Long id);
    List<Producto> findByMarcaId(Long marcaId);

}
