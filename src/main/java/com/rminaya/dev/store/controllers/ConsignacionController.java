package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.service.consignacion.GuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/consignaciones")
public class ConsignacionController {

    @Autowired
    private GuiaRemisionService guiaRemisionService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<GuiaRemision> listar() {
        return this.guiaRemisionService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        GuiaRemision guiaRemision;
        try {
            guiaRemision = this.guiaRemisionService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(guiaRemision);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GuiaRemision guardar(@RequestBody GuiaRemision guiaRemision) {
        return this.guiaRemisionService.save(guiaRemision);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.guiaRemisionService.deleteById(id);
    }

}
