package com.rminaya.dev.store.model.dto;

import com.rminaya.dev.store.model.entity.common.Producto;

import javax.validation.constraints.NotNull;

public class BoletaVentaDetalleInDto {
    @NotNull
    private Integer cantidad;
    @NotNull
    private ProductoInDto producto;

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ProductoInDto getProducto() {
        return producto;
    }

    public void setProducto(ProductoInDto producto) {
        this.producto = producto;
    }
}
