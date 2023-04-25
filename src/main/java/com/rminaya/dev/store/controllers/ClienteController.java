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
    public ResponseEntity<List<Cliente>> index() {
        return ResponseEntity.ok(this.clienteService.findAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> show(@PathVariable("id") Long clienteId) {
        Cliente cliente = this.clienteService.findById(clienteId);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody Cliente cliente) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.clienteService.save(cliente));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Cliente cliente, @PathVariable("id") Long clienteId) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", this.clienteService.update(cliente, clienteId));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long clienteId) {
        this.clienteService.deleteById(clienteId);
    }
}
