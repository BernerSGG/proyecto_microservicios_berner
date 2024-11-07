package com.bg.microservicios.clientes.services;

import java.util.List;
import java.util.Optional;
import com.bg.microservicios.clientes.entity.Cliente;

public interface ICliente {

    List<Cliente> findAll();
    Optional<Cliente> findById(Integer id);
    Cliente save(Cliente cliente);
    Cliente update(Integer id, Cliente cliente);
    void deleteById(Integer id);
    boolean existsById(Integer id);

    Optional<Cliente> findByNit(String nit);
}
