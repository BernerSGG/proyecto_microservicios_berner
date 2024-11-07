package com.bg.microservicios.Facturas.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bg.microservicios.Facturas.Dto.ProductoDto;
import com.bg.microservicios.Facturas.entity.Producto;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private RestTemplate restTemplate;

    private final String PRODUCTOS_SERVICE_URL = "http://localhost:8236/productos/";

    public Optional<ProductoDto> getProducto(List<Producto> list) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(PRODUCTOS_SERVICE_URL + list, ProductoDto.class));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el producto: " + e.getMessage(), e);
        }
    }
}
