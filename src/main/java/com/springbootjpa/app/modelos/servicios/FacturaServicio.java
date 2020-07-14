
package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.entidades.Factura;


public interface FacturaServicio {
    
    public void guardarFactura (Factura factura );
    
    public Factura buscarFacturaPorId(Long idFactura);
    
    public void borrarFacturaPorId(Long idFactura);
    
    public Factura buscarFacturaConClienteConItemsFacturaConProductos(Long id);
    
}
