package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KardexRepository extends JpaRepository<Kardex, Long> {

    @Query("select k from Kardex k where k.comprobanteId=?1 and k.tipoOperacion=?2")
    Optional<Kardex> kardexByConsignacion(Long guiaRemisionId, Long tipoOperacionId);

    //TODO : Confirmar si estos 2 métodos deberían estar acá o en "KardexDetalleRepository"
    @Query(value = "select * from kardex_detalle where producto_id= :producto_id and fecha_emision > :fecha_emision order by fecha_emision asc, id asc" , nativeQuery = true)
    List<KardexDetalle> KardexDetalleByProductoByFechaEmision(@Param("producto_id") Long produtoId, @Param("fecha_emision") LocalDateTime fechaEmision);

    @Query(value = "select * from kardex_detalle where producto_id = :producto_id and fecha_emision <= :fecha_emision order by fecha_emision desc, id desc", nativeQuery = true)
    Optional<KardexDetalle> KardexDetalleUltimoSaldoCantidad(@Param("producto_id") Long produtoId, @Param("fecha_emision") LocalDateTime fechaEmision);
}
