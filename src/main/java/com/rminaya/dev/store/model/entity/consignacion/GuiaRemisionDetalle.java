package com.rminaya.dev.store.model.entity.consignacion;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private Double precioVenta;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "guia_remision_id")
    @JsonIgnoreProperties({"guiaRemisionDetalles","hibernateLazyInitializer"})
    @JsonIgnore
    private GuiaRemision guiaRemision;
    @Column(name = "eliminado", columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    // MÉTODOS
    public Double getPorcentajeComision() {
        return this.guiaRemision.getPorcentajeComision();
    }

    public Double getImporteComision() {
        return this.precioVenta * (this.getPorcentajeComision() / 100);
//        return this.precioVenta * (this.porcentajeComision / 100);
    }

    public Double getPrecioCompra() {
        return this.precioVenta - this.getImporteComision();
    }

    public Double getTotalDetalle() {
        return this.precioVenta * this.cantidad;
    }

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

    public Double getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(Double precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public GuiaRemision getGuiaRemision() {
        return guiaRemision;
    }

    public void setGuiaRemision(GuiaRemision guiaRemision) {
        this.guiaRemision = guiaRemision;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "GuiaRemisionDetalle{" +
                "id=" + id +
                ", cantidad=" + cantidad +
                ", precioVenta=" + precioVenta +
                ", producto=" + producto +
                ", guiaRemision=" + guiaRemision +
                ", eliminado=" + eliminado +
                '}';
    }
}
