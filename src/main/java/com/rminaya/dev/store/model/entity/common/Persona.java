package com.rminaya.dev.store.model.entity.common;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Persona {
    //ATRIBUTOS
    protected String dni;
    protected String nombre;
    protected String direccion;
    protected String telefono;

    // GETTERS AND SETTERS
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
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
}
