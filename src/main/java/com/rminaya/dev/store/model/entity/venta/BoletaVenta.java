package com.rminaya.dev.store.model.entity.venta;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
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
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @Column(name = "fecha_emision", columnDefinition = "DATETIME")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime fechaEmision;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "boleta_venta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer"})
    private List<BoletaVentaDetalle> boletaVentaDetalles;

    // CONSTRUCTOR
    public BoletaVenta() {
        this.boletaVentaDetalles = new ArrayList<>();
    }

    // MÉTODOS
    public Double getBaseImponible() {
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.getBaseImponible())
                .sum();
    }

    public Double getImporteIgv() {
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.getImporteIgv())
                .sum();
    }

    public Double getTotal() {
         /*
        Double total = 0.0;
        for (BoletaVentaDetalle detalle : this.boletaVentaDetalles) {
            total += detalle.getTotal();
        }
        return total;
        */
        return this.boletaVentaDetalles
                .stream()
                .mapToDouble(value -> value.getTotalDetalle())
                .sum();
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

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<BoletaVentaDetalle> getBoletaVentaDetalles() {
        return boletaVentaDetalles;
    }

    public void setBoletaVentaDetalles(List<BoletaVentaDetalle> boletaVentaDetalles) {
        this.boletaVentaDetalles = boletaVentaDetalles;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
