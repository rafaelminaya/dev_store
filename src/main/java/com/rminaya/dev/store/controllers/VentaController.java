package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.service.venta.BoletaVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/ventas")
public class VentaController {
    @Autowired
    private BoletaVentaService boletaVentaService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<BoletaVenta> listar() {
        return this.boletaVentaService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        BoletaVenta boletaVenta;
        try {
            boletaVenta = this.boletaVentaService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(boletaVenta);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BoletaVenta crear(@RequestBody BoletaVenta boletaVenta) {
        return this.boletaVentaService.save(boletaVenta);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.boletaVentaService.deleteById(id);
    }
}
