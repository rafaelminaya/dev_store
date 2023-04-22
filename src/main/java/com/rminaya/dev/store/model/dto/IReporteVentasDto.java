package com.rminaya.dev.store.model.dto;

import java.time.LocalDateTime;

public interface IReporteVentasDto {
    Long getId();
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
