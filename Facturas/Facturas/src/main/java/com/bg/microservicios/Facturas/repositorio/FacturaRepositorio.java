package com.bg.microservicios.Facturas.repositorio;

import com.bg.microservicios.Facturas.entity.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Integer> {
    Optional<Factura> findByNit(String nit); 

}
