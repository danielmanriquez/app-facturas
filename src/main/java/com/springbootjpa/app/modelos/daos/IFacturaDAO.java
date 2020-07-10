
package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IFacturaDAO extends JpaRepository<Factura , Long> {
    
}
