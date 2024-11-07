package com.bg.microservicios.Facturas.Implementacion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bg.microservicios.Facturas.Dto.ClienteDto;
import com.bg.microservicios.Facturas.Dto.ProductoDto;
import com.bg.microservicios.Facturas.Services.IFactura;
import com.bg.microservicios.Facturas.entity.Factura;
import com.bg.microservicios.Facturas.entity.Producto;
import com.bg.microservicios.Facturas.repositorio.FacturaRepositorio;

import java.util.List;
import java.util.Optional;

@Service
public class FacturaService implements IFactura {

    @Autowired
    private FacturaRepositorio facturaRepositorio;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ProductoService productoService;

    @Override
    public List<Factura> findAll() {
        return facturaRepositorio.findAll();
    }

    @Override
    public Optional<Factura> findByNit(String nit) {
        return facturaRepositorio.findByNit(nit);
    }

    @Override
    public Factura save(Factura factura) {
        Optional<ClienteDto> cliente = clienteService.getCliente(factura.getNit());
        if (cliente.isEmpty()) {
            throw new ClienteNotFoundException(factura.getNit());
        }


        Optional<ProductoDto> producto = productoService.getProducto(factura.getProductos());
        if (producto.isEmpty()) {
            throw new ProductoNotFoundException(factura.getProductos());
        }

        return facturaRepositorio.save(factura);
    }

    @Override
    public Factura update(Integer id, Factura factura) {
        if (facturaRepositorio.existsById(id)) {
            factura.setId(id);
            return facturaRepositorio.save(factura);
        }
        throw new FacturaNotFoundException(id);
    }

    @Override
    public Integer deleteById(Integer id) {
        if (facturaRepositorio.existsById(id)) {
            facturaRepositorio.deleteById(id);
            return id;
        }
        throw new FacturaNotFoundException(id);
    }


    public static class ClienteNotFoundException extends RuntimeException {
        public ClienteNotFoundException(String nit) {
            super("Cliente no encontrado con NIT: " + nit);
        }
    }

    public static class ProductoNotFoundException extends RuntimeException {
        public ProductoNotFoundException(List<Producto> list) {
            super("Producto no encontrado con ID: " + list);
        }
    }

    public static class FacturaNotFoundException extends RuntimeException {
        public FacturaNotFoundException(Integer id) {
            super("Factura no encontrada con ID: " + id);
        }
    }
}