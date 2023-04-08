package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.common.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
