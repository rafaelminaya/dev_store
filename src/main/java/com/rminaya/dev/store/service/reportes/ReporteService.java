package com.rminaya.dev.store.service.reportes;

import com.rminaya.dev.store.model.dto.*;
import com.rminaya.dev.store.model.entity.almacen.Kardex;

import java.util.List;

public interface ReporteService {
    List<IReporteVentasDto> registroVentas(VentasInDto ventasInDto);
    List<IReporteKardexPorProductoDto> kardexPorProducto(KardexProductoDto kardexProductoDto);
    List<IReporteLiquidacionDto> liquidacionProveedores(LiquidacionDto liquidacionDto);
}
