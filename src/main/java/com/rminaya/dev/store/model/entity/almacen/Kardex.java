package com.rminaya.dev.store.model.entity.almacen;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "kardex")
public class Kardex {
    // ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    private Long comprobanteId;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_operacion_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private TipoOperacion tipoOperacion;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "kardex_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private List<KardexDetalle> kardexDetalles;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    // CONSTRUCTOR
    public Kardex() {
        this.kardexDetalles = new ArrayList<>();
    }

    // MÉTODOS
    public void addDetalle(KardexDetalle kardexDetalle) {
        this.kardexDetalles.add(kardexDetalle);
    }

    // GETTERS AND SETTERS

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Long getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Long comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public TipoOperacion getTipoOperacion() {
        return tipoOperacion;
    }

    public void setTipoOperacion(TipoOperacion tipoOperacion) {
        this.tipoOperacion = tipoOperacion;
    }

    public List<KardexDetalle> getKardexDetalles() {
        return kardexDetalles;
    }

    public void setKardexDetalles(List<KardexDetalle> kardexDetalles) {
        this.kardexDetalles = kardexDetalles;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "Kardex{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", comprobanteId=" + comprobanteId +
                ", fechaEmision=" + fechaEmision +
                ", tipoOperacion=" + tipoOperacion +
                ", kardexDetalles=" + kardexDetalles +
                ", eliminado=" + eliminado +
                '}';
    }
}
