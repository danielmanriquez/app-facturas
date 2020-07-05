package com.springbootjpa.app.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class InicioControlador {
    
    @GetMapping("/")
    public String inicio(){
        return "redirect:/cliente/listadoClientes";
    }
    
}
