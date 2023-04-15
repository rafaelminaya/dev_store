package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.almacen.TipoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoOperacionRepository extends JpaRepository<TipoOperacion, Long> {
}
