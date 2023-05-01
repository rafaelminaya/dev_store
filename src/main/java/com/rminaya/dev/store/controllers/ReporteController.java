package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.dto.IReporteKardexPorProductoDto;
import com.rminaya.dev.store.model.dto.IReporteLiquidacionDto;
import com.rminaya.dev.store.model.dto.IReporteRegistroVentasDto;
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

    @GetMapping(value = "/registro-ventas")
    public ResponseEntity<List<IReporteRegistroVentasDto>> registroVentas(
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) {

        return ResponseEntity.ok(reporteService.registroVentas(fechaInicio, fechaFin));
    }

    @GetMapping(value = "/kardex-producto/{productoId}")
    public ResponseEntity<List<IReporteKardexPorProductoDto>> kardexPorProducto(
            @PathVariable(value = "productoId") String productoId) {

        return ResponseEntity.ok(reporteService.kardexPorProducto(productoId));
    }

    @GetMapping(value = "/liquidacion-proveedores/{proveedorId}")
    public ResponseEntity<List<IReporteLiquidacionDto>> liquidacionProveedores(
            @PathVariable(value = "proveedorId") String proveedorId,
            @RequestParam(value = "fechaInicio") String fechaInicio,
            @RequestParam(value = "fechaFin") String fechaFin) {

        return ResponseEntity.ok(reporteService.liquidacionProveedores(proveedorId, fechaInicio, fechaFin));
    }
}
