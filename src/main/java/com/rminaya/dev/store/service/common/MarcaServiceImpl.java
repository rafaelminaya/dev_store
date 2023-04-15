package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.repository.MarcaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaServiceImpl implements MarcaService {
    // ATRIBUTOS - Dependencias
    private final MarcaRepository marcaRepository;

    // CONSTRUCTOR - Inyección de dependencias
    public MarcaServiceImpl(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    @Override
    public List<Marca> findAll() {
        return this.marcaRepository.findAll();
    }

    @Override
    public Marca findById(Long id) {
        return this.marcaRepository.findById(id).orElseThrow();
    }

    @Override
    public Marca save(Marca marca) {
        return this.marcaRepository.save(marca);
    }

    @Override
    public void deleteById(Long id) {
        this.marcaRepository.deleteById(id);
    }
}
