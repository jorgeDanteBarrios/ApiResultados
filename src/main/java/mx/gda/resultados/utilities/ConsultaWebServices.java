package mx.gda.resultados.utilities;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class ConsultaWebServices {

    Logger logger=LoggerFactory.getLogger(ConsultaWebServices.class);

    @Value("${app.parametro.segundos.timeout.labcore}")
	Integer TIME_OUT_SECONDS;
    @Value("${app.parametro.segundos.expira.ligafuji}")
	Integer EXPIRATION_SECONDS_URL_FUJI;
    
    /* Método para consultar el servivio de Azteca */
	public wsclient.azteca.RespReporte consultaWsAzteca(String orden, Boolean logo) {
		wsclient.azteca.RespReporte salida = null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<wsclient.azteca.RespReporte> futureTask1 = executor.submit(() -> {
				wsclient.azteca.WsLabCore service = new wsclient.azteca.WsLabCore();
				wsclient.azteca.IwsReporte port = service.getBasicHttpBindingIwsReporte();
				return port.getReporte(orden, logo);
			});
			salida = futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.error("Timeout al consultar ws Azteca ({} seconds)", TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Timeout al consultar ws Azteca (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			logger.error("Error al consultar ws Azteca: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar ws Azteca");
		} finally {
			executor.shutdown();
		}
		return salida;
	}

	/* Método para consultar el servivio de Swiss */
	public wsclient.swiss.RespReporte consultaWsSwiss(String orden, Boolean logo){
		wsclient.swiss.RespReporte salida = null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<wsclient.swiss.RespReporte> futureTask1 = executor.submit(() -> {
				wsclient.swiss.WsLabCore service = new wsclient.swiss.WsLabCore();
				wsclient.swiss.IwsReporte port = service.getBasicHttpBindingIwsReporte();
				return port.getReporte(orden, logo);
			});
			salida = futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			this.logger.error("Timeout al consultar ws Azteca ({} seconds)",TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,	"Timeout al consultar ws Swiss (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			this.logger.error("Error al consultar ws Swiss: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar ws Swiss");
		} finally {
			executor.shutdown();
		}
		return salida;
	}

	/* Método para consultar el servivio de Nova*/
	public wsclient.nova.RespReporte consultaWsNova(String orden, Boolean logo) {
		wsclient.nova.RespReporte salida = null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<wsclient.nova.RespReporte> futureTask1 = executor.submit(() -> {
				wsclient.nova.WsLabCore service = new wsclient.nova.WsLabCore();
				wsclient.nova.IwsReporte port = service.getBasicHttpBindingIwsReporte();
				return port.getReporte(orden, logo);
			});
			salida = futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.error("Timeout al consultar ws Nova ({} seconds)",TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Timeout al consultar ws Nova (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			logger.error("Error al consultar ws Nova: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar ws Nova");
		} finally {
			executor.shutdown();
		}
		return salida;
	}

	/* Método para consultar el servivio de Patcore*/
	public wsclient.patcore.RespReporte consultaWsPatcore(String orden, Boolean logo) {
		wsclient.patcore.RespReporte salida = null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<wsclient.patcore.RespReporte> futureTask1 = executor.submit(() -> {
				wsclient.patcore.Service1 serv = new wsclient.patcore.Service1();
				wsclient.patcore.IwsPatCore iPatcore = serv.getBasicHttpBindingIwsPatCore();
				return iPatcore.getReporte(orden, logo);
			});
			salida = futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.error("Timeout al consultar ws Nova ({} seconds)",TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Timeout al consultar ws Nova (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			logger.error("Error al consultar ws Nova: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar ws Nova");
		} finally {
			executor.shutdown();
		}
		return salida;
	}

	/* Método para consultar el servivio de Fuji , método para obtener el PDF*/
	public wsclient.fuji.Response consultaWsFujiPdf(Integer cmarca, Long csucursal, Long kmuestrasucursal) {
		wsclient.fuji.Response salida = null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<wsclient.fuji.Response> futureTask1 = executor.submit(() -> {
				wsclient.fuji.ReportWebService r = new wsclient.fuji.ReportWebService();
				wsclient.fuji.ReportWebServiceSoap iFuji = r.getReportWebServiceSoap();
				return iFuji.reporteLogoBase64(generaNumAccesoFuji(cmarca, csucursal, kmuestrasucursal));
			});
			salida = futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.error("Timeout al consultar consulta_WsFuji_PDF ({} seconds)",TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Timeout al consultar ws Fuji (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			logger.error("Error al consultar consulta_WsFuji_PDF: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar consulta_WsFuji_PDF");
		} finally {
			executor.shutdown();
		}
		return salida;
	}
	
	/* Método para consultar el Ws de fuji y obtener la liga para visualizar imagenes  */
	public String consultaWsFujiUrl(String numeroDeAcceso) {
		String salida=null;
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		try {
			Future<String> futureTask1 = executor.submit(() -> {
				wsclient.fuji.url.WsGetLink service= new wsclient.fuji.url.WsGetLink ();
				wsclient.fuji.url.WsGetLinkSoap port= service.getWsGetLinkSoap();
				return port.obtenerLink(numeroDeAcceso, EXPIRATION_SECONDS_URL_FUJI);
			});
			salida= futureTask1.get(TIME_OUT_SECONDS.intValue(), TimeUnit.SECONDS);
		} catch (TimeoutException e) {
			logger.error("Timeout al consultar consulta_WSFujiURL ({} seconds)",TIME_OUT_SECONDS);
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Timeout al consultar consulta_WSFujiURL (" + TIME_OUT_SECONDS + " seconds)");
		} catch (Exception e) {
			logger.error("Error al consultar consulta_WSFujiURL: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al consultar consulta_WSFujiURL");
		} finally {
			executor.shutdown();
		}
		return salida;
	}

    /* Método para generar el campo (numAcceso) para consultar las ordenes en los WS de Fuji */
	public String generaNumAccesoFuji(Integer cmarca, Long csucursaltranslado, Long kmuestrasucursal) {
		String salida = null;
		String letraSucursal = null;
		switch (cmarca.intValue()) {
			case 1:
				letraSucursal = "O";
				break;
			case 2:
				letraSucursal = "O";
				break;
			case 3:
				letraSucursal = "O"; //Olab
				break;
			case 4:
				letraSucursal = "A"; //Azteca
				break;
			case 5:
				letraSucursal = "S"; //Swiss
				break;
			case 7:
				letraSucursal = "J";  //Jenner
				break;
			case 15:
				letraSucursal = "L";  //Liacsa
				break;
			/*
			case :
				letra_sucursal = "B"; //biomedica
				break;
			case :
				letra_sucursal = "E"; //Exacta
				break;
			*/
			default:
				this.logger.error(" Error en generaNumAccesoFuji: Marca no definida para generar clave");
				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,	" Error en generaNumAccesoFuji: Marca no definida para generar clave");
		}
		salida=letraSucursal + csucursaltranslado + "-" + kmuestrasucursal;
		return salida;
	}
    
}
