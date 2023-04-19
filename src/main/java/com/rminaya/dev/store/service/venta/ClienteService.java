package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.venta.Cliente;

import java.util.List;

public interface ClienteService {
    List<Cliente> findAll();
    Cliente findById(Long id);
    Long save(Cliente cliente);
    void deleteById(Long id);
}
