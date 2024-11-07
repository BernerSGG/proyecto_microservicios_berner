package com.bg.microservicios.Facturas.Services;

import java.util.List;
import java.util.Optional;
import com.bg.microservicios.Facturas.entity.Factura;


public interface IFactura {
    
    List<Factura> findAll();
    Optional<Factura> findByNit(String nit);
    Factura save(Factura factura);
    Factura update(Integer id, Factura factura);
    Integer deleteById(Integer id);
}

