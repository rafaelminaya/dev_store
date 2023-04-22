package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GuiaRemisionRepository extends JpaRepository<GuiaRemision, Long> {
    @Query("select g from GuiaRemision g where g.id = ?1 and g.procesado = 0 and g.eliminado = 0")
    Optional<GuiaRemision> findByIdAndNoProcesado(Long id);

    @Query("select g from GuiaRemision g where g.id = ?1 and g.procesado = 1 and g.eliminado = 0")
    Optional<GuiaRemision> findByIdAndProcesado(Long id);

    //@Query("select p from GuiaRemisionDetalle gt join fetch gt.producto p join fetch gt.guiaRemision g join fetch g.proveedor pr where pr.id = ?1 ")
    //@Query("select p from GuiaRemision g join fetch g.guiaRemisionDetalles gt join fetch gt.producto p join fetch g.proveedor pr where pr.id = ?1")
    //@Query("select p from Producto p join fetch p.guiaRemisionDetalles gt join fetch gt.producto p join fetch g.proveedor pr where pr.id = ?1")
    @Query("select g from GuiaRemision g join fetch g.guiaRemisionDetalles gt join fetch g.proveedor pr where pr.id = ?1")
    List<GuiaRemision> findByProveedor(Long proveedorId);

//    @Query()
//    List<GuiaRemision> findProductosVendidosByProveedor(Long proveedorId);
//
//    @Query(value = "", nativeQuery = true)
//    List<IReporteLiquidacionDto> reporteKardexPorProducto(Long productoId, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
