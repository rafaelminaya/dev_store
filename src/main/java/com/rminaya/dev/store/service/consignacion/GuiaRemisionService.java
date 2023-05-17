package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GuiaRemisionService {
    List<GuiaRemision> findAll();
    Page<GuiaRemision> findAll(Integer page);
    GuiaRemision findById(Long id);
    Long save(GuiaRemision guiaRemision);
    Long update(GuiaRemision guiaRemision, Long id);
    void procesarKardex(Long guiaRemisionId);
    void desprocesarKardex(Long guiaRemisionId);
    void deleteById(Long id);
}
