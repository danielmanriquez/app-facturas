package com.springbootjpa.app.controladores;

import com.springbootjpa.app.controladores.paginator.PageRender;
import com.springbootjpa.app.modelos.entidades.Producto;
import com.springbootjpa.app.modelos.servicios.ProductoServicio;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor // para que inyeccion de dependencia
@Controller
@RequestMapping("/producto")
public class ProductoControlador {
    
    
    private final ProductoServicio productoServicio;
    
    
    @GetMapping("/listadoProductos")
    public String listadoProductos(@RequestParam(name = "page", defaultValue = "0") int page ,Model model , Producto producto){
        
        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Producto> productos = this.productoServicio.listarTodosLosProductos(pageRequest);
        PageRender<Producto> pageRender = new PageRender<>("listadoProductos", productos);
        model.addAttribute("listadoProductos", productos);
        model.addAttribute("page", pageRender);
        model.addAttribute("producto", producto);
        
    
        return "producto/listadoProductos";
    }
    
    
    @PostMapping("/guardarProducto")
    public String guardarProducto(@Valid Producto producto, BindingResult result , RedirectAttributes flash , Model model){
        
        if(result.hasErrors()){
            
            log.info("Hubo un error de validacion , redirigiendo a lista de productos");
            
           flash.addFlashAttribute("error", result.getFieldError().getDefaultMessage());
           
           return "redirect:/producto/listadoProductos";
        
        }
        
        
        if(producto.getId() != null && producto.getId() >0){
        
            flash.addFlashAttribute("info", "Producto actualizado con exito");
        
        }else{
        
            flash.addFlashAttribute("success", "Producto guardado con exito");
            
        }
        
        this.productoServicio.guardarProducto(producto);
        
        return "redirect:/producto/listadoProductos";
    
    }
    
    
    
}
