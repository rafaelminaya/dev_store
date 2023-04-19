package com.rminaya.dev.store.controllers;

import com.rminaya.dev.store.model.entity.venta.Cliente;
import com.rminaya.dev.store.service.venta.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping(value = "/api/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> listar() {
        return this.clienteService.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable("id") Long clienteId) {
        Cliente cliente = this.clienteService.findById(clienteId);
        return new ResponseEntity<>(cliente, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> guardar(@RequestBody Cliente cliente) {
        Long clienteId = this.clienteService.save(cliente);
        return new ResponseEntity<>(clienteId, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable("id") Long clienteId) {
        this.clienteService.deleteById(clienteId);
    }
}
