package com.rminaya.dev.store.service.consignacion;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.repository.ProveedorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<Proveedor> findAll() {
        return this.proveedorRepository.findAll()
                .stream()
                .filter(proveedor -> proveedor.getEliminado().equals(false))
                .toList();
    }

    @Override
    public Page<Proveedor> findAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 6, Sort.by("id").descending());

        return this.proveedorRepository.findAllByEliminado(false, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Proveedor findById(Long id) {
        return this.proveedorRepository.findById(id)
                .filter(proveedor -> proveedor.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró el proveedor.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long save(Proveedor proveedor) {
        if (proveedor.getId() != null && proveedor.getId() > 0) {
            proveedor.setId(0L);
        }
        return this.proveedorRepository.save(proveedor).getId();
    }

    @Override
    @Transactional
    public Long update(Proveedor proveedor, Long id) {
        Proveedor proveedorBuscado = this.findById(id);
        proveedorBuscado.setRuc(proveedor.getRuc());
        proveedorBuscado.setRazonComercial(proveedor.getRazonComercial());
        proveedorBuscado.setDireccion(proveedor.getDireccion());
        proveedorBuscado.setTelefono(proveedor.getTelefono());
        return this.proveedorRepository.save(proveedorBuscado).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Proveedor proveedor = this.findById(id);
        proveedor.setEliminado(true);
        this.proveedorRepository.save(proveedor);
    }
}
