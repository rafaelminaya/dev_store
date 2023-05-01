package com.rminaya.dev.store.service.reportes;

import com.rminaya.dev.store.model.dto.*;

import java.util.List;

public interface ReporteService {
    List<IReporteRegistroVentasDto> registroVentas(String fechaInicio, String fechaFin);
    List<IReporteKardexPorProductoDto> kardexPorProducto(String productoId);
    List<IReporteLiquidacionDto> liquidacionProveedores(String proveedorId, String fechaInicio, String fechaFin);
}
