package com.springbootjpa.app.modelos.entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_cliente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    
    
    @NotBlank
    @Column(name = "nombre")
    private String nombre;
    
    @NotBlank
    @Column(name = "apellido")
    private String apellido;
    
    @NotBlank
    @Email
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "creado_en")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date creadoEn;

//     @PrePersist
//     public void antesDePersistir(){
//         
//         creadoEn = new Date();
//         
//     }
}
