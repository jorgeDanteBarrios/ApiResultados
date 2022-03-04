package mx.gda.resultados.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import mx.gda.resultados.objects.EnviaResCovid;

@Component
public class EmailClient {
	
	Logger logger= LoggerFactory.getLogger(EmailClient.class);
	
	@Value("${api.correo.covid.url}")
	public String URL_API_CORREO;
	
	/* Método para enviar el resultado covid (consume ApiCorreo) */
	public Boolean sendResultadoCovid(EnviaResCovid enviaResCovid) {
		Boolean salida=false;
		RestTemplate restTemplate=new RestTemplate();
		HttpHeaders headers= new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    logger.debug("URL of Service: {}",enviaResCovid);
	    try {
	    	salida=restTemplate.postForObject(URL_API_CORREO, enviaResCovid, Boolean.class);
		} catch (Exception e) {
			logger.error("Error en método EmailClient.sendEmail: {}",e.getMessage());
			throw new ResponseStatusException(HttpStatus.FAILED_DEPENDENCY, e.getMessage());
		}
	    
	    return salida;
	}
	
	/* Método para separar los correos por coma*/
    public List<String>  getCorreos(String correos){
    	List<String> salida=new ArrayList<String>();
    	String tmp=null;
     	try {
            StringTokenizer st = new StringTokenizer(correos,",");                   		
            while (st.hasMoreElements()) {
            	tmp=st.nextElement().toString();            	
            	salida.add(tmp);
            }
		} catch (Exception e) {
			logger.error("Error en método EmailClient.getCorreos: {}",e.getMessage());
		}   	
    	return salida;
    }

}
