package com.rminaya.dev.store.service.reportes;

import com.rminaya.dev.store.model.dto.*;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.repository.BoletaVentaRepository;
import com.rminaya.dev.store.repository.GuiaRemisionRepository;
import com.rminaya.dev.store.repository.KardexRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<IReporteRegistroVentasDto> registroVentas(String fechaInicio, String fechaFin) {
        // Convertimos las fechas de tipo String a LocalDate le añadimos la hora mínima y máxima
        LocalDateTime fechaInicioDateTime = LocalDateTime.of(LocalDate.parse(fechaInicio), LocalTime.MIN);
        LocalDateTime fechaFinDateTime = LocalDateTime.of(LocalDate.parse(fechaFin), LocalTime.MAX);

        return this.boletaVentaRepository.reporteVenta(fechaInicioDateTime, fechaFinDateTime);
    }

    @Override
    public List<IReporteKardexPorProductoDto> kardexPorProducto(String productoId) {
        // Convertimos el "String" recbido en "Long" que es lo que se necesita
        return this.kardexRepository.findByProductoId(Long.valueOf(productoId));
    }

    @Override
    public List<IReporteLiquidacionDto> liquidacionProveedores(String proveedorId, String fechaInicio, String fechaFin) {
        // Obtengo las guías del proveedor
        List<GuiaRemision> guiasByProveedor = this.guiaRemisionRepository.findByProveedor(Long.valueOf(proveedorId));
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
        LocalDateTime fechaInicioDateTime = LocalDateTime.of(LocalDate.parse(fechaInicio), LocalTime.MIN);
        LocalDateTime fechaFinDateTime = LocalDateTime.of(LocalDate.parse(fechaFin), LocalTime.MAX);

        return this.boletaVentaRepository.findByProductosAgrupados(productosByProveedor,
                fechaInicioDateTime, fechaFinDateTime);
    }
}
