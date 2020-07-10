package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;

/**Interface que define los metodos para la capa de datos hacia la entidad Cliente**/
public interface ClienteDAO{
    
    public List<Cliente> findAll();
    public void save(Cliente cliente);
    public Cliente findById(Long idCliente);
    public void delete(Long idCliente);
    
}
