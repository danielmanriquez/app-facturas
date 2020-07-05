package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IClienteDAO extends   JpaRepository<Cliente , Long >{
    
}
