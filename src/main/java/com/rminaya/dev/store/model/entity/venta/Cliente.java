package com.rminaya.dev.store.model.entity.venta;

import com.rminaya.dev.store.model.entity.common.Persona;

import javax.persistence.*;

@Entity
@Table(name = "clientes")
public class Cliente extends Persona {
    //ATRIBUTOS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // GETTERS AND SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", numeroDocumento='" + numeroDocumento + '\'' +
                ", nombre='" + nombre + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", eliminado=" + eliminado +
                '}';
    }
}
