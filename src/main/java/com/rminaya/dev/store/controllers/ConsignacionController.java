package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.exceptions.DevStoreExceptions;
import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.service.consignacion.GuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@CrossOrigin(origins = {"http://localhost:4200"})
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
        System.out.println("GUIA: " + guiaRemision);
        return this.guiaRemisionService.save(guiaRemision);
    }

    @PostMapping(value = "/{id}/procesar-kardex")
    public ResponseEntity<?> procesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        this.guiaRemisionService.procesarKardex(guiaRemisionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/desprocesar-kardex")
    @ResponseStatus(HttpStatus.CREATED)
    public Kardex desprocesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        return this.guiaRemisionService.desprocesarKardex(guiaRemisionId);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.guiaRemisionService.deleteById(id);
    }

}
