package com.rminaya.dev.store.model.entity.venta;

import com.rminaya.dev.store.model.entity.common.Producto;

import javax.persistence.*;

@Entity
@Table(name = "boleta_venta_detalle")
public class BoletaVentaDetalle {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer cantidad;
    @Column(name = "precio_compra")
    private Double precioCompra;
    @Column(name = "precio_venta")
    private Double precioVenta;
    @Column(name = "base_imponible")
    private Double baseImponible;
    @Column(name = "importe_igv")
    private Double importeIgv;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    // MÉTODOS
    public Double getTotal() {
        return this.precioVenta * this.cantidad;
    }

    // GETERS AND SETTERS

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

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Double getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(Double baseImponible) {
        this.baseImponible = baseImponible;
    }

    public Double getImporteIgv() {
        return importeIgv;
    }

    public void setImporteIgv(Double importeIgv) {
        this.importeIgv = importeIgv;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

}
