package com.rminaya.dev.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class LiquidacionDto {
    // ATRIBUTOS
    @NotNull(message = "no debe ser vacío.")
    @Min(value = 1, message = "debe ser un ID mayor a 1.")
    private Long proveedorId;
    @NotNull(message = "no debe ser vacío.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @NotNull(message = "no debe ser vacío.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    // GETTERS AND SETTERS

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
}
