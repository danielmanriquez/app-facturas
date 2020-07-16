package com.springbootjpa.app.modelos.entidades;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 *Clase de Entidad que represanta la tabla "producto" de la base de datos 
 * tiene una relacion unidireccional con itemFactura.
 * @author Daniel
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "productos")
public class Producto extends EntidadAbstracta {

    @Column(name ="nombre")
    private String nombre;
    
    @Column(name="precio")
    private Double precio;
    

}
