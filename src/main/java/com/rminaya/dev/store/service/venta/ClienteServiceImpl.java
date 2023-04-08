package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.repository.ClienteRespository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {
    private final ClienteRespository clienteRespository;

    public ClienteServiceImpl(ClienteRespository clienteRespository) {
        this.clienteRespository = clienteRespository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() {
        return this.clienteRespository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return this.clienteRespository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return this.clienteRespository.save(cliente);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.clienteRespository.deleteById(id);
    }
}
