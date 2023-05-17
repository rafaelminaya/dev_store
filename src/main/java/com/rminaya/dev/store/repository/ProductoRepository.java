package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.common.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    Page<Producto> findAllByEliminado(Boolean eliminado, Pageable pageable);
    @Query("SELECT p FROM Producto as p JOIN p.marca as m WHERE m.id = ?1 and p.eliminado = 0")
    List<Producto> findByMarcaId(Long marcaId);
    @Query("SELECT p FROM Producto as p WHERE p.codigo LIKE %?1% OR p.nombre LIKE %?1%")
    List<Producto> findByFiltroVenta(String termino);

}
