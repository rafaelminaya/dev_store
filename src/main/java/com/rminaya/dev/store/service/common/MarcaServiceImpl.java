package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.repository.MarcaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<Marca> findAll() {
        return this.marcaRepository.findAll()
                .stream()
                .filter(marca -> marca.getEliminado().equals(false))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Marca findById(Long id) {
        return this.marcaRepository.findById(id)
                .filter(marca -> marca.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró la marca.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long save(Marca marca) {
        if (marca.getId() != null && marca.getId() > 0) {
            marca.setId(0L);
        }
        return this.marcaRepository.save(marca).getId();
    }

    @Override
    @Transactional
        public Long update(Marca marca, Long id) {
        Marca marcaBuscada = this.findById(id);
        marcaBuscada.setNombre(marca.getNombre());
        return this.marcaRepository.save(marcaBuscada).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Marca marca = findById(id);
        marca.setEliminado(true);
        this.marcaRepository.save(marca);
    }
}
