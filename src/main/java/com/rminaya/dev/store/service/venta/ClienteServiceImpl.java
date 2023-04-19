package com.rminaya.dev.store.service.venta;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.repository.ClienteRespository;
import org.springframework.http.HttpStatus;
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
        return this.clienteRespository.findAll()
                .stream()
                .filter(cliente -> cliente.getEliminado().equals(false))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Cliente findById(Long id) {
        return this.clienteRespository.findById(id)
                .filter(cliente -> cliente.getEliminado().equals(false))
                .orElseThrow(() -> new DevStoreExceptions("No se encontró al cliente.", HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Long save(Cliente cliente) {
        return this.clienteRespository.save(cliente).getId();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        Cliente cliente = this.findById(id);
        cliente.setEliminado(true);
        this.clienteRespository.save(cliente);
    }
}
