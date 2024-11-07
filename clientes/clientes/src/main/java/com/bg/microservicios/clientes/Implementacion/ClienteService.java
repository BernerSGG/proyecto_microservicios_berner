package com.bg.microservicios.clientes.Implementacion;

import java.util.List;
import java.util.Optional;

import com.bg.microservicios.clientes.entity.Cliente;
import com.bg.microservicios.clientes.repositorio.ClienteRepositorio;
import com.bg.microservicios.clientes.services.ICliente;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService implements ICliente {

    private final ClienteRepositorio clienteRepositorio;

    @Autowired
    public ClienteService(ClienteRepositorio clienteRepositorio) {
        this.clienteRepositorio = clienteRepositorio;
    }

    @Override
    public List<Cliente> findAll() {
        return (List<Cliente>) clienteRepositorio.findAll();
    }

    @Override
    public Optional<Cliente> findById(Integer id) {
        return clienteRepositorio.findById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepositorio.save(cliente);
    }

    @Override
    public Cliente update(Integer id, Cliente cliente) {
        Cliente existingCliente = clienteRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con ID " + id));
        
        existingCliente.setNombre(cliente.getNombre());
        existingCliente.setEmail(cliente.getEmail());
        existingCliente.setTelefono(cliente.getTelefono());
        existingCliente.setDireccion(cliente.getDireccion());
        existingCliente.setNit(cliente.getNit());
        
        return clienteRepositorio.save(existingCliente);
    }

    @Override
    public void deleteById(Integer id) {
        if (!clienteRepositorio.existsById(id)) {
            throw new EntityNotFoundException("Cliente con ID " + id + " no encontrado");
        }
        clienteRepositorio.deleteById(id);
    }
    
    @Override
    public boolean existsById(Integer id) {
        return clienteRepositorio.existsById(id);
    }

    @Override
    public Optional<Cliente> findByNit(String nit) {
        return clienteRepositorio.findByNit(nit);
    }
}