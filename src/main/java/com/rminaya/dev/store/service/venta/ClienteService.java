package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Page<Cliente> findAll(Integer page);
    Cliente findById(Long id);
    Long save(Cliente cliente);
    Long update(Cliente cliente, Long id);
    void deleteById(Long id);
}
