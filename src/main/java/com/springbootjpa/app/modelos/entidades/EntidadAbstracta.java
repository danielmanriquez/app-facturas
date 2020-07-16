package com.springbootjpa.app.modelos.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.springbootjpa.app.modelos.entidades.listeners.EntidadListener;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

/**
 *Clase abstracta que define los atributos comunes de las entidades.
 * @author Daniel
 */

@Getter
@Setter
@MappedSuperclass
@EntityListeners(EntidadListener.class)
public class EntidadAbstracta {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    
    @CreatedDate
    @Column(name = "fecha_creacion"  , nullable = false)
    @JsonIgnore
    private Date fechaCreacion ; 
    
    
    @LastModifiedDate
    @Column(name="fecha_ultima_modificacion")
    @JsonIgnore
    private Date fechaUltimaModificacion ;
    
    
}
