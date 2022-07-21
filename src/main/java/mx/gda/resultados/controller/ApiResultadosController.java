package mx.gda.resultados.controller;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import mx.gda.resultados.objects.LigaFuji;
import mx.gda.resultados.services.AmazonService;
import mx.gda.resultados.services.ResultadosService;

@RestController
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequestMapping("/ApiResultados")
public class ApiResultadosController {

	Logger logger = LoggerFactory.getLogger(ApiResultadosController.class);

	@Autowired
	private ResultadosService resultadosService;
	@Autowired
	private AmazonService amazonService;

	@GetMapping("/azteca/resultado/base64")
	@Operation(description = "Método para obtener el PDF  de resultados en base64  registrados en Labore CDMX", tags = {"PDF Base64"})
	public String getgResBase64Azteca(@RequestParam String orden, @RequestParam Integer opcion) {
		logger.info("Se consume método getgResBase64_Azteca");
		logger.debug("\t getgResBase64_Azteca : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultadosLabCdmx(orden, opcion);
	}
	
	@GetMapping("/nova/resultado/base64")
	@Operation(description = "Método para obtener el PDF  de resultados en base64  registrados en Labore Nova", tags = {"PDF Base64"})
	public String getBase64Nova(@RequestParam String orden, @RequestParam Integer opcion) {
		logger.info("Se consume método getBase64_Nova");
		logger.debug("\t getBase64_Nova : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultadosLabNova(orden, opcion);
	}

	@GetMapping("/resultado/amazon/base64")
	@Operation(description = "Consulta el PDF de Resultados del Portal de Amazon", tags = {"Amazon"})
	public ResponseEntity<String> getResultadoAmazon(@NotNull @RequestParam Long korden,@NotNull @RequestParam Integer opcion) {
		logger.info("Se consume método getResultadoAmazon");
		logger.debug("\t getResultadoAmazon : [ korden-{}, opcion-{} ]", korden,opcion);
		return ResponseEntity.ok(amazonService.getResultadosAmazon(korden));
	}
	
	@GetMapping("/resultado/amazon/certificado/base64")
	@Operation(description = "Consulta el Certificado del Portal de Amazon", tags = {"Amazon"})
	public ResponseEntity<String> getCertificadoAmazon(@NotNull @RequestParam Long korden) {
		logger.info("Se consume método getCertificadoAmazon");
		logger.debug("\t getResultadoAmazon : [ korden-{} ]", korden);
		return ResponseEntity.ok(amazonService.getCertificadoAmazon(korden));
	}
	
	@GetMapping("/resultado/gda/base64")
	@Operation(description = "Método para obtener el PDF  de resultados en base64, combina todos los resultados (laboratorio, interpretaciones de imagen, patología) en un solo archivo", tags = {"PDF Base64"})
	public ResponseEntity<String> getResBase64AllBrands(@RequestParam Long kordensucursal,	@RequestParam Integer opcion){
		logger.info("Se consume método getResBase64_AllBrands");
		logger.debug("\t getResBase64_AllBrands : [ kordensucursal-{}, opcion-{} ]", kordensucursal,opcion);
		return ResponseEntity.ok(resultadosService.getResultadoGDA(kordensucursal, opcion));
	}

	@GetMapping("/resultado/imagen/url")
	@Operation(description = "Método para generar las ligas del visor de Fuji (mobility) de  el/los estudio(s) de imagen de una orden", tags = {"Fuji - Mobility"})
	public ResponseEntity<List<LigaFuji>> getFujiURL(@NotNull @RequestParam Long kordensucursal){
		logger.info("Se consume método getFujiURL");
		logger.debug("\t getFujiURL : [ kordensucursal-{} ]", kordensucursal);
		return ResponseEntity.ok(resultadosService.getLigaFuji(kordensucursal));
	}

	@GetMapping("/swiss/resultado/base64")
	@Operation(description = "Método para obtener el PDF  de resultados en base64  registrados en Labore MTY", tags = {"PDF Base64"})
	public String getResBase64Swiss(@RequestParam String orden, @RequestParam Integer opcion) {
		logger.info("Se consume método getResBase64_Swiss");
		logger.debug("\t getResBase64_Swiss : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultadosLabMty(orden, opcion);
	}

}
