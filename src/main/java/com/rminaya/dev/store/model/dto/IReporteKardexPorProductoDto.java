package com.rminaya.dev.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface IReporteKardexPorProductoDto {
    Long getId();
    Long getProductoId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getFechaEmision();
    String getNumero();
    String getTipoOperacion();
    Integer getEntradaCantidad();
    Double getEntradaPrecio();
    Double getEntradaTotal();
    Integer getSalidaCantidad();
    Double getSalidaPrecio();
    Double getSalidaTotal();
    Integer getSaldoCantidad();
    Double getSaldoPrecio();
    Double getSaldoTotal();
}
