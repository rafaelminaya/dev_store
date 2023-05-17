package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Marca;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MarcaService {
    List<Marca> findAll();
    Page<Marca> findAll(Integer page);
    Marca findById(Long id);
    Long save(Marca marca);
    Long update(Marca marca, Long id);
    void deleteById(Long id);
}
