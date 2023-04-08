package com.rminaya.dev.store.model.entity.venta;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Entity
@Table(name = "boleta_venta")
public class BoletaVenta {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;
    @CreationTimestamp
//    @Column(name = "fecha_emision", columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    private LocalDateTime fechaEmision;
    @Column(name = "base_imponible")
    private Double baseImponible;
    @Column(name = "importe_igv")
    private Double importeIgv;
    private Double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "boleta_venta_id")
    private List<BoletaVentaDetalle> boletaVentaDetalles;

    public BoletaVenta() {
        this.boletaVentaDetalles = new ArrayList<>();
    }

    // MÉTODOS
    public void calcularTotal() {

        Double total = 0.0;
        for (BoletaVentaDetalle detalle : this.boletaVentaDetalles){
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

    public Double getTotal() {
        return total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<BoletaVentaDetalle> getBoletaVentaDetalles() {
        return boletaVentaDetalles;
    }

    public void setBoletaVentaDetalles(List<BoletaVentaDetalle> boletaVentaDetalles) {
        this.boletaVentaDetalles = boletaVentaDetalles;
    }

    @Override
    public String toString() {
        return "BoletaVenta{" +
                ", boletaVentaDetalles=" + boletaVentaDetalles +
                '}';
    }
}
