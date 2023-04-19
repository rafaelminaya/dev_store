package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.common.Marca;
import com.rminaya.dev.store.service.common.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/marcas")
public class MarcaController {
    @Autowired
    private MarcaService marcaService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Marca> listar() {
        return this.marcaService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Marca> obtener(@PathVariable("id") Long marcaId) {
        Marca marca = this.marcaService.findById(marcaId);
        return new ResponseEntity<>(marca, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> guardar(@RequestBody Marca marca) {
        Long marcaId = this.marcaService.save(marca);
        return new ResponseEntity<>(marcaId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.marcaService.deleteById(id);
    }
}
