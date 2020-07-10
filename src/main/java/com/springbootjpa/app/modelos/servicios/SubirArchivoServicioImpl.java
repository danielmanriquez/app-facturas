package com.springbootjpa.app.modelos.servicios;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class SubirArchivoServicioImpl implements SubirArchivoServicio {

    private final String CARPETA_UPLOAD = "uploads";

    @Override
    public Resource cargarImagen(String nombreArchivo) throws MalformedURLException {

        Path rutaFoto = obtenerRutaAbsoluta(nombreArchivo);
        log.info("Ruta de la foto :" + rutaFoto);
        Resource recurso = null;

        recurso = new UrlResource(rutaFoto.toUri());

        if (!recurso.exists() || !recurso.isReadable()) {
            throw new RuntimeException("Error no se puede cargar la imagen :" + rutaFoto.toString());
        }

        return recurso;

    }

    @Override
    public String copiarImagen(MultipartFile archivo) throws IOException {

        String nombreArchivoUnico = UUID.randomUUID().toString() + "_" + archivo.getOriginalFilename();

        Path rutaRaiz = obtenerRutaAbsoluta(nombreArchivoUnico);

        Path rutaAbsoluta = rutaRaiz.toAbsolutePath();

        log.info("Se ha guardado la imagen en : " + rutaAbsoluta.toString());

        Files.copy(archivo.getInputStream(), rutaAbsoluta);

        return nombreArchivoUnico;

    }

    @Override
    public boolean borrarImagen(String nombreArchivo) {

        Path rutaRaiz = obtenerRutaAbsoluta(nombreArchivo);

        File archivo = rutaRaiz.toFile();

        if (archivo.exists() && archivo.canRead()) {

            if (archivo.delete()) {
                
                log.info("Se ha borrado el archivo : " + nombreArchivo);
                return true;
            }

        }

        return false;
    }

    public Path obtenerRutaAbsoluta(String nombreArchivo) {

        return Paths.get(CARPETA_UPLOAD).resolve(nombreArchivo).toAbsolutePath();
    }

    @Override
    public void borrarCarpetaConImagenes() {
        
        
        FileSystemUtils.deleteRecursively(Paths.get(CARPETA_UPLOAD).toFile());
        log.info("Carpeta uploads borrada satisfactoriamente");
    }

    @Override
    public void crearCarpeta() throws Exception {
        
        Files.createDirectory(Paths.get(CARPETA_UPLOAD));
        log.info("Carpeta uploads creada satisfactoriamente.");
    }

}
