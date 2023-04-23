package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.service.venta.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<Cliente>> listar() {
        return ResponseEntity.ok(this.clienteService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable("id") Long clienteId) {
        Cliente cliente = this.clienteService.findById(clienteId);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.clienteService.save(cliente));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long clienteId) {
        this.clienteService.deleteById(clienteId);
    }
}
