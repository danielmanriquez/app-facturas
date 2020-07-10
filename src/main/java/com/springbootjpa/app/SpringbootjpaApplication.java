package com.springbootjpa.app;

import com.springbootjpa.app.modelos.servicios.SubirArchivoServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringbootjpaApplication implements CommandLineRunner {

    @Autowired
    private SubirArchivoServicio subirArchivoServicio;
    
    
    
    public static void main(String[] args) {
        SpringApplication.run(SpringbootjpaApplication.class, args);
    }
    
    /**
     *Metodo que borra la carpeta /uploads/ junto con todas las imagenes y despues crea una nueva.
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        
        this.subirArchivoServicio.borrarCarpetaConImagenes();
        this.subirArchivoServicio.crearCarpeta();
        
    }

}
