package com.bg.microservicios.producto.services;

import java.util.List;
import java.util.Optional;
import com.bg.microservicios.producto.entity.Producto;

public interface IProducto {
    List<Producto> findAll();
    Optional<Producto> findById(Integer idProducto);
    Producto save(Producto producto);
    Producto update(Integer idProductos, Producto producto);
    void deleteById(Integer id);
    boolean existsById(Integer idProducto);
}
