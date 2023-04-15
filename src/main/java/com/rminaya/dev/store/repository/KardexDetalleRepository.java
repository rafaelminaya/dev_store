package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface KardexDetalleRepository extends JpaRepository<KardexDetalle, Long> {


    @Query(value = "select * from kardex_detalle where producto_id = :producto_id and fecha_emision > :fecha_emision order by fecha_emision asc, id asc", nativeQuery = true)
    List<KardexDetalle> detallesByProductoAndFechaEmision(@Param("producto_id")Long productoId, @Param("fecha_emision") LocalDateTime fechaEmision);

}
