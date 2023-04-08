package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.repository.ProveedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorServiceImpl implements ProveedorService {
    // ATRIBUTOS - Dependencias
    private final ProveedorRepository proveedorRepository;

    // CONSTRUCTOR - Inyección de dependencias
    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Proveedor> findAll() {
        return this.proveedorRepository.findAll();
    }

    @Override
    public Proveedor findById(Long id) {
        return this.proveedorRepository.findById(id).orElseThrow();
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return this.proveedorRepository.save(proveedor);
    }

    @Override
    public void deleteById(Long id) {
        this.proveedorRepository.deleteById(id);
    }
}
