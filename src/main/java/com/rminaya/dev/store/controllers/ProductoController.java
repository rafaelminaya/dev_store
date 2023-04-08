package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.service.common.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Producto> listar() {
        return this.productoService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Producto producto;
        try {
            producto = this.productoService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(producto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Producto guardar(@RequestBody Producto producto) {
        return this.productoService.save(producto);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.productoService.deleteById(id);
    }
}
