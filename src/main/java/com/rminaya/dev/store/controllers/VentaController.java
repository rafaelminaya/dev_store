package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.almacen.Kardex;
import com.rminaya.dev.store.model.entity.almacen.KardexDetalle;
import com.rminaya.dev.store.model.entity.venta.BoletaVenta;
import com.rminaya.dev.store.model.entity.venta.BoletaVentaDetalle;
import com.rminaya.dev.store.service.venta.BoletaVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> crear(@RequestBody BoletaVenta boletaVenta) {

        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", this.boletaVentaService.save(boletaVenta));

//        return this.boletaVentaService.save(boletaVenta);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.boletaVentaService.deleteById(id);
    }
}
