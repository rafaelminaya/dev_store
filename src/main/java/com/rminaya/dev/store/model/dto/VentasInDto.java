package com.rminaya.dev.store.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
public class VentasInDto {
    // ATRIBUTOS
    @NotNull(message = "no debe ser vacío.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @NotNull(message = "no debe ser vacío.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;

    public VentasInDto() {
    }

    // GETTERS AND SETTERS
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
