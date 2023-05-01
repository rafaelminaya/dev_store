package com.rminaya.dev.store.model.entity.consignacion;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "proveedores")
public class Proveedor {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 11, max = 11,message = "solo puede ser de 11 caracteres.")
    private String ruc;
    @NotEmpty(message = "no debe ser vacío.")
    @Size(min = 3, max = 255,message = "debe tener entre 3 y 255 caracteres.")
    @Column(name = "razon_comercial")
    private String razonComercial;
    private String email;
    @NotNull
    private String direccion;
    @NotNull
    private String telefono;
    @Column(columnDefinition = "boolean default false")
    private Boolean eliminado = false;

    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getRazonComercial() {
        return razonComercial;
    }

    public void setRazonComercial(String razonComercial) {
        this.razonComercial = razonComercial;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getEliminado() {
        return eliminado;
    }

    public void setEliminado(Boolean eliminado) {
        this.eliminado = eliminado;
    }
}
