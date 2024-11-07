package com.bg.microservicios.clientes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.bg.microservicios.clientes.entity.Cliente;
import com.bg.microservicios.clientes.services.ICliente;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    
    @Autowired
    private RestTemplate restTemplate;

    private final ICliente clientesService;

    @Autowired
    public ClienteController(ICliente clientesService, RestTemplate restTemplate) {
        this.clientesService = clientesService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Cliente>> getAllClientes() {
        List<Cliente> clientes = clientesService.findAll();
        return clientes.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Integer id) {
        if (id == null || id < 1) { 
            return ResponseEntity.badRequest().body(null);
        }
        return clientesService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    @PostMapping("/save")
    public ResponseEntity<Cliente> save(@RequestBody Cliente cliente) {
        Cliente savedCliente = clientesService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCliente);
    }

    @PutMapping(path = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Cliente> update(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente updatedCliente = clientesService.update(id, cliente);
            return ResponseEntity.ok(updatedCliente);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable Integer id) {
        if (clientesService.existsById(id)) {
            clientesService.deleteById(id);
            return ResponseEntity.ok("Cliente con ID " + id + " ha sido eliminado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente con ID " + id + " no encontrado.");
        }
    }

    @GetMapping("/external/{nit}")
    public ResponseEntity<Cliente> getExternalCliente(@PathVariable String nit) {
        String url = "http://localhost:8999/clientes/" + nit;
        Cliente cliente = restTemplate.getForObject(url, Cliente.class);
        return cliente != null ? ResponseEntity.ok(cliente) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}