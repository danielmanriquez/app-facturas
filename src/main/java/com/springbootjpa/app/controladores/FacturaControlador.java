/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.springbootjpa.app.controladores;

import com.springbootjpa.app.modelos.entidades.Cliente;
import com.springbootjpa.app.modelos.entidades.Factura;
import com.springbootjpa.app.modelos.entidades.ItemFactura;
import com.springbootjpa.app.modelos.entidades.Producto;
import com.springbootjpa.app.modelos.servicios.ClienteServicio;
import com.springbootjpa.app.modelos.servicios.FacturaServicio;
import com.springbootjpa.app.modelos.servicios.ProductoServicio;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor // para que inyecte las dependencias de spring
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaControlador {
    
    private final ProductoServicio productoServicio;
    
    private final FacturaServicio facturaServicio;
    
    private final ClienteServicio clienteServicio;
    
    
    @GetMapping(value ="/cargarProductos/{term}" , produces = {"application/json"})
    public @ResponseBody List<Producto> cargarProductos(@PathVariable String term){
        
        return this.productoServicio.buscarProductoPorNombre(term);
        
    }
    
    
    @GetMapping("/formularioFactura/{idCliente}")
    public String crearFactura(@PathVariable("idCliente") Long idCliente , Model model , RedirectAttributes flash){
    
        Cliente cliente  = this.clienteServicio.buscarClientePorId(idCliente);
        if(cliente == null ){
        
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/cliente/listadoClientes";
        
        }
        
        Factura factura = new Factura();
        factura.setCliente(cliente);
        
        model.addAttribute("factura", factura);
        
        return "factura/formularioFactura";
    
    }
    
    
    @PostMapping("/guardarFactura")
    public String guardarFactura (Factura factura , 
            @RequestParam(name ="item_id[]"  ,required =false) Long[] itemId ,
            @RequestParam(name="cantidad[]" , required = false ) Integer[] cantidad ,
            RedirectAttributes flash ,
            SessionStatus status
    ){
        

        
        for (int i =0 ; i<itemId.length ; i++) {
            
            Producto producto = this.productoServicio.buscarProductoPorId(itemId[i]);
            ItemFactura linea = new ItemFactura() ;
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);
            
            log.info("Valor de ID : " +itemId[i] );
            log.info("Nombre Producto : " +producto.getNombre() );
            log.info("Cantidad : " +itemId[i] );
            
            
            
            factura.anadirItemFactura(linea);
            
        }
        
        
        this.facturaServicio.guardarFactura(factura);
        flash.addFlashAttribute("success", "Factura añadida con exito al cliente " + factura.getCliente().getNombre());
        status.isComplete();
        
        return "redirect:/cliente/verDetalle/"+factura.getCliente().getIdCliente();
    
    }
    
    
    
}
