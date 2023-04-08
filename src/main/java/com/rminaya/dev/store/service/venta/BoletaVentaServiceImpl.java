package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoletaVentaServiceImpl implements BoletaVentaService {

    private final BoletaVentaRepository boletaVentaRepository;

    public BoletaVentaServiceImpl(BoletaVentaRepository boletaVentaRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BoletaVenta> findAll() {
        return this.boletaVentaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public BoletaVenta findById(Long id) {
        return this.boletaVentaRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public BoletaVenta save(BoletaVenta boletaVenta) {
        return this.boletaVentaRepository.save(boletaVenta);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.boletaVentaRepository.deleteById(id);
    }
}
