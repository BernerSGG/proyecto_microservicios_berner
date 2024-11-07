package com.bg.microservicios.Facturas.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.bg.microservicios.Facturas.Dto.ClienteDto;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private RestTemplate restTemplate;

    private final String CLIENTES_SERVICE_URL = "http://localhost:8999/clientes/";

    public Optional<ClienteDto> getCliente(String nit) {
        try {
            return Optional.ofNullable(restTemplate.getForObject(CLIENTES_SERVICE_URL + nit, ClienteDto.class));
        } catch (Exception e) {
            throw new RuntimeException("Error al obtener el cliente: " + e.getMessage(), e);
        }
    }
}
