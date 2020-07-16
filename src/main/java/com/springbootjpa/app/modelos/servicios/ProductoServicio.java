package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.entidades.Producto;
import java.util.List;


public interface ProductoServicio {
    
    public List<Producto> listarTodosLosProducto();
    
    public List<Producto> buscarProductoPorNombre(String nombre);
    
    public Producto buscarProductoPorId(Long id );
    
    
}
