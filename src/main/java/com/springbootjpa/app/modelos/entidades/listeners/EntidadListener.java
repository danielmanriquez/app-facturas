/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springbootjpa.app.modelos.entidades.listeners;

import com.springbootjpa.app.modelos.entidades.EntidadAbstracta;
import java.util.Date;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 *Clase Listener que se encarga de Asignar la fecha de creacion y la fecha de modificacion de una Entidad.
 * 
 * 
 * @author Daniel
 */
public class EntidadListener {
    
    
    @PrePersist
    public void fechaCreacion(EntidadAbstracta entidad){
    
        Date fechaCreacion = new Date();
        
        entidad.setFechaCreacion(fechaCreacion);
    
        
    }
    
    @PreUpdate
    public void fechaUltimaModificacion(EntidadAbstracta entidad){
    
        Date fechaUltimaModificacion = new Date();
        
        entidad.setFechaUltimaModificacion(fechaUltimaModificacion);
    
    }
    
    
    
}
