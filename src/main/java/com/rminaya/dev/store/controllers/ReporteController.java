package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.dto.*;
import com.rminaya.dev.store.service.reportes.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/reportes")
public class ReporteController {
    @Autowired
    private ReporteService reporteService;

    @GetMapping(value = "/ventas")
    public ResponseEntity<List<IReporteVentasDto>> registroVentas(@RequestBody VentasInDto ventasInDto) {
        return ResponseEntity.ok(this.reporteService.registroVentas(ventasInDto));
    }

    @GetMapping(value = "/kardex-producto")
    public ResponseEntity<List<IReporteKardexPorProductoDto>> kardexPorProducto(@RequestBody KardexProductoDto kardexProductoDto) {
        return ResponseEntity.ok(this.reporteService.kardexPorProducto(kardexProductoDto));
    }

    @GetMapping(value = "/liquidacion-proveedores")
    public ResponseEntity<List<IReporteLiquidacionDto>> liquidacionProveedores(@RequestBody LiquidacionDto liquidacionDto) {
        List<IReporteLiquidacionDto> productos = this.reporteService.liquidacionProveedores(liquidacionDto);
        return ResponseEntity.ok(productos);
    }
}
