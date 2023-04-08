package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.service.venta.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping(value = "/listar")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listar() {
        return this.clienteService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Cliente cliente;
        try {
            cliente = this.clienteService.findById(id);
        } catch (NoSuchElementException ex) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(cliente);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente guardar(@RequestBody Cliente cliente) {
        return this.clienteService.save(cliente);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        this.clienteService.deleteById(id);
    }
}
