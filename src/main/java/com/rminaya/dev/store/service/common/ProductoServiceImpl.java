package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductoServiceImpl implements ProductoService {
    // ATRIBUTOS - Dependencias
    private final ProductoRepository productoRepository;

    // CONSTRUCTOR - Inyección de dependencias
    public ProductoServiceImpl(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> findAll() {
        return this.productoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return this.productoRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Producto save(Producto producto) {
        return this.productoRepository.save(producto);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.productoRepository.deleteById(id);
    }
}
