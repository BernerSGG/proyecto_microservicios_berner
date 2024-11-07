package com.bg.microservicios.producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import com.bg.microservicios.producto.entity.Producto;
import com.bg.microservicios.producto.services.IProducto;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    private final IProducto productosService;
    private final RestTemplate restTemplate;

    @Autowired
    public ProductoController(IProducto productosService, RestTemplate restTemplate) {
        this.productosService = productosService;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public ResponseEntity<List<Producto>> getAllProductos() {
        List<Producto> productos = productosService.findAll();
        return productos.isEmpty() 
            ? ResponseEntity.noContent().build() 
            : ResponseEntity.ok(productos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Integer id) {
        return productosService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> saveProducto(@RequestBody Producto producto) {
        Producto savedProducto = productosService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProducto);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Producto> updateProducto(@PathVariable Integer id, @RequestBody Producto producto) {
        return productosService.findById(id)
                .map(existingProducto -> {
                    Producto updatedProducto = productosService.update(id, producto);
                    return ResponseEntity.ok(updatedProducto);
                })
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProducto(@PathVariable Integer id) {
        if (productosService.existsById(id)) {
            productosService.deleteById(id);
            return ResponseEntity.ok("Producto con ID " + id + " ha sido eliminado.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto con ID " + id + " no encontrado.");
        }
    }
    
    @GetMapping("/external/{id}")
    public ResponseEntity<Producto> getExternalProducto(@PathVariable Integer id) {
        String url = "http://localhost:8236/productos/" + id; 
        try {
            Producto producto = restTemplate.getForObject(url, Producto.class);
            return ResponseEntity.ok(producto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

