package com.rminaya.dev.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public interface IReporteVentasDto {
    Long getId();
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime getFechaEmision();
    String getNumero();
    Double getBaseImponible();
    Double getImporteIgv();
    Double getTotal();
    Long getClienteId();
    String getClienteNombre();
    String getClienteDni();
    Boolean getEliminado();
}
