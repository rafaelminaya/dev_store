package com.rminaya.dev.store.service.common;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.repository.ProductoRepository;
import org.springframework.http.HttpStatus;
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
        return this.productoRepository.findAll()
                .stream()
                .filter(producto -> producto.getEliminado().equals(false))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Producto findById(Long id) {
        return this.productoRepository.findById(id)
                .filter(producto -> producto.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró el producto.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long save(Producto producto) {
        return this.productoRepository.save(producto).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Producto producto = this.findById(id);
        producto.setEliminado(true);
        this.productoRepository.save(producto);
    }
}
