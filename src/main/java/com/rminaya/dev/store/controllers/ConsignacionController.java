package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.service.consignacion.GuiaRemisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/consignaciones")
public class ConsignacionController {
    @Autowired
    private GuiaRemisionService guiaRemisionService;

    @GetMapping
    public ResponseEntity<List<GuiaRemision>> listar() {
        return ResponseEntity.ok(this.guiaRemisionService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable("id") Long guiaRemisionId) {
        GuiaRemision guiaRemision = this.guiaRemisionService.findById(guiaRemisionId);
        return  new ResponseEntity<>(guiaRemision, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> guardar(@RequestBody GuiaRemision guiaRemision) {
        System.out.println("GUIA: " + guiaRemision);
        Long guiaRemisionId = this.guiaRemisionService.save(guiaRemision);
        return new ResponseEntity<>(guiaRemisionId, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{id}/procesar-kardex")
    public ResponseEntity<?> procesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        this.guiaRemisionService.procesarKardex(guiaRemisionId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/{id}/desprocesar-kardex")
    public ResponseEntity<?> desprocesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        this.guiaRemisionService.desprocesarKardex(guiaRemisionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.guiaRemisionService.deleteById(id);
    }

}
