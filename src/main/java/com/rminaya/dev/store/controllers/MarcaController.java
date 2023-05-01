package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.service.common.MarcaService;
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
@RequestMapping(value = "/api/marcas")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<Marca>> index() {
        return ResponseEntity.ok(this.marcaService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Marca> show(@PathVariable("id") Long marcaId) {
        Marca marca = this.marcaService.findById(marcaId);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Marca marca) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.marcaService.save(marca));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Marca marca, @PathVariable("id") Long marcaId) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.marcaService.update(marca, marcaId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.marcaService.deleteById(id);
    }
}
