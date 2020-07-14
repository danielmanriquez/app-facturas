
package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Factura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IFacturaDAO extends JpaRepository<Factura , Long> {
    
    @Query("select f from Factura f join fetch f.cliente c join fetch f.itemsFactura l join fetch l.producto where f.id=?1 ")
    public Factura fetchByIDWithClienteWhitItemFacturaWithProducto(Long id);
    
}
