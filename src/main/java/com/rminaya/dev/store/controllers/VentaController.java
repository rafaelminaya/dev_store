package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.service.venta.BoletaVentaService;
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
@RequestMapping(value = "/api/ventas")
public class VentaController {
    @Autowired
    private BoletaVentaService boletaVentaService;

    @GetMapping
    public ResponseEntity<List<BoletaVenta>> listar() {
        return ResponseEntity.ok(this.boletaVentaService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<BoletaVenta> obtener(@PathVariable("id") Long boletaVentaId) {
        BoletaVenta boletaVenta = this.boletaVentaService.findById(boletaVentaId);
        return new ResponseEntity<>(boletaVenta, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> crear(@RequestBody BoletaVenta boletaVenta) {
        Long boletaVentaId = this.boletaVentaService.save(boletaVenta);
        return new ResponseEntity<>(boletaVentaId, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.boletaVentaService.deleteById(id);
    }

    @PostMapping(value = "/{id}/anular")
    public ResponseEntity<?> anular(@PathVariable(value = "id") Long boletaVentaId) {
        this.boletaVentaService.anular(boletaVentaId);
        return ResponseEntity.noContent().build();
    }
}
