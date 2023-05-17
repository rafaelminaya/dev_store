package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BoletaVentaService {
    List<BoletaVenta> findAll();
    Page<BoletaVenta> findAll(Integer page);
    BoletaVenta findById(Long id);
    Long save(BoletaVenta boletaVenta);
    void deleteById(Long id);
    // kardex
    Kardex saveKardex(Kardex kardex);
    void anular(Long boletaVentaId);
}
