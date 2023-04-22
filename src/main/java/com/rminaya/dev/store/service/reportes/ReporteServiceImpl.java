package com.rminaya.dev.store.service.reportes;

import com.rminaya.dev.store.model.dto.*;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import com.rminaya.dev.store.repository.GuiaRemisionRepository;
import com.rminaya.dev.store.repository.KardexDetalleRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReporteServiceImpl implements ReporteService {
    private final BoletaVentaRepository boletaVentaRepository;
    private final GuiaRemisionRepository guiaRemisionRepository;
    private final KardexRepository kardexRepository;

    public ReporteServiceImpl(BoletaVentaRepository boletaVentaRepository, GuiaRemisionRepository guiaRemisionRepository, KardexRepository kardexRepository) {
        this.boletaVentaRepository = boletaVentaRepository;
        this.guiaRemisionRepository = guiaRemisionRepository;
        this.kardexRepository = kardexRepository;
    }

    @Override
    public List<IReporteVentasDto> registroVentas(VentasInDto ventasInDto) {
        return this.boletaVentaRepository.reporteVenta(ventasInDto.getFechaInicio(), ventasInDto.getFechaFin());
    }

    @Override
    public List<IReporteKardexPorProductoDto> kardexPorProducto(KardexProductoDto kardexProductoDto) {
        List<IReporteKardexPorProductoDto> kardexByProducto = this.kardexRepository.findByProductoId(kardexProductoDto.getProductoId());
        return kardexByProducto;
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
        return this.boletaVentaRepository.findByProductosAgrupados(productosByProveedor,
                liquidacionDto.getFechaInicio(), liquidacionDto.getFechaFin());
    }
}
