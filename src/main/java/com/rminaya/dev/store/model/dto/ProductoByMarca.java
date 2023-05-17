package com.rminaya.dev.store.model.dto;

import com.rminaya.dev.store.model.entity.common.Producto;

public class ProductoByMarca {
    Long productoId;
    Producto producto;
    String cadenaProducto;

    public ProductoByMarca() {
    }

    public ProductoByMarca(Long productoId, Producto productos, String cadenaProductos) {
        this.productoId = productoId;
        this.producto = productos;
        this.cadenaProducto = cadenaProductos;
    }
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public String getCadenaProducto() {
        return cadenaProducto;
    }

    public void setCadenaProducto(String cadenaProducto) {
        this.cadenaProducto = cadenaProducto;
    }
}
