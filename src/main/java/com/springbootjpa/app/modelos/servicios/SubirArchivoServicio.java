
package com.springbootjpa.app.modelos.servicios;

import java.io.IOException;
import java.net.MalformedURLException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;


public interface SubirArchivoServicio {
    
    public Resource cargarImagen(String nombreArchivo)  throws MalformedURLException;
    
    public String copiarImagen(MultipartFile archivo)  throws IOException;
    
    public boolean borrarImagen(String nombreArchivo);
    
    public void borrarCarpetaConImagenes();
    
    public void crearCarpeta() throws Exception;
    
}
