package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.common.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
