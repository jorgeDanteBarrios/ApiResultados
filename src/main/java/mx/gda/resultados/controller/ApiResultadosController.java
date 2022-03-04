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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import mx.gda.resultados.objects.LigaFuji;
import mx.gda.resultados.services.ResultadosService;

@RestController
@CrossOrigin(origins = { "*" }, allowedHeaders = { "*" })
@RequestMapping({ "/ApiResultados" })
@Api
public class ApiResultadosController {

	Logger logger = LoggerFactory.getLogger(ApiResultadosController.class);

	@Autowired
	private ResultadosService resultadosService;

	@GetMapping(value = { "/azteca/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Consulta el PDF de Resultados (byte[]) de Labore CDMX")
	public byte[] getResultado_Azteca(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getResultado_Azteca");
		logger.debug("\t getResultado_Azteca : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultado_WsAzteca(orden, opcion);
	}

	@GetMapping(value = { "/swiss/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Consulta el PDF de Resultados (byte[]) de Labore MTY")
	public byte[] getResultado_Swiss(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getResultado_Swiss");
		logger.debug("\t getResultado_Swiss : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultado_WsSwiss(orden, opcion);
	}

	@GetMapping(value = { "/nova/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Consulta el PDF de Resultados (byte[]) de Labore  Nova")
	public byte[] getResultado_Nova(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getResultado_Nova");
		logger.debug("\t getResultado_Nova : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResultado_WsNova(orden, opcion);
	}

	@GetMapping("/resultado/amazon/base64")
	@ApiOperation("Consulta el PDF de Resultados del Portal de Amazon")
	public ResponseEntity<String> getResultadoAmazon(@NotNull @RequestParam Long korden,@NotNull @RequestParam Integer opcion) {
		logger.info("Se consume método getResultadoAmazon");
		logger.debug("\t getResultadoAmazon : [ korden-{}, opcion-{} ]", korden,opcion);
		return ResponseEntity.ok(resultadosService.getResultadosAmazon(korden));
	}
	
	@GetMapping("/resultado/amazon/certificado/base64")
	@ApiOperation("Consulta el Certificado del Portal de Amazon")
	public ResponseEntity<String> getCertificadoAmazon(@NotNull @RequestParam Long korden) {
		logger.info("Se consume método getCertificadoAmazon");
		logger.debug("\t getResultadoAmazon : [ korden-{} ]", korden);
		return ResponseEntity.ok(resultadosService.getCertificadoAmazon(korden));
	}

	@GetMapping("/resultado/gda/base64")
	@ApiOperation("Consulta el PDF de Resultados(base64) (laboratorio,imagen,patologia) ")
	public ResponseEntity<String> getResBase64_AllBrands(@RequestParam Long kordensucursal,	@RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getResBase64_AllBrands");
		logger.debug("\t getResBase64_AllBrands : [ kordensucursal-{}, opcion-{} ]", kordensucursal,opcion);
		return ResponseEntity.ok(resultadosService.getResultado(kordensucursal, opcion));
	}
	
	@GetMapping("/resultado/imagen/url")
	@ApiOperation("Método para consultar las url de los estudios de imagen de una orden")
	public ResponseEntity<List<LigaFuji>> getFujiURL(@NotNull @RequestParam Long kordensucursal){
		logger.info("Se consume método getFujiURL");
		logger.debug("\t getFujiURL : [ kordensucursal-{} ]", kordensucursal);
		return ResponseEntity.ok(resultadosService.getLigaFuji(kordensucursal));
	}

	@GetMapping("/azteca/resultado/base64")
	@ApiOperation("Consulta el PDF de Resultados (base64) de Labore CDMX")
	public String getgResBase64_Azteca(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getgResBase64_Azteca");
		logger.debug("\t getgResBase64_Azteca : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResBase64_WsAzteca(orden, opcion);
	}

	@GetMapping("/swiss/resultado/base64")
	@ApiOperation("Consulta el PDF de Resultados (base64) de Labore MTY")
	public String getResBase64_Swiss(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getResBase64_Swiss");
		logger.debug("\t getResBase64_Swiss : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResBase64_WsSwiss(orden, opcion);
	}

	@GetMapping({ "/nova/resultado/base64" })
	@ApiOperation("Consulta el PDF de Resultados (base64) de Labore Nova")
	public String getBase64_Nova(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		logger.info("Se consume método getBase64_Nova");
		logger.debug("\t getBase64_Nova : [ orden-{}, opcion-{} ]", orden,opcion);
		return resultadosService.getResBase64_WsNova(orden, opcion);
	}

	/*
	@GetMapping(value = { "/evento" }, produces = { "application/zip" })
	@ApiOperation("Method to generate a zip with the results of an event")
	public byte[] getResultadosByEvento(@RequestParam Long kevento, @RequestParam Integer opcion) throws Exception {
		return resultadosService.getResultadosByEvento(kevento, opcion);
	}
	*/

	/*
	 * //download automatically the file
	 * 
	 * @GetMapping(value = "/azteca3/resultado") public ResponseEntity<byte[]>
	 * getResultado_Azteca3(@RequestParam String orden,@RequestParam Integer opcion)
	 * throws Exception{ HttpHeaders headers = new HttpHeaders();
	 * headers.setContentType(MediaType.APPLICATION_PDF); String filename =
	 * orden+".pdf"; headers.setContentDispositionFormData(filename, filename);
	 * headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	 * ResponseEntity<byte[]> response = new
	 * ResponseEntity<>(resultadosService.getResultado_WsAzteca(orden, opcion),
	 * headers, HttpStatus.OK); return response; }
	 */
}
