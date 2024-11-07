package com.bg.microservicios.producto.repositorio;

import org.springframework.data.repository.CrudRepository;
import com.bg.microservicios.producto.entity.Producto;

public interface ProductoRepositorio extends CrudRepository<Producto, Integer> {
}
