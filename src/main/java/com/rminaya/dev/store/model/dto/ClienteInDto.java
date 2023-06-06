package com.rminaya.dev.store.model.dto;

import javax.validation.constraints.NotBlank;

public class ClienteInDto {
    @NotBlank
    private String numeroDocumento;
    @NotBlank
    private String nombre;
    private String direccion;

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
}
