package mx.gda.resultados.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import mx.gda.resultados.objects.EmailFile;
import mx.gda.resultados.objects.EmailResultadoCovid;
import mx.gda.resultados.objects.EnviaResCovid;
import mx.gda.resultados.utilities.EmailClient;

@Service
public class EnvioResultados {

	Logger logger = LoggerFactory.getLogger(EnvioResultados.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private ResultadosService resultadosService;
	@Autowired
	private TransaccionesService transaccionesService;
	@Autowired
	private EmailClient emailClient;
	
	//@Scheduled(cron = "0 1/1 * * * *")
	
	public Boolean enviaResultadosCovid() {
		Boolean salida = false;
		List<EmailResultadoCovid> ordenes = null;
		String resultado=null;
		logger.info("---- enviaResultadosCovid [Inicio] ----");
		ordenes = get_PruebasRapidasCovid();
		if (ordenes != null) {
			for (EmailResultadoCovid o : ordenes) {
				try {
					resultado=getResultadoCovid(o.getKrescovid(), o.getKordensucursal());					
					if(o.getTipo().equals(1)) {
						enviaEmail_Particular(o, resultado);
					}else if(o.getTipo().equals(2)) {
						enviaEmail_Empresa(o, resultado);
					}
				} catch (Exception e) {
					logger.error("Error en método enviaResultadosCovid: Error al procesar la korden {}",o.getKorden());
				}
			}
		}
		logger.info("---- enviaResultadosCovid [Fin] ----");
		return salida;
	}
	
	public List<EmailResultadoCovid> get_PruebasRapidasCovid() {
		List<EmailResultadoCovid> salida=null;
		try {
			Query q = this.entityManager.createNativeQuery(
					" select "+
							" 	a.krescovid, "+
							" 	a.ncmarca, "+
							" 	a.nkorden, "+
							" 	a.nkordensucursal, "+
							" 	a.snombre, "+
							" 	a.slabcore, "+
							" 	a.ntipo, "+
							" 	a.semail_px, "+
							" 	a.semail_med, "+
							" 	a.semail_empresa "+
							" from "+
							" 	eventos.t_email_covid a "+
							" where  "+
							" 	a.nintento<4 "+
							" order by a.krescovid asc ");
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			if(results!=null) {
				salida= new ArrayList<EmailResultadoCovid>();
				for (Object[] r : results) {					
					salida.add(new EmailResultadoCovid(
							(String) r[0],
							((BigDecimal) r[1]).intValue(),
							Long.valueOf(((BigDecimal) r[2]).longValue()),
							Long.valueOf(((BigDecimal) r[3]).longValue()),
							(String) r[4],
							(String) r[5],
							((BigDecimal) r[6]).intValue(),
							(String) r[7],
							(String) r[8],
							(String) r[9]
							));
				}					
			}			
		} catch (Exception e) {
			this.logger.error("Error en get_PruebasRapidasCovid: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			this.entityManager.close();
		}
		return salida;
	}
	
	/* Método para obtener el pdf de resultados (en base64)*/
	public String getResultadoCovid(String krescovid,Long kordensucursal) {
		String salida=null;
		try {
			salida=resultadosService.getResultado(kordensucursal, 1);
			if(salida==null||salida.isEmpty()) {
				logger.error("Error en método getResultadoCovid: Orden sin resultados (cadena vacia)");
				transaccionesService.updateOrdenCovid(krescovid, "Orden sin resultados (cadena vacia");
				throw new ResponseStatusException(HttpStatus.NO_CONTENT, "  Orden sin resultado (cadena vacia)");
			}
		} catch (Exception e) {
			logger.error("Error en método enviaResultado_Particular: Error al consultar en labcore",e.getMessage());
			transaccionesService.updateOrdenCovid(krescovid, "Error al consultar en labcore");
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar en labcore");
		}
		return salida;
	}
	
	/* Método para enviar correo de pruebas rapidas covid segun su origen y marca
	 * Nota: En caso de que el doctor tenga un correo se enviara como copia
	 * */
	public Boolean enviaEmail_Particular(EmailResultadoCovid emailResultadoCovid,String resultado) {
		Boolean salida=false;
		EnviaResCovid tmpEnviaResCovid=new EnviaResCovid();
		EmailFile tmpEmailFile=null;
		List<EmailFile> files= new ArrayList<EmailFile>();
		Pattern p = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
		//Set data
		tmpEmailFile=new EmailFile(emailResultadoCovid.getLabcore()+".pdf", resultado);
		files.add(tmpEmailFile);
		tmpEnviaResCovid.setPaciente(emailResultadoCovid.getPaciente());
		tmpEnviaResCovid.setKordensucursal(emailResultadoCovid.getKordensucursal().toString());
		tmpEnviaResCovid.setCmarca(emailResultadoCovid.getMarca().longValue());
		tmpEnviaResCovid.setFiles(files);
		if(emailResultadoCovid.getEmailPx()!=null) {
			if(p.matcher(emailResultadoCovid.getEmailPx()).find()) {
				tmpEnviaResCovid.setTo(emailClient.getCorreos(emailResultadoCovid.getEmailPx()));
				if(emailResultadoCovid.getEmailMedico()!=null) {
					if(p.matcher(emailResultadoCovid.getEmailMedico()).find()) {
						tmpEnviaResCovid.setCc(emailClient.getCorreos(emailResultadoCovid.getEmailMedico()));
					}						
				}
				try {
					salida=emailClient.sendResultadoCovid(tmpEnviaResCovid);
					if(salida) {
						transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
						transaccionesService.updateOrdenEvento(emailResultadoCovid.getKorden());
					}else {
						transaccionesService.updateOrdenCovid(emailResultadoCovid.getKrescovid(), "Error al enviar correo");
					}
				} catch (Exception e) {
					logger.error("Error en método enviaResultado_Particular: Error al enviar orden particular",e.getMessage());
					transaccionesService.updateOrdenCovid(emailResultadoCovid.getKrescovid(), "Error al enviar correo");
				}
			}else {
				transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
				transaccionesService.insertaExclusion(emailResultadoCovid.getKorden(), emailResultadoCovid.getKordensucursal(), 1, "El px no tiene correo valido");
			}
		}else {
			//el px no tiene correo registrado/ valido
			transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
			transaccionesService.insertaExclusion(emailResultadoCovid.getKorden(), emailResultadoCovid.getKordensucursal(), 1, "El px no tiene un correo registrado (campo nulo)");
		}
		return salida;
	}
	
	
	/* Método para enviar el reusltado por correo de una pruebas rapidas a la empresa */
	public Boolean enviaEmail_Empresa(EmailResultadoCovid emailResultadoCovid,String resultado) {
		Boolean salida=false;
		EnviaResCovid tmpEnviaResCovid=new EnviaResCovid();
		EmailFile tmpEmailFile=null;
		List<EmailFile> files= new ArrayList<EmailFile>();
		Pattern p = Pattern.compile("^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$");
		//Set data
		tmpEmailFile=new EmailFile(emailResultadoCovid.getLabcore()+".pdf", resultado);
		files.add(tmpEmailFile);
		tmpEnviaResCovid.setPaciente(emailResultadoCovid.getPaciente());
		tmpEnviaResCovid.setKordensucursal(emailResultadoCovid.getKordensucursal().toString());
		tmpEnviaResCovid.setCmarca(emailResultadoCovid.getMarca().longValue());
		tmpEnviaResCovid.setFiles(files);
		//empresa
		if(emailResultadoCovid.getEmailEmpresa()!=null) {
			if(p.matcher(emailResultadoCovid.getEmailEmpresa()).find()) {
				tmpEnviaResCovid.setTo(emailClient.getCorreos(emailResultadoCovid.getEmailEmpresa()));
				try {
					salida=emailClient.sendResultadoCovid(tmpEnviaResCovid);
					if(salida) {
						transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
						transaccionesService.updateOrdenEventoEmpresa(emailResultadoCovid.getKorden());
					}else {
						transaccionesService.updateOrdenCovid(emailResultadoCovid.getKrescovid(), "Error al enviar correo");
					}
				} catch (Exception e) {
					logger.error("Error en método enviaResultado: Error al enviar orden de empresa",e.getMessage());
					transaccionesService.updateOrdenCovid(emailResultadoCovid.getKrescovid(), "Error al enviar correo");
				}
			}else {
				transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
				transaccionesService.insertaExclusion(emailResultadoCovid.getKorden(), emailResultadoCovid.getKordensucursal(), 2, "La empresa no tiene correo valido");
			}
		}else {
			transaccionesService.deleteOrdenCovid(emailResultadoCovid.getKrescovid());
			transaccionesService.insertaExclusion(emailResultadoCovid.getKorden(), emailResultadoCovid.getKordensucursal(), 2, "La empresa no tiene un correo registrado (campo nulo)");
		}
		return salida;
	}
	
	
	
}
