package mx.gda.resultados.services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TransaccionesService {

	Logger logger = LoggerFactory.getLogger(TransaccionesService.class);
	
	@PersistenceContext
	private EntityManager entityManager;
	
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
			logger.debug("Valor de r (updateOrdenCovid) : {} ", r);
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
	public Boolean deleteOrdenCovid(String krescovid) {
		Boolean salida=false;
		Integer r = 0;
		try {
			Query q = entityManager.createNativeQuery(
					"delete from eventos.t_email_covid  where krescovid=?1");
			q.setParameter(1, krescovid);
			r = q.executeUpdate();
			logger.debug("Valor de r (deleteOrdenCovid) : {} ", r);
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
	public Boolean updateOrdenEvento(String kordensucursal) {
		Boolean salida=false;
		Integer r = 0;
		try {
			Query q = entityManager.createNativeQuery(
					"update eventos.t_orden set dcorreo=now(),benvio_correo=true where kordensucursal=?1");
			q.setParameter(1, kordensucursal);
			r = q.executeUpdate();
			logger.debug("Valor de r (updateOrdenEvento) : {} ", r);
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
	
	
}
