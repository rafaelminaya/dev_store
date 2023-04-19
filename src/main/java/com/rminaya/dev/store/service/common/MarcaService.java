package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Marca;

import java.util.List;

public interface MarcaService {
    List<Marca> findAll();
    Marca findById(Long id);
    Long save(Marca marca);
    void deleteById(Long id);
}
