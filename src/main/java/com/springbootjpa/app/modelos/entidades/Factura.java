

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
import lombok.AllArgsConstructor;
import lombok.Data;

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
    
    
    public void anadirItemFactura(ItemFactura itemFactura){
    
        this.itemsFactura.add(itemFactura);
    
    }
    
    public Double getTotal(){
    
        Double total = 0.0 ; 
        
       for (ItemFactura item : this.itemsFactura){
       
           total += item.calcularImporte();
       
       }
        
        return total ;
    }
    
    @PrePersist
     public void antesDePersistir(){
         
         this.fechaCreacion = new Date();
         
     }
    
}
