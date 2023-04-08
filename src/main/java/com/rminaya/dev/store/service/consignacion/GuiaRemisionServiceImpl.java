package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.repository.GuiaRemisionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GuiaRemisionServiceImpl implements GuiaRemisionService {
    private final GuiaRemisionRepository guiaRemisionRepository;

    public GuiaRemisionServiceImpl(GuiaRemisionRepository guiaRemisionRepository) {
        this.guiaRemisionRepository = guiaRemisionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<GuiaRemision> findAll() {
        return this.guiaRemisionRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public GuiaRemision findById(Long id) {
        return this.guiaRemisionRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public GuiaRemision save(GuiaRemision guiaRemision) {
        return this.guiaRemisionRepository.save(guiaRemision);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.guiaRemisionRepository.deleteById(id);
    }
}
