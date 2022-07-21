package mx.gda.resultados.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import java.io.ByteArrayOutputStream;

@Component
public class UtilBase64 {
	
	Logger logger= LoggerFactory.getLogger(UtilBase64.class);
	
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


	/* Método para combinar los PDF's (cadenas en base64) */
	public String combinePDFs(List<String> resultados) {
		String salida = null;
		PDDocument mergedPDF=null;
		PDDocument tmpPDF=null;
		try {
			if (resultados != null) {
				if (resultados.size() > 1) {
					//PDDocument mergedPDF = new PDDocument();
					mergedPDF = new PDDocument();
					PDFMergerUtility ut = new PDFMergerUtility();
					byte[] tmpDocument = null;
					for (String resultado : resultados) {
						tmpDocument = Base64.getDecoder().decode(resultado);
						tmpPDF=PDDocument.load(tmpDocument);
						//logger.info("Document Pages: {}", tmpPDF.getPages().getCount());
						ut.appendDocument(mergedPDF, tmpPDF);
						tmpPDF.close();
					}
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					mergedPDF.save(baos);
					salida = Base64.getEncoder().encodeToString(baos.toByteArray());
					mergedPDF.close();
				} else {
					salida = resultados.get(0);
				}
			}
		} catch (Exception e) {
			logger.error("Error en método combinePDFs : {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error en combinePDFs");
		}
		return salida;
	}

}
