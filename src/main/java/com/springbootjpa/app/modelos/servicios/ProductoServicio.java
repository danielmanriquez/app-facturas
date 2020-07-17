package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.entidades.Producto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductoServicio {

    public Page<Producto> listarTodosLosProductos(Pageable pageable);

    public List<Producto> listarTodosLosProductos();

    public List<Producto> buscarProductoPorNombre(String nombre);

    public Producto buscarProductoPorId(Long id);
    
    public void guardarProducto(Producto producto);

}
