package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.dto.IReporteKardexPorProductoDto;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface KardexRepository extends JpaRepository<Kardex, Long> {
    @Query("SELECT k FROM Kardex AS k JOIN FETCH k.tipoOperacion AS o WHERE k.comprobanteId=?1 AND o.id=?2")
    Optional<Kardex> kardexByIdAndTipoOperacion(Long guiaRemisionId, Long tipoOperacionId);

    //TODO : Confirmar si estos 2 métodos deberían estar acá o en "KardexDetalleRepository"
    @Query(value = "select * from kardex_detalle where producto_id= :producto_id and fecha_emision > :fecha_emision order by fecha_emision asc, id asc", nativeQuery = true)
    List<KardexDetalle> KardexDetalleByProductoByFechaEmision(@Param("producto_id") Long produtoId, @Param("fecha_emision") LocalDateTime fechaEmision);

    @Query(value = "select saldo_cantidad from kardex_detalle where producto_id = :producto_id and fecha_emision <= :fecha_emision and eliminado = 0 order by fecha_emision desc, id desc limit 1", nativeQuery = true)
    Integer KardexDetalleUltimoSaldoCantidad(@Param("producto_id") Long produtoId, @Param("fecha_emision") LocalDateTime fechaEmision);

    @Query("SELECT d FROM KardexDetalle AS d JOIN FETCH d.producto AS p WHERE p.id=?1 AND d.fechaEmision <=?2 ORDER BY d.fechaEmision DESC, d.id DESC")
    KardexDetalle KardexDetalleUltimoSaldoCantidad2(Long produtoId, LocalDateTime fechaEmision);

    @Query("SELECT k.id AS id, p.id AS productoId, k.fechaEmision AS fechaEmision, k.numero AS numero, t.nombre AS tipoOperacion, kd.entradaCantidad AS entradaCantidad, kd.entradaPrecio AS entradaPrecio, kd.entradaTotal AS entradaTotal, kd.salidaCantidad AS salidaCantidad, kd.salidaPrecio AS salidaPrecio, kd.salidaTotal AS salidaTotal, kd.saldoCantidad AS saldoCantidad, kd.saldoPrecio AS saldoPrecio, kd.saldoTotal AS saldoTotal FROM Kardex AS k JOIN k.tipoOperacion as t JOIN k.kardexDetalles AS kd JOIN kd.producto AS p WHERE p.id = :producto_id AND k.eliminado = 0 AND kd.eliminado = 0 ORDER BY k.fechaEmision DESC, k.id DESC, kd.id DESC")
    List<IReporteKardexPorProductoDto> findByProductoId(@Param("producto_id") Long productoId);
}
