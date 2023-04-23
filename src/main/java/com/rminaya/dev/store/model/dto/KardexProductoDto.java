package com.rminaya.dev.store.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class KardexProductoDto {
    // ATRIBUTOS
    @NotNull(message = "no debe ser vacío.")
    @Min(value = 1, message = "debe ser un ID mayor a 1.")
    private Long productoId;


    // GETTERS AND SETTERS
    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

}
