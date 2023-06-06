package com.rminaya.dev.store.model.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@MappedSuperclass
public class Persona {
    //ATRIBUTOS
    @NotEmpty(message = "no puede estar vacio.")
    @Size( min = 8, max = 13, message = "debe tener entre 8 y 13 caracteres.")
    protected String numeroDocumento;
    @NotEmpty(message = "no puede estar vacio.")
    @Size( min = 2, max = 255, message = "debe tener entre 2 y 255 caracteres.")
    protected String nombre;
    @NotNull
    protected String direccion;
    protected String telefono;
    @Column(columnDefinition = "boolean default false")
    protected Boolean eliminado = false;

    // GETTERS AND SETTERS
    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
