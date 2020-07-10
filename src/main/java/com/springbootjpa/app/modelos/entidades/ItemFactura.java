/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springbootjpa.app.modelos.entidades;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "items_factura")
public class ItemFactura implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="id_item_factura")
    private Long idItemFactura ;
    
    @Column(name="cantidad")
    private Integer cantidad ;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_producto")
    private Producto producto;
    
    
    public Double calcularImporte(){
    
        return this.cantidad.doubleValue() * producto.getPrecio();
        
    }
   
    
    
    
    
    
}
