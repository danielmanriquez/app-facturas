/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springbootjpa.app.modelos.daos;

import com.springbootjpa.app.modelos.entidades.Producto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface IProductoDAO extends JpaRepository <Producto , Long>{
    
    /**
     * Metodo para buscar un producto por nombre usando etiqueta query y jpql
     * @param nombre
     * @return List Producto
     */
    @Query("select p from Producto p where p.nombre like %?1% ")
    public List<Producto> findByNombre(String nombre);
    
    /**
     *Metodo para buscar un producto por nombre usando el mecanismo de Spring Data Query Builder 
     * @param nombre
     * @return List Producto
     */
    public List<Producto> findByNombreLikeIgnoreCase(String nombre);
}
