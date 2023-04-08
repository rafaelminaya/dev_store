package com.rminaya.dev.store.model.entity.consignacion;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guia_remision")
public class GuiaRemision {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    private LocalDateTime fechaEmision;
    @Column(name = "porcentaje_comision")
    private Double porcentajeComision;
    private Double total;
    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "guia_remision_id")
    private List<GuiaRemisionDetalle> guiaRemisionDetalles;

    public GuiaRemision() {
        this.guiaRemisionDetalles = new ArrayList<>();
    }

    // MÉTODOS
    public void calcularTotal() {

        Double total = 0.0;
        for (GuiaRemisionDetalle detalle : this.guiaRemisionDetalles) {
            total += detalle.getTotal();
        }

        this.setTotal(total);
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

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public List<GuiaRemisionDetalle> getGuiaRemisionDetalles() {
        return guiaRemisionDetalles;
    }

    public void setGuiaRemisionDetalles(List<GuiaRemisionDetalle> guiaRemisionDetalles) {
        this.guiaRemisionDetalles = guiaRemisionDetalles;
    }
}
