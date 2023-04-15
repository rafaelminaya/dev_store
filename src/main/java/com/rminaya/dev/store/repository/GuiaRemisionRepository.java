package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Long> {
    @Query("select g from GuiaRemision g where id = ?1 and procesado = 0")
    Optional<GuiaRemision> findByIdAndNoProcesado(Long id);
}
