package com.rminaya.dev.store.model.entity.consignacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "guia_remision")
public class GuiaRemision {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no puede estar vacio.")
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @NotNull(message = "no puede estar vacio.")
    //@Column(name = "fecha_emision", columnDefinition = "DATETIME")
    //@Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(pattern = "yyyy-MM-ddTHH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDate fechaEmision;
    @NotNull(message = "no puede estar vacio.")
    @Min(value = 0, message = "no debe ser menor que 0.")
    @Max(value = 100, message = "no debe ser mayor que 100.")
    private Double porcentajeComision;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proveedor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Proveedor proveedor;
    @NotNull
    @OneToMany(mappedBy = "guiaRemision",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "guia_remision_id")
    @JsonIgnoreProperties(value = {"guiaremision", "hibernateLazyInitializer", "handler"}, allowSetters = true)
    private List<GuiaRemisionDetalle> guiaRemisionDetalles;
    @Column(columnDefinition = "boolean default false")
    private Boolean procesado = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    // CONSTRUCTOR
    public GuiaRemision() {
        this.guiaRemisionDetalles = new ArrayList<>();
    }

    // MÉTODOS
    public void addDetalle(GuiaRemisionDetalle guiaRemisionDetalle) {
        this.guiaRemisionDetalles.add(guiaRemisionDetalle);
    }

    public Double getTotalPrecioCompra() {
        return this.guiaRemisionDetalles
                .stream()
                .mapToDouble(value -> value.getPrecioCompra() * value.getCantidad())
                .sum();
    }

    public Double getTotalPrecioVenta() {
        return this.guiaRemisionDetalles
                .stream()
                .mapToDouble(value -> value.getPrecioVenta() * value.getCantidad())
                .sum();
    }

    public Double getTotalImporteComision() {
        return this.getTotalPrecioVenta() - this.getTotalPrecioCompra();
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

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
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

    public Boolean getProcesado() {
        return procesado;
    }

    public void setProcesado(Boolean procesado) {
        this.procesado = procesado;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }

    @Override
    public String toString() {
        return "GuiaRemision{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", fechaEmision=" + fechaEmision +
                ", porcentajeComision=" + porcentajeComision +
                ", proveedor=" + proveedor +
                ", guiaRemisionDetalles=" + guiaRemisionDetalles +
                ", procesado=" + procesado +
                ", eliminado=" + eliminado +
                '}';
    }
}
