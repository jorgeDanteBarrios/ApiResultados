package mx.gda.resultados.controller;

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
import mx.gda.resultados.services.ResultadosService;


@RestController
@CrossOrigin(origins = {"*"}, allowedHeaders = {"*"})
@RequestMapping({"/ApiResultados"})
@Api
public class ApiResultadosController {

	Logger logger = LoggerFactory.getLogger(ApiResultadosController.class);

	@Autowired
	private ResultadosService resultadosService;

	@GetMapping(value = { "/azteca/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Method to consult the PDF result (Labcore CDMX)")
	public byte[] getResultado_Azteca(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResultado_WsAzteca(orden, opcion);
	}

	@GetMapping(value = { "/swiss/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Method to consult the PDF result (Labcore MTY)")
	public byte[] getResultado_Swiss(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResultado_WsSwiss(orden, opcion);
	}

	@GetMapping(value = { "/nova/resultado" }, produces = { "application/pdf" })
	@ApiOperation("Method to consult the PDF result (Labcore Nova-MTY)")
	public byte[] getResultado_Nova(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResultado_WsNova(orden, opcion);
	}

	@GetMapping({ "/resultado/gda/base64" })
	@ApiOperation("Method to consult the result in base64 (All brands)")
	public ResponseEntity<String> getResBase64_AllBrands(@RequestParam Long kordensucursal,
			@RequestParam Integer opcion) throws Exception {
		return ResponseEntity.ok(this.resultadosService.getResultado(kordensucursal, opcion));
	}

	@GetMapping({ "/resultado/gda/base64_v2" })
	@ApiOperation("Method to consult the result in base64 (All brands)")
	public ResponseEntity<String> getResBase64_AllBrands2(@RequestParam Long kordensucursal,
			@RequestParam Integer opcion) throws Exception {
		return ResponseEntity
				.ok("{\"pdfBase64\":\"" + this.resultadosService.getResultado(kordensucursal, opcion) + "\"}");
	}

	@GetMapping({ "/azteca/resultado/base64" })
	@ApiOperation("Method to consult the result in base64 (Labcore CDMX)")
	public String getgResBase64_Azteca(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResBase64_WsAzteca(orden, opcion);
	}

	@GetMapping({ "/swiss/resultado/base64" })
	@ApiOperation("Method to consult the result in base64 (Labcore MTY)")
	public String getResBase64_Swiss(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResBase64_WsSwiss(orden, opcion);
	}

	@GetMapping({ "/nova/resultado/base64" })
	@ApiOperation("Method to consult the result in base64 (Labcore Nova)")
	public String getBase64_Nova(@RequestParam String orden, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResBase64_WsNova(orden, opcion);
	}

	@GetMapping(value = { "/evento" }, produces = { "application/zip" })
	@ApiOperation("Method to generate a zip with the results of an event")
	public byte[] getResultadosByEvento(@RequestParam Long kevento, @RequestParam Integer opcion) throws Exception {
		return this.resultadosService.getResultadosByEvento(kevento, opcion);
	}
	
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
