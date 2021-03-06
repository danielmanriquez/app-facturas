package com.springbootjpa.app.modelos.servicios;

import com.springbootjpa.app.modelos.daos.IProductoDAO;
import com.springbootjpa.app.modelos.entidades.Producto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class ProductoServicioImpl implements ProductoServicio {

    private final IProductoDAO productoDao;
    
    
    @Transactional(readOnly = true)
    @Override
    public List<Producto> listarTodosLosProductos() {
        
        return this.productoDao.findAll();
        
        
    }
    
    @Transactional(readOnly = true)
    @Override
    public List<Producto> buscarProductoPorNombre(String nombre) {

        return this.productoDao.findByNombreLikeIgnoreCase("%" + nombre + "%");
    }

    @Transactional(readOnly = true)
    @Override
    public Producto buscarProductoPorId(Long id) {

        return this.productoDao.findById(id).orElse(null);

    }
    
    @Transactional(readOnly = true)
    @Override
    public Page<Producto> listarTodosLosProductos(Pageable pageable) {
        
        return this.productoDao.findAll(pageable);
        
        
    }
    
    @Transactional
    @Override
    public void guardarProducto(Producto producto) {
        
        this.productoDao.save(producto);
    }

    

}
