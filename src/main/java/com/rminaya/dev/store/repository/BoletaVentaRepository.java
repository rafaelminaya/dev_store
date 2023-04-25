package com.rminaya.dev.store.repository;

import com.rminaya.dev.store.model.dto.IReporteLiquidacionDto;
import com.rminaya.dev.store.model.dto.IReporteVentasDto;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface BoletaVentaRepository extends JpaRepository<BoletaVenta, Long> {
    @Query(value = """
            select\s
            bol.id as id,
            bol.fecha_emision as fechaEmision,
            bol.numero as numero,
            bol.base_imponible as baseImponible,
            bol.importe_igv as importeIgv,
            (bol.base_imponible + bol.importe_igv) as total,
            cli.id as clienteId,
            cli.nombre as clienteNombre,
            cli.dni as clienteDni,
            bol.eliminado as eliminado
            from boleta_venta as bol
            inner join clientes as cli
            on bol.cliente_id = cli.id
            where bol.fecha_emision between :fecha_inicio and :fecha_fin
            order by bol.fecha_emision asc, bol.id asc""", nativeQuery = true)
    List<IReporteVentasDto> reporteVenta(@Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);

    @Query("SELECT b FROM BoletaVenta AS b JOIN FETCH b.boletaVentaDetalles bt JOIN FETCH bt.producto AS p WHERE p.id IN :productos AND b.fechaEmision BETWEEN :fecha_inicio AND :fecha_fin")
    List<BoletaVenta> findByProductos(@Param("productos") List<Long> productosByProveedor, @Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);

    @Query("SELECT b.id AS boletaId, " +
            "b.numero AS boletaNumero, " +
            "b.fechaEmision AS boletaFechaEmision, " +
            "p.id as productoId, m.nombre AS marcaNombre,  " +
            "p.nombre AS productoNombre, " +
            "p.codigo AS productoCodigo, " +
            "p.color AS productoColor, " +
            "p.talla AS productoTalla, " +
            "SUM(bt.cantidad) AS boletaCantidad, " +
            "SUM(bt.cantidad * bt.precioCompra) AS boletaPrecioCompra, " +
            "SUM(bt.cantidad * bt.precioVenta) AS boletaPrecioVenta, " +
            "SUM((bt.getBaseImponible() + bt.getImporteIgv()) * bt.cantidad) AS boletaPrecioNeto " +
            "FROM BoletaVenta AS b " +
            "JOIN b.boletaVentaDetalles bt " +
            "JOIN bt.producto AS p " +
            "JOIN p.marca AS m " +
            "WHERE p.id IN :productos AND b.eliminado = 0 " +
            "AND b.fechaEmision BETWEEN :fecha_inicio AND :fecha_fin " +
            "GROUP BY p.id")
    List<IReporteLiquidacionDto> findByProductosAgrupados(@Param("productos") List<Long> productosByProveedor, @Param("fecha_inicio") LocalDateTime fechaInicio, @Param("fecha_fin") LocalDateTime fechaFin);
}
