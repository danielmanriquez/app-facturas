package com.springbootjpa.app.controladores;

import com.springbootjpa.app.controladores.paginator.PageRender;
import com.springbootjpa.app.modelos.entidades.Cliente;
import com.springbootjpa.app.modelos.servicios.ClienteServicio;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/cliente")
@SessionAttributes("cliente")
public class ClienteControlador {

    
    @Autowired
    @Qualifier("clienteServicioImpl")
    private ClienteServicio clienteServicio;

    @GetMapping("/listadoClientes")
    public String listadoClientes(@RequestParam(name="page" , defaultValue = "0") int page , Model model) {

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cliente> clientes = clienteServicio.listarClientes(pageRequest);
        
        PageRender<Cliente> pageRender = new PageRender <>("listadoClientes" , clientes);
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("listadoClientes", this.clienteServicio.listarClientes(pageRequest));
        model.addAttribute("page", pageRender);

        return "listadoClientes";
    }

    @GetMapping("/formularioAgregarCliente")
    public String formularioAgregarCliente(Cliente cliente, Model model) {
        
        model.addAttribute("titulo", "Formulario agregar Cliente");

        return "agregarCliente";
    }

    @PostMapping("/guardarCliente")
    public String guardarCliente(@Valid Cliente cliente , BindingResult result , SessionStatus status , RedirectAttributes flash) {
        
        if(result.hasErrors()){
            return "agregarCliente";
        }
        
        this.clienteServicio.guardarCliente(cliente);
        
        status.setComplete();
        
        flash.addFlashAttribute("success", "EL CLIENTE HA SIDO GUARDADO CON EXITO.");
        
        return "redirect:/cliente/listadoClientes";
    }
    
    @GetMapping("/editarCliente/{id}")
    public String editarCliente ( @PathVariable(value="id") Long idCliente ,Model model ){
            
        Cliente clienteTemporal = null;
        
         if(idCliente != null){
             
             clienteTemporal = clienteServicio.buscarClientePorId(idCliente);
             
         }   else {
             
             return "redirect:/cliente/listadoClientes";
             
         }
         
         model.addAttribute("cliente" , clienteTemporal);
         model.addAttribute("titulo", "editarCliente");
        
         return "agregarCliente";
    
    }
    
    @GetMapping("/eliminarCliente/{idCliente}")
    public String eliminarCliente(@PathVariable(value = "idCliente") Long idCliente){
    
        if(idCliente != null){
        
            this.clienteServicio.eliminarCliente(idCliente);
        }
        
        
        
        return "redirect:/cliente/listadoClientes";

    }

}
