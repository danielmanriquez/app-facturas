package com.springbootjpa.app.modelos.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
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
    
    
    @NotBlank
    @Column(name ="nombre")
    private String nombre;
    
    @DecimalMin(value = "1.0", inclusive = false)
    @DecimalMax(value = "99999.9")
    @NotNull
    @Column(name="precio")
    private Double precio;
    

}
