package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Producto;
import com.rminaya.dev.store.service.common.ProductoService;
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
@RequestMapping(value = "/api/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<List<Producto>> index() {
        return ResponseEntity.ok(this.productoService.findAll());
    }

    @GetMapping(value = "page/{page}")
    public ResponseEntity<Page<Producto>> index(@PathVariable Integer page) {
        return ResponseEntity.ok(this.productoService.findAll(page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> show(@PathVariable("id") Long productoId) {
        Producto producto = this.productoService.findById(productoId);
        return ResponseEntity.ok(producto);
    }

    @GetMapping("/{id}/by-marca")
    public ResponseEntity<List<Producto>> getProductosByMarcaId(@PathVariable("id") Long marcaId){
        return ResponseEntity.ok(this.productoService.findByMarcaId(marcaId));
    }

    @GetMapping("/by-filtro-venta")
    public ResponseEntity<List<Producto>> getProductosByFiltroVenta(@RequestParam("termino") String termino){
        return ResponseEntity.ok(this.productoService.findByFiltroVenta(termino));
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Producto producto) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.productoService.save(producto));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody  Producto producto, @PathVariable("id") Long productoId) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.productoService.update(producto, productoId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.productoService.deleteById(id);
    }
}
