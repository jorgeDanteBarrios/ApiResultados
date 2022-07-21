package mx.gda.resultados.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import mx.gda.resultados.objects.LigaFuji;
import mx.gda.resultados.objects.MuestraFuji;
import mx.gda.resultados.objects.TordenSucursal;
import mx.gda.resultados.utilities.ConsultaWebServices;
import mx.gda.resultados.utilities.UtilBase64;
import wsclient.fuji.Response;

@Service
public class ResultadosService {

	Logger logger = LoggerFactory.getLogger(mx.gda.resultados.services.ResultadosService.class);

	@Value("${app.parametro.intentos.consulta.labcore}")
	Integer NO_INTENTOS;
	@Autowired
	private ConsultaWebServices consultaWebServices;
	@Autowired
	private UtilBase64 utilBase64;
	@PersistenceContext
	private EntityManager entityManager;

	/* Método para obtener el PDF de resultados
	 * 1) Conuslta los datos de la orden (estado, valida si tiene estudios de Patcore & Imagen/ Fuji)
	 * 2) Filtra por estatus de orden
	 * 3) Filtra por marca de la orden (consulta los Ws de Labcore correspondientes)
	 * 4) Fusiona todos los resultados en un solo PDF
	 */
	public String getResultadoGDA(Long kordensucursal, Integer opcion) {
		String salida = null;
		List<String> tmpResultados = null;
		TordenSucursal tordenSucursal = getDatosOrden(kordensucursal);
		if (tordenSucursal != null) {
			if (tordenSucursal.getCestadoRegistro().longValue() != 17L) {
				switch (tordenSucursal.getCmarca()) {
					// Marcas no operativas
					case 6:
					case 14:
						logger.error("Error en getResultado: Marca no operativa {}, favor de validar la información",tordenSucursal.getCmarca());
						throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"Marca no operativa, favor de validar la información");
					case 5:
						tmpResultados = getResultadosMarcaSwiss(tordenSucursal, opcion);
						break;
					case 15:
						tmpResultados = getResultadoMarcaLiacsa(tordenSucursal, opcion);
						break;
					case 1:
					case 2:
					case 3:
					case 4:
					case 7:
						tmpResultados = getResultadosMarcasCDMX(tordenSucursal, opcion);
						break;
					default:
						logger.error(
								"Error en getResultado: Sin reglas definidas para consulta de resultados para la marca {}",	tordenSucursal.getCmarca());
						throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED,"Sin reglas definidas para consulta de resultados para esta marca");
				}
			} else {
				logger.error("Error en getResultado: Orden cancelada");
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Orden cancelada");
			}
		} else {
			logger.error("Error en getResultado: Orden no valida");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Orden no valida");
		}
		logger.debug(">>> No. de PDF's {}", Integer.valueOf(tmpResultados.size()));
		if(tmpResultados!=null && !tmpResultados.isEmpty()){
			salida = utilBase64.combinePDFs(tmpResultados);
		}else{
			logger.error("Error en getResultado: Orden sin resultados");
			throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Orden sin resultados");
		}		
		return salida;
	}
	

	/* Método para consultar los resultados de las marcas:
	 *  4-Azteca
	 *  7-Jenner
	 *  1,2,3-Olab
	 */
	private List<String> getResultadosMarcasCDMX(TordenSucursal tordenSucursal, Integer opcion) {
		List<String> salida = new ArrayList<>();
		String tmpResCdmx;
		String tmpResPatcore;
		List<String> tmpResFuji;
		// Labcore CDMX
		tmpResCdmx=getResultadosLabCdmx(tordenSucursal.getClaveLabcore(), opcion);
		if(tmpResCdmx!=null){
			salida.add(tmpResCdmx);
		}
		//Patcore
		if(Boolean.TRUE.equals(tordenSucursal.getbPatcore())){
			tmpResPatcore=getResultadosPatcore(tordenSucursal.getClaveLabcore(), opcion);
			if(tmpResPatcore!=null){
				salida.add(tmpResPatcore);
			}
		}		
		//Fuji
		if(Boolean.TRUE.equals(tordenSucursal.getbFuji()) && !tordenSucursal.getMuestrasFuji().isEmpty()){
			tmpResFuji=getResBase64FujiPdf(tordenSucursal.getCmarca(), tordenSucursal.getMuestrasFuji());
			if(!tmpResFuji.isEmpty()){
				salida.addAll(tmpResFuji);
			}
		}		
		return salida;		
	}

	/* Método para consultar los resultados de las marcas:
	 *  5-Swiss
	 */
	private List<String> getResultadosMarcaSwiss(TordenSucursal tordenSucursal, Integer opcion) {
		List<String> salida = new ArrayList<>();
		String tmpResSwiss;
		//String tmpResNova;
		String tmpResCdmx;
		List<String> tmpResFuji;
		// Labcore Swiss
		tmpResSwiss=getResultadosLabMty(tordenSucursal.getClaveLabcore(), opcion);
		if(tmpResSwiss!=null){
			salida.add(tmpResSwiss);
		}
		/*/ Labcore Nova
		tmpResNova=getResultadosLabNova(tordenSucursal.getClaveLabcore(), opcion);
		if(tmpResNova!=null){
			salida.add(tmpResNova);
		}
		*/
		// Labcore CDMX
		tmpResCdmx=getResultadosLabCdmx(tordenSucursal.getClaveLabcore(), opcion);
		if(tmpResCdmx!=null){
			salida.add(tmpResCdmx);
		}
		//Fuji
		if(Boolean.TRUE.equals(tordenSucursal.getbFuji()) && !tordenSucursal.getMuestrasFuji().isEmpty()){
			tmpResFuji=getResBase64FujiPdf(tordenSucursal.getCmarca(), tordenSucursal.getMuestrasFuji());
			if(!tmpResFuji.isEmpty()){
				salida.addAll(tmpResFuji);
			}
		}
		return salida;
	}

	/* Método para consultar los resultados de las marcas:
	 *  5-Swiss
	 */
	private List<String> getResultadoMarcaLiacsa(TordenSucursal tordenSucursal, Integer opcion) {
		List<String> salida = new ArrayList<>();
		String tmpResCdmx;
		List<String> tmpResFuji;
		// Labcore CDMX
		tmpResCdmx=getResultadosLabCdmx(tordenSucursal.getClaveLabcore(), opcion);
		if(tmpResCdmx!=null){
			salida.add(tmpResCdmx);
		}
		//Fuji
		if(Boolean.TRUE.equals(tordenSucursal.getbFuji()) && !tordenSucursal.getMuestrasFuji().isEmpty()){
			tmpResFuji=getResBase64FujiPdf(tordenSucursal.getCmarca(), tordenSucursal.getMuestrasFuji());
			if(!tmpResFuji.isEmpty()){
				salida.addAll(tmpResFuji);
			}
		}		
		return salida;
	}

	/*  Método para generar las URL's de los estudios de imagen ( visor mobility) */
	public List<LigaFuji> getLigaFuji(Long kordensucursal) {
		List<LigaFuji> salida = new ArrayList<>();
		LigaFuji tmpLigaFuji = null;
		String tmpURL = null;
		TordenSucursal tordenSucursal = null;
		tordenSucursal = getDatosOrden(kordensucursal);
		if (tordenSucursal != null) {
			if (tordenSucursal.getCestadoRegistro().longValue() != 17L) {
				if (Boolean.TRUE.equals(tordenSucursal.getbFuji()) && !tordenSucursal.getMuestrasFuji().isEmpty()) {
					for (MuestraFuji m : tordenSucursal.getMuestrasFuji()) {
						tmpURL = consultaWebServices.consultaWsFujiUrl(consultaWebServices.generaNumAccesoFuji(
								tordenSucursal.getCmarca(), m.getCsucursaltranslado(), m.getKmuestrasucursal()));
						tmpLigaFuji = new LigaFuji(m.getEstudio(), tmpURL);
						salida.add(tmpLigaFuji);
					}
				} else {
					logger.error("Error en getLigaFuji: Orden sin estudios de imagen");
					throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Orden sin estudios de imagen");
				}
			} else {
				logger.error("Error en getLigaFuji: Orden cancelada");
				throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "Orden cancelada");
			}
		} else {
			logger.error("Error en getLigaFuji: Orden no valida");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Orden no valida ");
		}
		return salida;
	}

	/* Método para consultar el WS de CDMX
	 * - Se realizan tres intentos
	 * - En caso de que la orden no exista / no esten validados sus resultados el proceso se detiene
	 */
	public String getResultadosLabCdmx(String claveLabcore, Integer opcion) {
		String salida = null;
		wsclient.azteca.RespReporte tmpAzteca;
		int i = 1;
		boolean logo;
		logo = (opcion.intValue() >= 1) ? true : false;
		while (i < NO_INTENTOS.intValue()) {
			logger.debug("Consulta de WsAzteca, intento: {}", Integer.valueOf(i));
			try {
				tmpAzteca = consultaWebServices.consultaWsAzteca(claveLabcore, logo);
				if (tmpAzteca.isGenerado().booleanValue()) {
					salida = tmpAzteca.getArchivoBase64().getValue();
					break;
				}
				if ((tmpAzteca.getMensaje().getValue()).equals("La orden no existe.")	|| (tmpAzteca.getMensaje().getValue()).equals("Orden sin resultados validados."))
					break;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			i++;
		}
		return salida;
	}

	/* Método para consultar el WS de MTY
	 * - Se realizan tres intentos
	 * - En caso de que la orden no exista / no esten validados sus resultados el proceso se detiene
	 */
	public String getResultadosLabMty(String claveLabcore, Integer opcion) {
		String salida = null;
		wsclient.swiss.RespReporte tmpSwiss;
		int i = 1;
		boolean logo;
		logo = (opcion.intValue() >= 1) ? true : false;
		while (i < NO_INTENTOS.intValue()) {
			logger.debug("Consulta de WsSwiss, intento: {}", Integer.valueOf(i));
			try {
				tmpSwiss = consultaWebServices.consultaWsSwiss(claveLabcore, logo);
				if (tmpSwiss.isGenerado().booleanValue()) {
					salida = tmpSwiss.getArchivoBase64().getValue();
					break;
				}
				if ((tmpSwiss.getMensaje().getValue()).equals("La orden no existe.")|| (tmpSwiss.getMensaje().getValue()).equals("Orden sin resultados validados."))
					break;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			i++;
		}
		return salida;
	}

	/* Método para consultar el WS de Nova
	 * - Se realizan tres intentos
	 * - En caso de que la orden no exista / no esten validados sus resultados el proceso se detiene
	 */
	public String getResultadosLabNova(String claveLabcore, Integer opcion) {
		String salida = null;
		wsclient.nova.RespReporte tmpNova;
		int i = 1;
		boolean logo;
		logo = (opcion.intValue() >= 1) ? true : false;
		while (i < NO_INTENTOS.intValue()) {
			this.logger.debug("Consulta de WsNova, intento: {}", Integer.valueOf(i));
			try {
				tmpNova = consultaWebServices.consultaWsNova(claveLabcore, logo);
				if (tmpNova.isGenerado().booleanValue()) {
					salida = tmpNova.getArchivoBase64().getValue();
					break;
				}
				if ((tmpNova.getMensaje().getValue()).equals("La orden no existe.")|| (tmpNova.getMensaje().getValue()).equals("Orden sin resultados validados."))
					break;
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			i++;
		}
		return salida;
	}

	/* Método para consultar el WS de Patcore
	 * - Se realizan tres intentos
	 * - En caso de que la orden no exista el proceso se detiene
	 */
	private String getResultadosPatcore(String claveLabcore, Integer opcion) {
		String salida = null;
		wsclient.patcore.RespReporte tmpPatcore;
		int i = 1;
		boolean logo;
		logo = (opcion.intValue() >= 1) ? true : false;
		while (i < NO_INTENTOS.intValue()) {
			logger.debug("Consulta de WsPatcore, intento: {}", Integer.valueOf(i));
			try {
				tmpPatcore = consultaWebServices.consultaWsPatcore(claveLabcore, logo);
				if (tmpPatcore.isGenerado().booleanValue()) {
					salida = tmpPatcore.getArchivoBase64().getValue();
					break;
				}
				if ((tmpPatcore.getMensaje().getValue()).equals("La orden no existe.")) {
					break;
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
			i++;
		}
		return salida;
	}

	/* Método para consultar el/los PDF(s) en base64 de Fuji
	 * - Se realizan tres intentos
	 * - En caso de que la orden no exista el proceso se detiene
	 */
	public List<String> getResBase64FujiPdf(Integer cmarca, List<MuestraFuji> muestrasFuji) {
		List<String> salida = new ArrayList<>();
		int i = 1;
		for (MuestraFuji muestra : muestrasFuji) {
			logger.debug("Consulta de Fuji-PDF: csucursaltranslado {}, kmuestrasucursal {}",
					muestra.getCsucursaltranslado(), muestra.getKmuestrasucursal());
			i = 1;
			while (i < NO_INTENTOS.intValue()) {
				this.logger.debug("Consulta de WsFuji-PDF, intento: {}", Integer.valueOf(i));
				try {
					Response tmpFujiResponse = consultaWebServices.consultaWsFujiPdf(cmarca,
							muestra.getCsucursaltranslado(), muestra.getKmuestrasucursal());
					if (tmpFujiResponse.isSuccess()) {
						salida.add(Base64.getEncoder().encodeToString(tmpFujiResponse.getReporte()));
						break;
					}
					if (tmpFujiResponse.getMessage().equals("No se encontro documento disponible para mostrar"))
						break;
				} catch (Exception e) {
					logger.error("Error en getResBase64_WsFuji_PDF: {}", e.getMessage());
					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
							"Error en getResBase64_WsFuji_PDF");
				}
				i++;
			}
		}
		return salida;
	}

	/* Método para obtener todos los datos necesarios de un orden para la consulta de resultados */
	private TordenSucursal getDatosOrden(Long kordensucursal) {
		TordenSucursal salida = null;
		salida = consultaOrden(kordensucursal);
		if (salida != null) {
			salida.setbFuji((contieneEstudiosFuji(kordensucursal) >= 1) ? true : false);
			if (Boolean.TRUE.equals(salida.getbFuji())) {
				salida.setMuestrasFuji(getMuestrasFuji(kordensucursal));
			}
			salida.setbPatcore((contieneEstudiosPatcore(kordensucursal) >= 1) ? true : false);
		}
		return salida;
	}

	/* Método obtener los datos generales de la orden (estado, claveLabcore,csucursal,marca) */
	private TordenSucursal consultaOrden(Long kordensucursal) {
		TordenSucursal salida = null;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+ " b.kordensucursal, "
				+ "  case "
				+ "   when length(b.uorden) <= 6 then RPAD(b.ssucursal, 3, '0') || 'L' || LPAD(b.uorden, 6, '0') "
				+ "   else RPAD(b.ssucursal, 3, '0') || LPAD(b.uorden, 7, '0') "
				+ "  end as claveLabcore, "
				+ "  b.cmarca, "
				+ "  b.csucursal, "
				+ "  b.cestadoregistro "
				+ " from "
				+ "   public.t_orden_sucursal b "
				+ " where "
				+ "   b.kordensucursal=?1 "
			);
			q.setParameter(1, kordensucursal);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object[] r : results) {
				salida = new TordenSucursal(Long.valueOf(((BigDecimal) r[0]).longValue()), (String) r[1],
						Integer.valueOf(((BigDecimal) r[2]).intValue()), Long.valueOf(((BigDecimal) r[3]).longValue()),
						Long.valueOf(((BigDecimal) r[4]).longValue()));
			}
		} catch (Exception e) {
			logger.error("Error en consultaOrden: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}

	/* Método para validar si una orden tiene estudios de Imagen */
	private Integer contieneEstudiosFuji(Long kordensucursal) {
		int salida;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+ "   count(a.*) "
				+ " from "
				+ "   web2lablis.c_examen a, "
				+ "   web2lablis.c_departamento b "
				+ " where "
				+ "   a.cexamen in ( "
				+ "   select "
				+ "     distinct (c.cexamenproceso) "
				+ "   from "
				+ "     public.t_orden_sucursal a, "
				+ "     public.t_orden_examen_sucursal b, "
				+ "     web2lablis.c_examen c "
				+ "   where "
				+ "     a.kordensucursal=?1 "
				+ "     and b.kordensucursal=a.kordensucursal "
				+ "     and c.cexamen = b.cexamen "
				+ "     and c.ulaboratoriogabinete = 2  "
				+ "     and c.cdepartamento<>73 " // marketing
				+ "     ) "
				+ "   and b.cdepartamento = a.cdepartamento "
				+ "   and b.cdepartamento not in (100,114,115,116,123,130,117,118,119,137,120,109,132,131,133,127,136,129,73) "
			);
			q.setParameter(1, kordensucursal);
			salida = ((Number) q.getSingleResult()).intValue();
		} catch (Exception e) {
			logger.error("Error en contieneEstudiosDeFuji: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}

	/* Método obtener las muestras de imagen para consultar el WS_Fuji */
	private List<MuestraFuji> getMuestrasFuji(Long kordensucursal) {
		List<MuestraFuji> salida = new ArrayList<>();
		MuestraFuji tmp = null;
		try {
			Query q = entityManager.createNativeQuery(
					" select "
					+ "   c.cexamen, "
					+ "   c.sexamen, "
					+ "   c.cexamenproceso, "
					+ "   d.kmuestrasucursal, "
					+ "   b.csucursaltranslado "
					+ " from "
					+ "   public.t_orden_sucursal a, "
					+ "   public.t_orden_examen_sucursal b, "
					+ "   web2lablis.c_examen c, "
					+ "   public.r_examen_muestra_sucursal d "
					+ " where "
					+ "   a.kordensucursal =?1 "
					+ "   and b.kordensucursal = a.kordensucursal "
					+ "   and c.cexamen = b.cexamen "
					+ "   and c.cdepartamento <> 73 " // marketing
					+ "   and d.kordenexamensucursal = b.kordenexamensucursal "
					+ "   and exists ( "
					+ "   select "
					+ "       f.* "
					+ "   from "
					+ "       web2lablis.c_examen e, "
					+ "       web2lablis.c_departamento f "
					+ "   where "
					+ "       e.cexamen = c.cexamenproceso "
					+ "       and e.ulaboratoriogabinete=2 "
					+ "       and f.cdepartamento = e.cdepartamento "
					// " and f.cdepartamento not in (133,73,129) ) " //--serv
					// gabinete,marketing,productos
					+ "       and f.cdepartamento not in (100,114,115,116,123,130,117,118,119,137,120,109,132,131,133,127,136,129,73) ) "
			);
			q.setParameter(1, kordensucursal);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object[] r : results) {
				tmp = new MuestraFuji(
						((BigDecimal) r[0]).longValue(),
						(String) r[1],
						((BigDecimal) r[2]).longValue(),
						((BigDecimal) r[3]).longValue(),
						((BigDecimal) r[4]).longValue());
				salida.add(tmp);
			}
		} catch (Exception e) {
			logger.error("Error en getObjectMuestrasFuji: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}

	/* Método para validar si una orden tiene estudios de Patcore */
	private Integer contieneEstudiosPatcore(Long kordensucursal) {
		int salida;
		try {
			Query q = entityManager.createNativeQuery(
				" select"
				+ "   count(a.*)"
				+ " from"
				+ "   web2lablis.c_examen a,"
				+ "   web2lablis.c_departamento b"
				+ " where"
				+ "   a.cexamen in ("
				+ "   select"
				+ "       distinct (c.cexamenproceso)"
				+ "   from"
				+ "       public.t_orden_sucursal a,"
				+ "       public.t_orden_examen_sucursal b,"
				+ "       web2lablis.c_examen c"
				+ "   where"
				+ "       a.kordensucursal =?1"
				+ "       and b.kordensucursal = a.kordensucursal"
				+ "       and c.cexamen = b.cexamen )"
				+ "   and substring(a.scodigolis, 1, 3)= 'PAT'"
				+ "   and b.cdepartamento = a.cdepartamento "
			);
			q.setParameter(1, kordensucursal);
			salida = ((Number) q.getSingleResult()).intValue();
		} catch (Exception e) {
			logger.error("Error en mconsultaOrden: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}

}