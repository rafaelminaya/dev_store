package com.rminaya.dev.store.service.reportes;

import com.rminaya.dev.store.model.dto.*;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import com.rminaya.dev.store.repository.GuiaRemisionRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {
    private final BoletaVentaRepository boletaVentaRepository;
    private final GuiaRemisionRepository guiaRemisionRepository;
    private final KardexRepository kardexRepository;

    public ReporteServiceImpl(BoletaVentaRepository boletaVentaRepository,
                              GuiaRemisionRepository guiaRemisionRepository,
                              KardexRepository kardexRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
        this.guiaRemisionRepository = guiaRemisionRepository;
        this.kardexRepository = kardexRepository;
    }

    @Override
    public List<IReporteVentasDto> registroVentas(VentasInDto ventasInDto) {
        LocalDateTime fechaInicio = LocalDateTime.of(ventasInDto.getFechaInicio(), LocalTime.MIN);
        LocalDateTime fechaFin = LocalDateTime.of(ventasInDto.getFechaFin(), LocalTime.MAX);
        return this.boletaVentaRepository.reporteVenta(fechaInicio, fechaFin);
    }

    @Override
    public List<IReporteKardexPorProductoDto> kardexPorProducto(KardexProductoDto kardexProductoDto) {
        return this.kardexRepository.findByProductoId(kardexProductoDto.getProductoId());
    }

    @Override
    public List<IReporteLiquidacionDto> liquidacionProveedores(LiquidacionDto liquidacionDto) {
        // Obtengo las guías del proveedor
        List<GuiaRemision> guiasByProveedor = this.guiaRemisionRepository.findByProveedor(liquidacionDto.getProveedorId());
        // Obtengo los id de los productos del proveedor
        List<Long> productosByProveedor = guiasByProveedor
                .stream()
                .flatMap(guiaRemision -> guiaRemision.getGuiaRemisionDetalles().stream())
                .map(guiaRemisionDetalle -> guiaRemisionDetalle.getProducto())
                .map(producto -> producto.getId())
                .distinct().toList();
        /*
        List<BoletaVenta> ventasByProductos = this.boletaVentaRepository.findByProductos(productosByProveedor,
                liquidacionDto.getFechaInicio(), liquidacionDto.getFechaFin());
        */
        // Obtengo los productos vendidos del proveedor en el rango de fechas recibido
        LocalDateTime fechaInicio = LocalDateTime.of(liquidacionDto.getFechaInicio(), LocalTime.MIN);
        LocalDateTime fechaFin = LocalDateTime.of(liquidacionDto.getFechaFin(), LocalTime.MAX);
        return this.boletaVentaRepository.findByProductosAgrupados(productosByProveedor,
                fechaInicio, fechaFin);
    }
}
