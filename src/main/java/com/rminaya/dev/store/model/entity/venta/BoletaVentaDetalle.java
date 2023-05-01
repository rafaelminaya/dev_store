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
    private Double precioCompra;
    private Double precioVenta;
    private Double baseImponible;
    private Double importeIgv;
    private Double totalDetalle;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;
    private static final Double PORCENTAJE_IGV = 0.18;

    // MÉTODOS
    public Double calcularBaseImponible() {
        return (this.calcularTotalDetalle() / (1 + PORCENTAJE_IGV));
    }

    public Double calcularImporteIgv() {
        return this.calcularTotalDetalle() - this.calcularBaseImponible();
    }

    public Double calcularTotalDetalle() {
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

    public Double getTotalDetalle() {
        return totalDetalle;
    }

    public void setTotalDetalle(Double totalDetalle) {
        this.totalDetalle = totalDetalle;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
