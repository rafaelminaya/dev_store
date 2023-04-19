package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.service.consignacion.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Proveedor> listar() {
        return this.proveedorService.findAll();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable("id") Long proveedorId) {
        Proveedor proveedor = this.proveedorService.findById(proveedorId);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> guardar(@RequestBody Proveedor proveedor) {
        Long proveedorId = this.proveedorService.save(proveedor);
        return new ResponseEntity<>(proveedorId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.proveedorService.deleteById(id);
    }

}
