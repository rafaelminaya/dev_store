package com.rminaya.dev.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface IReporteLiquidacionDto {
    Long getBoletaId();
    String getBoletaNumero();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getBoletaFechaEmision();
    Long getProductoId();
    String getMarcaNombre();
    String getProductoNombre();
    String getProductoCodigo();
    String getProductoTalla();
    String getProductoColor();
    Long getBoletaCantidad();
    Double getBoletaPrecioCompra();
    Double getBoletaPrecioVenta();
    Double getBoletaPrecioNeto();
}
