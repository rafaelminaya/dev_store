package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRespository extends JpaRepository<Cliente, Long> {
}