package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.Proveedor;
import com.rminaya.dev.store.service.consignacion.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.proveedorService.deleteById(id);
    }

}
