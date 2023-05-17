package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.service.consignacion.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/proveedores")
public class ProveedorController {
    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<List<Proveedor>> index() {
        return ResponseEntity.ok(this.proveedorService.findAll());
    }

    @GetMapping(value = "page/{page}")
    public ResponseEntity<Page<Proveedor>> index(@PathVariable Integer page) {
        return ResponseEntity.ok(this.proveedorService.findAll(page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long proveedorId) {
        Proveedor proveedor = this.proveedorService.findById(proveedorId);
        return new ResponseEntity<>(proveedor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Proveedor proveedor) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.proveedorService.save(proveedor));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Proveedor proveedor, @PathVariable("id") Long proveedorId) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.proveedorService.update(proveedor, proveedorId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.proveedorService.deleteById(id);
    }

}
