package com.rminaya.dev.store.model.dto;

import com.rminaya.dev.store.model.entity.venta.Cliente;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class BoletaVentaInDto {
    @NotBlank
    @Size(min = 3, max = 6, message = "debe tener entre 3 y 6 caracteres.")
    private String numero;
    @NotNull
    private ClienteInDto cliente;
    @NotNull
    private List<BoletaVentaDetalleInDto> boletaVentaDetalles;

    public BoletaVentaInDto() {
        this.boletaVentaDetalles = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public ClienteInDto getCliente() {
        return cliente;
    }

    public void setCliente(ClienteInDto cliente) {
        this.cliente = cliente;
    }

    public List<BoletaVentaDetalleInDto> getBoletaVentaDetalles() {
        return boletaVentaDetalles;
    }

    public void setBoletaVentaDetalles(List<BoletaVentaDetalleInDto> boletaVentaDetalles) {
        this.boletaVentaDetalles = boletaVentaDetalles;
    }
}
