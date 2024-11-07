package com.bg.microservicios.clientes.repositorio;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.bg.microservicios.clientes.entity.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Integer> {
    Optional<Cliente> findByNit(String nit);
}
