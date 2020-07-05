
package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.entidades.Cliente;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ClienteServicio {
    
   public List<Cliente> listarClientes();
    public Page<Cliente> listarClientes(Pageable  pageable);
   public void guardarCliente(Cliente cliente);
   public Cliente buscarClientePorId(Long idCliente);
   public void eliminarCliente(Long idCliente);
   
    
    
}
