package com.bg.microservicios.Facturas.controller;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.bg.microservicios.Facturas.Dto.ClienteDto;
import com.bg.microservicios.Facturas.Dto.ProductoDto;
import com.bg.microservicios.Facturas.Services.IFactura;
import com.bg.microservicios.Facturas.entity.Factura;

@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    private IFactura facturaService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public List<Factura> getAllFacturas() {
        return facturaService.findAll();
    }

    @GetMapping("/clientes/{nit}")
    public ResponseEntity<Factura> getFacturaByNit(@PathVariable String nit) {
        Optional<Factura> factura = facturaService.findByNit(nit);
        return factura.map(ResponseEntity::ok)
                      .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/facturas")
    public ResponseEntity<Factura> crearFactura(@RequestBody Factura factura) {
        // Imprimir el NIT para depuración
        System.out.println("Creando factura para NIT: " + factura.getNit());

        ClienteDto cliente = getCliente(factura.getNit());
        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        factura.calcularTotal();
        Factura nuevaFactura = facturaService.save(factura);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFactura);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Factura> updateFactura(@PathVariable Integer id, @RequestBody Factura factura) {
        Factura updatedFactura = facturaService.update(id, factura);
        return updatedFactura != null
            ? ResponseEntity.ok(updatedFactura)
            : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFactura(@PathVariable Integer id) {
        if (facturaService.deleteById(id) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/todos-clientes") // Cambiado aquí
    public ResponseEntity<List<ClienteDto>> getAllClientes() {
        return getExternalData("http://localhost:8999/clientes", new ParameterizedTypeReference<List<ClienteDto>>() {});
    }

    @GetMapping("/productos")
    public ResponseEntity<List<ProductoDto>> getAllProductos() {
        return getExternalData("http://localhost:8236/productos", new ParameterizedTypeReference<List<ProductoDto>>() {});
    }

    private <T> ResponseEntity<List<T>> getExternalData(String url, ParameterizedTypeReference<List<T>> responseType) {
        try {
            ResponseEntity<List<T>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
            return response.getStatusCode().is2xxSuccessful() ? ResponseEntity.ok(response.getBody())
                : ResponseEntity.status(response.getStatusCode()).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    private ClienteDto getCliente(String nit) {
        String url = "http://localhost:8999/clientes/" + nit;
        try {
            return restTemplate.getForObject(url, ClienteDto.class);
        } catch (HttpClientErrorException.NotFound e) {
            System.err.println("Cliente con NIT " + nit + " no encontrado");
            return null; // Retorna null si el cliente no existe
        } catch (HttpClientErrorException e) {
            System.err.println("Error al obtener cliente: " + e.getStatusCode());
            return null; // O lanza una excepción personalizada
        } catch (Exception e) {
            System.err.println("Error interno al acceder al microservicio de clientes");
            return null;
        }
    }

}
