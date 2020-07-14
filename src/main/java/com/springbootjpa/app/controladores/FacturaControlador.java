package com.springbootjpa.app.controladores;

import com.springbootjpa.app.modelos.entidades.Cliente;
import com.springbootjpa.app.modelos.entidades.Factura;
import com.springbootjpa.app.modelos.entidades.ItemFactura;
import com.springbootjpa.app.modelos.entidades.Producto;
import com.springbootjpa.app.modelos.servicios.ClienteServicio;
import com.springbootjpa.app.modelos.servicios.FacturaServicio;
import com.springbootjpa.app.modelos.servicios.ProductoServicio;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequiredArgsConstructor // para que inyeccion de dependencia
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaControlador {

    private final ProductoServicio productoServicio;

    private final FacturaServicio facturaServicio;

    private final ClienteServicio clienteServicio;

    /**
     * Metodo que retorna un JSON con una lista de productos buscados por nombre
     * con ajax Revisar Codigo JS que esta en la carpeta
     * static/js/autocompletar-productos.js
     *
     * @param term
     * @return Json List
     */
    @GetMapping(value = "/cargarProductos/{term}", produces = {"application/json"})
    public @ResponseBody
    List<Producto> cargarProductos(@PathVariable String term) {
        log.info("Devolviendo un JSON con una lista de Productos Asincronamente");
        return this.productoServicio.buscarProductoPorNombre(term);

    }

    /**
     *Metodo controlador para ingresar al formulario de agregar una factura .
     * 
     * @param idCliente
     * @param model
     * @param flash
     * @return String
     */
    @GetMapping("/formularioFactura/{idCliente}")
    public String crearFactura(@PathVariable("idCliente") Long idCliente, Model model, RedirectAttributes flash) {

        log.info("Entrando al metodo crearFactura con el id de cliente : " + idCliente);

        Cliente cliente = this.clienteServicio.buscarClientePorId(idCliente);
        if (cliente == null) {

            log.info("El cliente no existe es nulo.");
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/cliente/listadoClientes";

        }

        log.info("Cliente encontrado : " + cliente.getNombre() + " " + cliente.getApellido() + " ID : " + cliente.getIdCliente());

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("factura", factura);

        log.info("Redirigiendo a factura/formularioFactura ");
        return "factura/formularioFactura";

    }

    /**
     *Metodo controlador de tipo POST para guardar una factura y sus itemsFactura ,
     * si hay un error en la validacion de los campos , redireccionara a la pagina del formulario,
     * los arreglos itemId y cantidad se obtienen de la iteracion de plantillaItems ,
     * revisar autocompletar-productos.js de la carpeta static.
     * 
     * 
     * @param factura
     * @return
     */
    @PostMapping("/guardarFactura")
    public String guardarFactura(@Valid Factura factura,
            BindingResult error,
            Model model,
            @RequestParam(name = "item_id[]", required = false) Long[] itemId,
            @RequestParam(name = "cantidad[]", required = false) Integer[] cantidad,
            RedirectAttributes flash,
            SessionStatus status
    ) {

        if (error.hasErrors()) {

            log.info("Hubo errores de validacion Redirigiendo a formularioFactura");
            model.addAttribute("error", "Hubo un error de validacion");
            return "factura/formularioFactura";

        }

        if (itemId == null || itemId.length == 0) {

            log.info("La factura esta vacia .");
            model.addAttribute("warning", "La Factura no contiene items O esta vacia");
            return "factura/formularioFactura";

        }

        for (int i = 0; i < itemId.length; i++) {

            Producto producto = this.productoServicio.buscarProductoPorId(itemId[i]);
            ItemFactura linea = new ItemFactura();
            linea.setCantidad(cantidad[i]);
            linea.setProducto(producto);

            log.info("Valor de ID : " + itemId[i]);
            log.info("Nombre Producto : " + producto.getNombre());
            log.info("Cantidad : " + itemId[i]);

            factura.anadirItemFactura(linea);

        }

        this.facturaServicio.guardarFactura(factura);
        flash.addFlashAttribute("success", "Factura añadida con exito al cliente " + factura.getCliente().getNombre());
        status.isComplete();

        return "redirect:/cliente/verDetalle/" + factura.getCliente().getIdCliente();

    }

    /**
     *Metodo controlador del tipo Get que nos redirige a la vista para ver la factura que buscamos por el id.
     * 
     * 
     * @param id
     * @return
     */
    @GetMapping("/verFactura/{id}")
    public String verFactura(@PathVariable Long id,
            Model model,
            RedirectAttributes flash) {

        log.info("Entrando al metodo verFactura");
        
        Factura factura = this.facturaServicio.buscarFacturaConClienteConItemsFacturaConProductos(id);
        
        //Factura factura = this.facturaServicio.buscarFacturaPorId(id);

        if (factura == null) {

            log.info("La factura es nula .");
            flash.addFlashAttribute("error", "La factura no existe en la base de datos");
            return "redirect:/";

        }

        model.addAttribute("factura", factura);

        return "factura/verFactura";
    }

    /**
     *Metodo controlador del tipo Get para borrar una factura , si la factura no existe ,
     * redirige hacia la lista de clientes.
     * @param id
     * @return
     */
    @GetMapping("/borrarFactura/{id}")
    public String borrarFactura(@PathVariable Long id,
            RedirectAttributes flash) {

        Factura factura = this.facturaServicio.buscarFacturaPorId(id);

        if (factura != null) {

            this.facturaServicio.borrarFacturaPorId(id);
            flash.addFlashAttribute("success", "Factura Eliminada Con exito .");

            return "redirect:/cliente/verDetalle/" + factura.getCliente().getIdCliente();

        }
        
        flash.addFlashAttribute("warning", "La factura no existe en la base de datos");
        return "redirect:/";
    }

}
