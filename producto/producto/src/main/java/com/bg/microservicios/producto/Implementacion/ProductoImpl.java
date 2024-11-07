package com.bg.microservicios.producto.Implementacion;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bg.microservicios.producto.entity.Producto;
import com.bg.microservicios.producto.repositorio.ProductoRepositorio;
import com.bg.microservicios.producto.services.IProducto;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductoImpl implements IProducto {
    
    @Autowired
    private ProductoRepositorio productoRepositorio;

    @Override
    public List<Producto> findAll() {
        return (List<Producto>) productoRepositorio.findAll();
    }

    @Override
    public Optional<Producto> findById(Integer idProducto) {
        return productoRepositorio.findById(idProducto);
    }

    @Override
    public Producto save(Producto producto) {
        return productoRepositorio.save(producto);
    }

    @Override
    public Producto update(Integer idProducto, Producto producto) {
        return productoRepositorio.findById(idProducto).map(existingProducto -> {
            existingProducto.setProducto(producto.getProducto());
            existingProducto.setDescripcion(producto.getDescripcion());
            existingProducto.setPrecio(producto.getPrecio());
            existingProducto.setStock(producto.getStock());
            return productoRepositorio.save(existingProducto);
        }).orElseThrow(() -> new EntityNotFoundException("Producto con ID " + idProducto + " no encontrado"));
    }

    @Override
    public void deleteById(Integer idProducto) {
        if (!productoRepositorio.existsById(idProducto)) {
            throw new EntityNotFoundException("Producto con ID " + idProducto + " no encontrado");
        }
        productoRepositorio.deleteById(idProducto);
    }

    @Override
    public boolean existsById(Integer idProducto) {
        return productoRepositorio.existsById(idProducto);
    }
}
