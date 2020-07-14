package com.springbootjpa.app.controladores;

import com.springbootjpa.app.controladores.paginator.PageRender;
import com.springbootjpa.app.modelos.entidades.Cliente;
import com.springbootjpa.app.modelos.servicios.ClienteServicio;
import com.springbootjpa.app.modelos.servicios.SubirArchivoServicio;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@RequiredArgsConstructor // para que inyeccion de dependencia
@Controller
@Slf4j
@RequestMapping("/cliente")
@SessionAttributes("cliente")
public class ClienteControlador {

    
    private final ClienteServicio clienteServicio;


    private final SubirArchivoServicio subirArchivoServicio;

    /**
     * Metodo que retorna la imagen del cliente en un cabecero http , y la
     * dibuja en la etiqueta <img> que lo solicita.
     *
     * <img th:src="@{'/cliente/uploads/' + ${cliente.foto}}"/>
     *
     *
     * @param nombreArchivo
     * @return ResponseEntity
     */
    @GetMapping("/uploads/{nombreArchivo:.+}")
    public ResponseEntity<Resource> verImagen(@PathVariable(value = "nombreArchivo") String nombreArchivo) {
        Resource recurso = null;
        try {
            recurso = this.subirArchivoServicio.cargarImagen(nombreArchivo);

        } catch (MalformedURLException ex) {
            log.info("Hubo un error al cargar la imagen");
             log.info(ex.toString());
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename()
                + "\"").body(recurso);

    }
    
    /**
     *Metodo Controlador de tipo get que nos redirige hacia la vista para ver el detalle del cliente 
     * que buscamos por su id.
     * 
     * 
     * @param id
     * @return String
     */
    @GetMapping("/verDetalle/{id}")
    public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

        Cliente cliente = this.clienteServicio.buscarClientePorId(id);
        if (cliente == null) {

            flash.addFlashAttribute("error", "El cliente no existe en la base de datos ");
            return "redirect:/listadoClientes";

        }

        model.addAttribute("cliente", cliente);

        return "verDetalle";

    }

    /**
     *Metodo controlador de tipo GET que redirige hacia la vista con el listado de clientes 
     * paginable. 
     * @param page
     * @param model
     * @return
     */
    @GetMapping("/listadoClientes")
    public String listadoClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {

        Pageable pageRequest = PageRequest.of(page, 5);
        Page<Cliente> clientes = clienteServicio.listarClientes(pageRequest);

        PageRender<Cliente> pageRender = new PageRender<>("listadoClientes", clientes);
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

    /**
     *Metodo de tipo POST que guarda un cliente en la base de datos , si este no tiene id nulo ,
     * se creara un nuevo registro ,
     *Si la variable foto es distinto de nulo actualizara la foto del cliente.
     * @param cliente
     * @param foto
     * @return
     */
    @PostMapping("/guardarCliente")
    public String guardarCliente(@Valid Cliente cliente,
            BindingResult result,
            SessionStatus status,
            RedirectAttributes flash,
            @RequestParam("file") MultipartFile foto) {

        if (result.hasErrors()) {
            
            log.info("Hubo un error de validacion en el formulario cliente.");
            
            return "agregarCliente";
        }

        if (!foto.isEmpty()) {

            if (cliente.getIdCliente() != null
                    && cliente.getIdCliente() > 0
                    && cliente.getFoto() != null
                    && cliente.getFoto().length() > 0) {

              this.subirArchivoServicio.borrarImagen(cliente.getFoto());

            }
            
            String nombreArchivoUnico = null;
            
            try {
                nombreArchivoUnico = this.subirArchivoServicio.copiarImagen(foto);
            } catch (IOException ex) {
                log.info(ex.toString());
                log.info("Problemas al subir el archivo ... ");
            }
            
            flash.addFlashAttribute("success", "Has subido correctamente : " + nombreArchivoUnico);
            cliente.setFoto(nombreArchivoUnico);
        }
        
        
        this.clienteServicio.guardarCliente(cliente);

        status.setComplete();

        flash.addFlashAttribute("info", "El cliente ha sido guardado con exito");

        return "redirect:/cliente/listadoClientes";
    }

    @GetMapping("/editarCliente/{id}")
    public String editarCliente(@PathVariable(value = "id") Long idCliente, Model model) {

        Cliente clienteTemporal = null;

        if (idCliente != null) {

            clienteTemporal = clienteServicio.buscarClientePorId(idCliente);

        } else {

            return "redirect:/cliente/listadoClientes";

        }

        model.addAttribute("cliente", clienteTemporal);
        model.addAttribute("titulo", "editarCliente");

        return "agregarCliente";

    }

    /**
     * Metodo de tipo Get que elimina el cliente de la base de datos y tambien
     * la imagen de la carpeta /uploads/ .
     *
     * <a th:href="@{/cliente/eliminarCliente}+'/'+${cliente.idCliente}">[[#{menu.eliminar}]]</a>
     *
     * @param idCliente
     * @return String
     */
    @GetMapping("/eliminarCliente/{idCliente}")
    public String eliminarCliente(@PathVariable(value = "idCliente") Long idCliente, RedirectAttributes flash) {

        if (idCliente > 0 ) {
            Cliente cliente = this.clienteServicio.buscarClientePorId(idCliente);

            this.clienteServicio.eliminarCliente(idCliente);
            if(subirArchivoServicio.borrarImagen(cliente.getFoto())){
            
                flash.addFlashAttribute("success", "Foto del cliente eliminada con exito");
                
            }
            

            flash.addFlashAttribute("info", "Cliente eliminado Con Exito");
        }
        return "redirect:/cliente/listadoClientes";

    }

}
