

package com.springbootjpa.app.modelos.entidades;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 *Clase de Entidad que represanta la tabla "facturas" de la base de datos 
 * tiene una relacion bidireccional con factura , en la cual una Factura puede tener un solo Cliente
 * Tambien tiene una relacion unidireccional con ItemFactura , donde solo puede existir este 
 * objeto dentro de la clase Factura.
 * 
 * @author Daniel Manriquez
 */
@Data
@AllArgsConstructor
@Entity
@Table(name ="facturas")
public class Factura implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_factura")
    private Long idFactura;
    
    @NotBlank
    @Column(name = "descripcion")
    private String descripcion;
    
    @Column(name= "observacion")
    private String observacion ;
    
    @Temporal(TemporalType.DATE)
    @Column(name="fecha_creacion")
    private Date fechaCreacion;
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Cliente cliente ;
    
    @OneToMany(fetch = FetchType.LAZY , cascade = CascadeType.ALL)
    @JoinColumn(name="factura_id") //Relacion en un solo sentido .
    private List <ItemFactura> itemsFactura ;
    
    public Factura(){
    
        this.itemsFactura = new ArrayList();
        
    }
    
    /**
     *Metodo para añadir un ItemFactura a la lista de itemsFactura , de la actual Factura. 
     * @param itemFactura
     */
    public void anadirItemFactura(ItemFactura itemFactura){
    
        this.itemsFactura.add(itemFactura);
    
    }
    
    /**
     *Metodo que devuelve el importe total de la factura 
     * @return Double
     */
    public Double getTotal(){
    
        Double total = 0.0 ; 
        
       for (ItemFactura item : this.itemsFactura){
       
           total += item.calcularImporte();
       
       }
        
        return total ;
    }
    
    
    
    /**
     *Metodo que se ejecuta justo antes de persistir en la base de datos  , le asigna la fecha actual al objeto fechaCreacion y luego lo persiste.
     * 
     */
    @PrePersist
     public void antesDePersistir(){
         
         this.fechaCreacion = new Date();
         
     }
    
}
