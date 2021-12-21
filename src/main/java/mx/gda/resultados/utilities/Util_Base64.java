package mx.gda.resultados.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class Util_Base64 {
	
	Logger logger= LoggerFactory.getLogger(Util_Base64.class);
	
	/*Método para codificar un archivo en base64*/
	public String encodeFileToBase64(File file) {
		String salida=null;
		try {
	        byte[] fileContent = Files.readAllBytes(file.toPath());
	        salida=Base64.getEncoder().encodeToString(fileContent);
	    } catch (IOException e) {
	    	logger.error("Error en método Util_Base64.encodeFileToBase64: {}",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	    }
		return salida;
	}
	
	/*Método para codificar un archivo en base64*/
	public String encodeFileToBase64(String path) {
		String salida=null;
		try {
	        byte[] fileContent = Files.readAllBytes(Paths.get(path));
	        salida=Base64.getEncoder().encodeToString(fileContent);
	    } catch (IOException e) {
	    	logger.error("Error en método Util_Base64.encodeFileToBase64: {}",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
	    }
		return salida;
	}

	/*Método para decodificar una cadena base 64 en bytes[]*/
	public byte[] base64ToBytes(String cadena) {
		byte[] salida=null;
		try {
			salida=Base64.getDecoder().decode(cadena);
		} catch (Exception e) {
			logger.error("Error en método Util_Base64.base64ToBytes: {}",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return salida;
	}

}
