package com.rminaya.dev.store.model.entity.almacen;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rminaya.dev.store.model.entity.common.Producto;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kardex_detalle")
public class KardexDetalle {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    private Integer entradaCantidad;
    private Double entradaPrecio;
    private Double entradaTotal;
    private Integer salidaCantidad;
    private Double salidaPrecio;
    private Double salidaTotal;
    private Integer saldoCantidad;
    private Double saldoPrecio;
    private Double saldoTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private Producto producto;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    /*
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kardex_id")
    @JsonIgnoreProperties({"kardexDetalles","hibernateLazyInitializer"})
    private Kardex kardex;
    */

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Integer getEntradaCantidad() {
        return entradaCantidad;
    }

    public void setEntradaCantidad(Integer entradaCantidad) {
        this.entradaCantidad = entradaCantidad;
    }

    public Double getEntradaPrecio() {
        return entradaPrecio;
    }

    public void setEntradaPrecio(Double entradaPrecio) {
        this.entradaPrecio = entradaPrecio;
    }

    public Double getEntradaTotal() {
        return entradaTotal;
    }

    public void setEntradaTotal(Double entradaTotal) {
        this.entradaTotal = entradaTotal;
    }

    public Integer getSalidaCantidad() {
        return salidaCantidad;
    }

    public void setSalidaCantidad(Integer salidaCantidad) {
        this.salidaCantidad = salidaCantidad;
    }

    public Double getSalidaPrecio() {
        return salidaPrecio;
    }

    public void setSalidaPrecio(Double salidaPrecio) {
        this.salidaPrecio = salidaPrecio;
    }

    public Double getSalidaTotal() {
        return salidaTotal;
    }

    public void setSalidaTotal(Double salidaTotal) {
        this.salidaTotal = salidaTotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Integer getSaldoCantidad() {
        return saldoCantidad;
    }

    public void setSaldoCantidad(Integer saldoCantidad) {
        this.saldoCantidad = saldoCantidad;
    }

    public Double getSaldoPrecio() {
        return saldoPrecio;
    }

    public void setSaldoPrecio(Double saldoPrecio) {
        this.saldoPrecio = saldoPrecio;
    }

    public Double getSaldoTotal() {
        return saldoTotal;
    }

    public void setSaldoTotal(Double saldoTotal) {
        this.saldoTotal = saldoTotal;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "KardexDetalle{" +
                "id=" + id +
                ", fechaEmision=" + fechaEmision +
                ", entradaCantidad=" + entradaCantidad +
                ", entradaPrecio=" + entradaPrecio +
                ", entradaTotal=" + entradaTotal +
                ", salidaCantidad=" + salidaCantidad +
                ", salidaPrecio=" + salidaPrecio +
                ", salidaTotal=" + salidaTotal +
                ", saldoCantidad=" + saldoCantidad +
                ", saldoPrecio=" + saldoPrecio +
                ", saldoTotal=" + saldoTotal +
                ", producto=" + producto +
                ", eliminado=" + eliminado +
                '}';
    }
}
