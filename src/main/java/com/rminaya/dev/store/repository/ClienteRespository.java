package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Long> {
    Page<Cliente> findAllByEliminado(Boolean eliminado, Pageable pageable);
}
