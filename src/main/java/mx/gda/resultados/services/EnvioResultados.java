package mx.gda.resultados.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
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
	
	@Scheduled(cron = "0 1/1 * * * *")
	//@Scheduled(cron = "0 1/5 * * * *")
	public Boolean enviaResultadosCovid() {
		Boolean salida = false;
		List<EmailResultadoCovid> ordenes = null;
		Boolean tmpEnvio = false;
		String msgError = null;
		logger.info("---- enviaResultadosCovid [Inicio] ----");
		ordenes = get_PruebasRapidasCovid();
		if (ordenes != null) {
			for (EmailResultadoCovid o : ordenes) {
				tmpEnvio = false;
				try {
					tmpEnvio = enviaResultado(o.getKordensucursal(), o.getOrdenLabcore(), o.getCmarca().longValue(),
							o.getEmail(),o.getPaciente());
				} catch (Exception e) {
					logger.error("Error al enviar el resultado vocid de la orden: {}-{}", o.getKordensucursal(),
							o.getKrescovid());
					tmpEnvio = false;
					msgError = e.getMessage();
				}
				try {
					if (tmpEnvio) {
						transaccionesService.deleteOrdenCovid(o.getKrescovid());
						transaccionesService.updateOrdenEvento(o.getKordensucursal().toString());
					} else {
						transaccionesService.updateOrdenCovid(o.getKrescovid(), msgError);
					}
				} catch (Exception e) {
					logger.error("Error en método enviaResultadosCovid: {}", e.getMessage());
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
							" 	a.nkordensucursal, "+
							" 	case "+
							" 		when length(b.uorden) <= 6 then RPAD(b.ssucursal, 3, '0') || 'L' || LPAD(b.uorden, 6, '0') "+
							" 		else RPAD(b.ssucursal, 3, '0') || LPAD(b.uorden, 7, '0') "+
							" 	end as claveLabcore, "+
							" 	b.cmarca, "+
							" 	c.scorreoelectronico, "+
							" 	a.krescovid,   "+
							"   c.snombre||' '||c.sapellidopaterno||' '||c.sapellidomaterno  as px  "+
							" from "+
							" 	eventos.t_email_covid a, "+
							" 	public.t_orden_sucursal b, "+
							" 	public.t_paciente c "+
							" where  "+
							" 	a.nintento<4 "+
							" 	and b.kordensucursal=a.nkordensucursal  "+
							" 	and c.kpaciente=b.kpaciente  "+
							" order by "+
							" 	a.krescovid desc ");
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			if(results!=null) {
				salida= new ArrayList<EmailResultadoCovid>();
				for (Object[] r : results) {					
					salida.add(new EmailResultadoCovid(Long.valueOf(((BigDecimal) r[0]).longValue()),(String) r[1], ((BigDecimal) r[2]).intValue(),(String) r[3],(String) r[4],(String) r[5]));
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
	
	public Boolean enviaResultado(Long kordensucursal,String ordenLabcore,Long cmarca,String email,String paciente) {
		String tmpResultado=null;
		EnviaResCovid tmpEnviaResCovid=null;
		EmailFile tmpEmailFile=null;
		List<EmailFile> files= new ArrayList<EmailFile>();
		try {
			tmpResultado=resultadosService.getResultado(kordensucursal, 1);
		} catch (Exception e) {
			logger.error("Error en método enviaResultado: Error al consultar los resultados, orden {}",kordensucursal);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, " Error al consultar los resultados ");
		}
		//mandar correo
		if(tmpResultado==null||tmpResultado.isEmpty()) {
			logger.error("Error en método enviaResultado: Orden sin resultados (cadena vacia)");
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "  Orden sin resultado (cadena vacia)");
		}
		tmpEmailFile=new EmailFile(ordenLabcore+".pdf", tmpResultado);
		files.add(tmpEmailFile);
		//tmpEnviaResCovid=new EnviaResCovid(kordensucursal.toString(), emailClient.getCorreos(email), cmarca, files);
		tmpEnviaResCovid=new EnviaResCovid(kordensucursal.toString(), emailClient.getCorreos("marco.sosa@gda.mx"), cmarca, files,paciente);
		return emailClient.sendResultadoCovid(tmpEnviaResCovid);
	}

/*
	@Transactional
	public Boolean deleteOrdenCovid(String krescovid) {
		Boolean salida=false;
		Integer r = 0;
		try {
			Query q = entityManager.createNativeQuery(
					"delete from eventos.t_email_covid  where krescovid=?1");
			q.setParameter(1, krescovid);
			r = q.executeUpdate();
			logger.debug("Valor de r (updateRol) : {} ", r);
			if (r >= 1) {
				salida = true;
				entityManager.flush();
			}
		} catch (Exception e) {
			logger.error("Error en método saveUserRole : {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return salida;
	}
	
	@Transactional
	public Boolean updateOrdenCovid(String krescovid,String error) {
		Boolean salida=false;
		Integer r = 0;
		try {
			Query q = entityManager.createNativeQuery(
					"update eventos.t_email_covid set nintento=(nintento+1),serror=?2 where krescovid=?1");
			q.setParameter(1, krescovid);
			q.setParameter(2, error);
			r = q.executeUpdate();
			logger.info("yaEsta");
			logger.debug("Valor de r (updateRol) : {} ", r);
			if (r >= 1) {
				salida = true;
				entityManager.flush();
			}
		} catch (Exception e) {
			logger.error("Error en método saveUserRole : {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		return salida;
	}
	
	*/
}
