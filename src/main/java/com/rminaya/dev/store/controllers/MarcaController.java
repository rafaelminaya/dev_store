package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.service.common.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/api/marcas")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Marca> listar() {
        return this.marcaService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Marca marca;
        try {
            marca = this.marcaService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(marca);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Marca guardar(@RequestBody Marca marca) {
        return this.marcaService.save(marca);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.marcaService.deleteById(id);
    }
}
