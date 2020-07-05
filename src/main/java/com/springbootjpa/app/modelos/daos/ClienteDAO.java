package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;


public interface ClienteDAO{
    
    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findById(Long idCliente);
    public void delete(Long idCliente);
    
}
