package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;

import java.util.List;

public interface GuiaRemisionService {
    List<GuiaRemision> findAll();
    GuiaRemision findById(Long id);
    GuiaRemision save(GuiaRemision guiaRemision);
    void procesarKardex(Long guiaRemisionId);
    void desprocesarKardex(Long guiaRemisionId);
    void deleteById(Long id);
}
