package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.service.common.ProductoService;
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
@RequestMapping(value = "/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> index() {
        return ResponseEntity.ok(this.productoService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> show(@PathVariable("id") Long productoId) {
        Producto producto = this.productoService.findById(productoId);
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.productoService.save(producto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.productoService.deleteById(id);
    }
}
