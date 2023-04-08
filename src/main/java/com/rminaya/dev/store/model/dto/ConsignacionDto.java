package com.rminaya.dev.store.model.dto;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;

import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

public class ConsignacionDto {
    // ATRIBUTOS
    private String numero;
    private LocalDateTime fechaEmision;
    private Double porcentajeComision;
    @ManyToOne
    private Proveedor proveedor;

    //GETTERS AND SETTERS
    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }
}
