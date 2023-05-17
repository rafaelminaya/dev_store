package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.consignacion.GuiaRemision;
import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.service.consignacion.GuiaRemisionService;
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
@RequestMapping(value = "/api/consignaciones")
public class ConsignacionController {
    @Autowired
    private GuiaRemisionService guiaRemisionService;

    @GetMapping
    public ResponseEntity<List<GuiaRemision>> index() {
        return ResponseEntity.ok(this.guiaRemisionService.findAll());
    }

    @GetMapping(value = "page/{page}")
    public ResponseEntity<Page<GuiaRemision>> index(@PathVariable Integer page) {
        return ResponseEntity.ok(this.guiaRemisionService.findAll(page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> show(@PathVariable("id") Long guiaRemisionId) {
        GuiaRemision guiaRemision = this.guiaRemisionService.findById(guiaRemisionId);
        return  new ResponseEntity<>(guiaRemision, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody GuiaRemision guiaRemision) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.guiaRemisionService.save(guiaRemision));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody GuiaRemision guiaRemision, @PathVariable("id") Long guiaRemisionId) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.guiaRemisionService.update(guiaRemision, guiaRemisionId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}/procesar-kardex")
    public ResponseEntity<?> procesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        this.guiaRemisionService.procesarKardex(guiaRemisionId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}/desprocesar-kardex")
    public ResponseEntity<?> desprocesarKardex(@PathVariable(value = "id") Long guiaRemisionId) {
        this.guiaRemisionService.desprocesarKardex(guiaRemisionId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.guiaRemisionService.deleteById(id);
    }

}
