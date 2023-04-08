package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.service.consignacion.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Proveedor> listar() {
        return this.proveedorService.findAll();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Proveedor proveedor;
        try {
            proveedor = this.proveedorService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Proveedor guardar(@RequestBody Proveedor proveedor) {
        return this.proveedorService.save(proveedor);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.proveedorService.deleteById(id);
    }

}
