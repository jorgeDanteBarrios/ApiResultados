package mx.gda.resultados.utilities;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UtilFiles {

    Logger logger= LoggerFactory.getLogger(UtilFiles.class);
    
    @Autowired
    ResourceLoader resourceLoader;

    /* Método para obtener un archivo dentro del jar */
    public File getFileFromResource(String path,String fileName,String extension) {
        Path file=null;
        try {
            Resource resource = resourceLoader.getResource("classpath:" +path+ fileName+"."+extension);
            InputStream inputStream = resource.getInputStream();
            file = Files.createTempFile(fileName ,extension);
            Files.copy(inputStream, file, StandardCopyOption.REPLACE_EXISTING);             
        } catch (Exception e) {
            logger.error("Error en método getFileFromResource: {}",e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        return file.toFile();
    }
      
}
