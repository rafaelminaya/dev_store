package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;

import java.util.List;

public interface BoletaVentaService {
    List<BoletaVenta> findAll();
    BoletaVenta findById(Long id);
    BoletaVenta save(BoletaVenta boletaVenta);
    void deleteById(Long id);
    // kardex
    Kardex saveKardex(Kardex kardex);
}
