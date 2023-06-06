package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.dto.BoletaVentaInDto;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.service.venta.BoletaVentaService;
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
@RequestMapping(value = "/api/ventas")
public class VentaController {
    @Autowired
    private BoletaVentaService boletaVentaService;

    @GetMapping
    public ResponseEntity<List<BoletaVenta>> index() {
        return ResponseEntity.ok(this.boletaVentaService.findAll());
    }

    @GetMapping(value = "page/{page}")
    public ResponseEntity<Page<BoletaVenta>> index(@PathVariable Integer page) {
        return ResponseEntity.ok(this.boletaVentaService.findAll(page));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoletaVenta> show(@PathVariable("id") Long boletaVentaId) {
        BoletaVenta boletaVenta = this.boletaVentaService.findById(boletaVentaId);
        return new ResponseEntity<>(boletaVenta, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<?> store(@Valid @RequestBody BoletaVenta boletaVenta) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("id", this.boletaVentaService.save(boletaVenta));
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody BoletaVentaInDto boletaVenta) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.boletaVentaService.save(boletaVenta));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        this.boletaVentaService.deleteById(id);
    }

    @PutMapping(value = "/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable(value = "id") Long boletaVentaId) {
        this.boletaVentaService.anular(boletaVentaId);
        return ResponseEntity.noContent().build();
    }
}
