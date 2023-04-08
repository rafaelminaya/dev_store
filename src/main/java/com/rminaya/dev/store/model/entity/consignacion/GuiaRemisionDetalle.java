package com.rminaya.dev.store.model.entity.consignacion;

import com.rminaya.dev.store.model.entity.common.Producto;

import javax.persistence.*;

@Entity
@Table(name = "guia_emision_detalle")
public class GuiaRemisionDetalle {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    @Column(name = "precio_compra")
    private Double precioCompra;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    private Double total;

    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(Double precioCompra) {
        this.precioCompra = precioCompra;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
