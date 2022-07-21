package mx.gda.resultados.services;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import mx.gda.resultados.objects.ReporteAmazon;
import mx.gda.resultados.objects.ResultadoJasperAmazon;
import mx.gda.resultados.utilities.UtilBase64;
import mx.gda.resultados.utilities.UtilFiles;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.util.Locale;
import java.util.Date;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import java.util.HashMap;
import java.util.Map;

@Service
public class AmazonService {
    
    Logger logger= LoggerFactory.getLogger(AmazonService.class);

    @PersistenceContext
	private EntityManager entityManager;
    @Autowired
    private UtilBase64 utilBase64;
	@Autowired
	private UtilFiles utilFiles;
    @Value("${app.parametro.idkeventoamazon}")
	Long KEVENTO_AMAZON;
	private String PATH_IMG="/static/img/";
	private String PATH_FILES="/static/files/";
	private String JASPER_FILE_EXTENSION="jrxml";
	private String IMG_FILE_EXTENSION="png";

    public String getResultadosAmazon(Long korden) {
		String salida=null;	
		ResultadoJasperAmazon tmp=null;
		List<String> resultados= new ArrayList<String>();
		tmp=getDatosJasperAmazon(korden);
		if(tmp==null) {
			logger.error("Error en getResultadosAmazon: {}", "Orden inexistente o sin resultados");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favor de validar el korden,Orden inexistente o sin resultados");
		}
		if(tmp.getFecha_orina()!=null) {
			resultados.add(getReporteOrinaAmazon(tmp));
			resultados.add(getReporteSalivaAmazon(tmp));
			salida=utilBase64.combinePDFs(resultados);
		}else {
			salida=getReporteSalivaAmazon(tmp);
		}
		return salida;
	}
	
	/* Método para descargar el certificado de Amzon por # korden */
	public String getCertificadoAmazon(Long korden) {
		String salida=null;
		Integer estatusAmz=null;
		ReporteAmazon tmpReporte=null;
		estatusAmz=getEstatusAmz(korden);
		if(estatusAmz==null) {
			this.logger.error("Error en getCertificadoAmazon: {}", "Orden inexistente o sin resultados");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favor de validar el korden, Orden inexistente");
		}else if(estatusAmz.equals(6) || estatusAmz.equals(7)) {
			tmpReporte=getDatosReporteAmazon(korden);
			salida=getReporteAmazon(tmpReporte);
		}else{
			this.logger.error("Error en getCertificadoAmazon: {}", "Orden pendiente de registro de resultados");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Favor de validar, Orden pendiente de registro de resultados");
		}
		return salida;
	}
	
	/* Método para obteber la clave Labcore de una orden del Evento de Amzaon */
	public String getOrdenLabcoreAmazon(Long korden) {
		String salida=null;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+"   a.kordensucursal, "
				+"   case "
				+"       when length(b.uorden) <= 6 then RPAD(b.ssucursal, 3, '0') || 'L' || LPAD(b.uorden, 6, '0') "
				+"       else RPAD(b.ssucursal, 3, '0') || LPAD(b.uorden, 7, '0') "
				+"   end as claveLabcore "
				+" from "
				+"   eventos.t_orden a, "
				+"   public.t_orden_sucursal b "
				+" where   "
				+"   a.korden=?1 "
				+"   and a.kevento=?2 "
				+"   and a.kordensucursal  is not null "
				+"   and a.benviadolabcore=true "
				+"   and b.kordensucursal=a.kordensucursal "
			);
			q.setParameter(1, korden);
			q.setParameter(2, KEVENTO_AMAZON);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object[] r : results) {
				salida =((BigDecimal) r[0]).toString()+":" +(String) r[1];						
			}
			results.clear();
		} catch (Exception e) {
			logger.error("Error en getOrdenLabcoreAmazon: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}
	
	/* Método para obtener la información del reporte de Amazon */
	public ReporteAmazon getDatosReporteAmazon(Long korden) {
		ReporteAmazon salida=null;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+"   a.snombre||' '||a.sapellidopaterno||' '||a.sapellidomaterno  as paciente, "
				+"   extract( year from age(trunc(a.dreg),a.dnacimiento)) as edad, "
				+"   b.stipo as tipo,  "
				+"   to_char(c.dreg, 'dd/MM/yyyy') as fechaToma, "
				+"   d.snombre||' '||d.sapellidopaterno||' '||d.sapellidomaterno  as medico, "
				+"   e.snum_cedula as cedula, "
				+"   e.sfirma as firma, "
				+"   c.sdiagnostico, "
				+"   c.sresultado  "
				+" from "
				+"   eventos.t_orden a, "
				+"   eventos.t_orden_detalle_amz b, "
				+"   eventos.t_amz_cuestionario c, "
				+"   portales.c_users d, "
				+"   eventos.c_firmas_amz e "
				+" where  "
				+"   a.korden=?1 "
				+"   and b.korden=a.korden  "
				+"   and c.korden=b.korden  "
				+"   and d.login =c.user_reg  "
				+"   and e.kuser(+)=d.kuser "
			);
			q.setParameter(1, korden);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object[] r : results) {
				salida=new ReporteAmazon(
						(String) r[0], //nombre_paciente
						Double.toString(((Double) r[1]).doubleValue()),//edad_paciente
						(String) r[2],//tipo
						(String) r[3],//fecha_toma
						(String) r[4],//nombre_medico
						(String) r[5],//cedula_medico
						(String) r[6],//firmaBase64_medico
						(String) r[7],//diagnostico
						(String) r[8]//resultad
				);
			}
			results.clear();
		} catch (Exception e) {
			logger.error("Error en getDatosReporteAmazon: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}
	
	
	/* Método para generar el Reporte de Resultados del Evento de Amazon */
	public String getReporteAmazon(ReporteAmazon reporte) {
		String salida=null;
		File tempTemplate=null;
		File tempFile=null;
		try {			
			//
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			LocalDate localDate = LocalDate.parse(reporte.getFecha_toma(), formatter);
			Locale spanishLocale=new Locale("es", "ES");
			String dateInSpanish=localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM ; yyyy",spanishLocale));
			dateInSpanish=dateInSpanish.replace(";", "del");
			// Generate Report
			//JasperDesign etiquetaReport=JRXmlLoader.load(new File(System.getProperty("user.dir")+"/Reporte_Amazon.jrxml"));			
			tempTemplate=utilFiles.getFileFromResource(PATH_FILES, "Reporte_Amazon", JASPER_FILE_EXTENSION);
			JasperDesign etiquetaReport=JRXmlLoader.load(tempTemplate);			
			Map<String,Object> param = new HashMap<>();
			param.put("nombre_paciente", reporte.getNombre_paciente());
			param.put("edad", reporte.getEdad_paciente());
			param.put("tipo", reporte.getTipo());
			param.put("fecha_toma",dateInSpanish);
			param.put("nombre_medico", reporte.getNombre_medico());
			param.put("cedula_medico", reporte.getCedula_medico());
			param.put("img_base64", reporte.getFirmaBase64_medico());
			param.put("diagnostico", reporte.getDiagnostico());
			param.put("resultado", reporte.getResultado());		
			JasperReport jasperReport  = JasperCompileManager.compileReport(etiquetaReport);
			JasperPrint jp=JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
			//Create tmp file
			tempFile=File.createTempFile("Reporte_Amz_", ".pdf");
			logger.debug("tempFile path : {}",tempFile.getAbsolutePath());
			JasperExportManager.exportReportToPdfFile(jp,tempFile.getAbsolutePath());
			salida=utilBase64.encodeFileToBase64(tempFile.getAbsolutePath());			
		} catch (Exception e) {
			logger.error("Error en método getReporteAmazon: {} ",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}finally{
            if(tempTemplate!=null && tempTemplate.exists()){
				if(!tempTemplate.delete()){
                    logger.error("Error en al borrar el template Reporte_Amazon.jrxml");
                }
			}			
			if(tempFile!=null && tempFile.exists()){
                if(!tempFile.delete()){
                    logger.error("Error en al borrar el archivo en  getReporteAmazon");
                }
            }
        }
		return salida;
	}
	
	/* Método para validar el estatus de la orden AMZ */
	public Integer getEstatusAmz(Long korden) {
		Integer salida=null;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+"   kestatamz "
				+" from "
				+"   eventos.t_orden_detalle_amz "
				+" where "
				+"   korden=?1"
			);
			q.setParameter(1, korden);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object r : results) {
				salida=((BigDecimal) r).intValue();
			}
			results.clear();
		} catch (Exception e) {
			logger.error("Error en getEstatusAmz: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}
	
	/* Método para obtener la información del reporte de Amazon */
	public ResultadoJasperAmazon getDatosJasperAmazon(Long korden) {
		ResultadoJasperAmazon salida=null;
		try {
			Query q = entityManager.createNativeQuery(
				" select "
				+"   case "
				+"       when length(b.uorden) <= 6 then RPAD(b.ssucursal, 3, '0') || 'L' || LPAD(b.uorden, 6, '0') "
				+"       else RPAD(b.ssucursal, 3, '0') || LPAD(b.uorden, 7, '0') "
				+"   end as claveLabcore, "
				+"   a.snombre||' '||a.sapellidopaterno||' '||a.sapellidomaterno  as paciente, "
				+"   cast('TO WHOM IT MAY CONCERN' as varchar) as doctor, "
				+"   to_char(a.kordensucursal) as kordensucursal, "
				+"   extract( year from age(trunc(a.dreg),a.dnacimiento)) as edad_paciente, "
				+"   case "
				+"       when a.bsexo=0 then 'FEMALE' "
				+"       else 'MALE' "
				+"   end as sexo, "
				+"   case "
				+"       when d.dreg_saliva2  is not null then to_char(d.dreg_saliva2, 'MM/dd/yyyy HH24:MI:SS') "
				+"       when d.dreg_saliva is not null then to_char(d.dreg_saliva, 'MM/dd/yyyy HH24:MI:SS') "
				+"       else  '' "
				+"   end as fecha_saliva, "
				+"   case "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_mpa Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_mpa2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_amp, "
				+"   case  "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_bar Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_bar2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_bar, "
				+"   case  "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_bzd Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_bzd2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_bzd, "
				+"   case  "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_thc Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_thc2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_thc, "
				+"   case  "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_coc Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_coc2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_coc, "
				+"   case  "
				+"       when d.dreg_orina is not null then Cast(Case When d.bres_sal_met Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"       else Cast(Case When d.bres_sal_met2 Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) "
				+"   end as res_sal_met, "
				+"   to_char(d.dreg_orina, 'MM/dd/yyyy HH24:MI:SS') as f_orina, "
				+"   Cast(Case When d.bres_orina_mpa Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_mpa, "
				+"   Cast(Case When d.bres_orina_bar Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_bar, "
				+"   Cast(Case When d.bres_orina_bzd Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_bzd, "
				+"   Cast(Case When d.bres_orina_thc Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_thc, "
				+"   Cast(Case When d.bres_orina_coc Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_coc, "
				+"  Cast(Case When d.bres_orina_met Then 'POSITIVE' ELse 'NEGATIVE' END AS Varchar) as res_orina_met  "
				+" from "
				+"   eventos.t_orden a, "
				+"   public.t_orden_sucursal b, "
				+"   eventos.t_orden_detalle_amz c, "
				+"   eventos.t_amz_toxicologico d "
				+" where  "
				+"   a.korden=?1 "
				+"   and a.kevento=?2 "
				+"   and a.cestatus=3 "
				+"   and b.kordensucursal=a.kordensucursal "
				+"   and c.korden=a.korden  "
				+"   and c.kestatamz in (6,7) "
				+"   and d.korden=c.korden "
			);
			q.setParameter(1, korden);
			q.setParameter(2, KEVENTO_AMAZON);
			@SuppressWarnings("unchecked")
			List<Object[]> results = q.getResultList();
			for (Object[] r : results) {
				salida=new ResultadoJasperAmazon(
						(String) r[0],   //claveLabcore
						(String) r[1],   //paciente
						(String) r[2],   //doctor
						(String) r[3],   //kordensucursal
						Double.toString(((Double) r[4]).doubleValue()),//edad_paciente
						(String) r[5],   //sexo
						(String) r[6],   //fecha_saliva
						(String) r[7],   //res_sal_amp
						(String) r[8],   //res_sal_bar
						(String) r[9],   //res_sal_bzd
						(String) r[10],  //res_sal_thc
						(String) r[11],  //res_sal_coc
						(String) r[12],  //res_sal_met
						(String) r[13],  //fecha_orina
						(String) r[14],  //res_orina_amp
						(String) r[15],  //res_orina_bar
						(String) r[16],  //res_orina_bzd
						(String) r[17],  //res_orina_thc
						(String) r[18],  //res_orina_coc
						(String) r[19]   //res_orina_met
				);
			}
			results.clear();
		} catch (Exception e) {
			logger.error("Error en getDatosJasperAmazon: {}", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		} finally {
			entityManager.close();
		}
		return salida;
	}
	
	/* Método para generar el Reporte de Resultados del Evento de Amazon */
	public String getReporteSalivaAmazon(ResultadoJasperAmazon reporte) {
		String salida=null;
		File tempTemplate=null;
		File tempFile=null;
		try {			
			//
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String actualDate = simpleDateFormat.format(new Date());
			// Generate Report
			//JasperDesign salivaReport=JRXmlLoader.load(new File(System.getProperty("user.dir")+"/Saliva_drug_test.jrxml"));	
			tempTemplate=utilFiles.getFileFromResource(PATH_FILES, "Saliva_drug_test", JASPER_FILE_EXTENSION);	
			JasperDesign salivaReport=JRXmlLoader.load(tempTemplate);
			Map<String,Object> param = new HashMap<>();
			param.put("ordenLabcore", reporte.getOrdenLabcore());
			param.put("paciente", reporte.getPaciente());
			param.put("doctor", reporte.getDoctor());
			param.put("kordensucursal",reporte.getKordensucursal());
			param.put("edad",reporte.getEdad_paciente());
			param.put("sexo", reporte.getSexo());
			param.put("fechaEmision",actualDate );
			param.put("resul_AMP", reporte.getRes_sal_amp());
			param.put("resul_BAR", reporte.getRes_sal_bar());
			param.put("resul_BZD", reporte.getRes_sal_bzd());
			param.put("resul_THC", reporte.getRes_sal_thc());
			param.put("resul_COC", reporte.getRes_sal_coc());
			param.put("resul_MET", reporte.getRes_sal_met());
			param.put("fecha_liberacion", reporte.getFecha_saliva());
			/*
			param.put("path_firma", System.getProperty("user.dir")+"/Firma.png");
			param.put("path_gda", System.getProperty("user.dir")+"/GDA_white.png");
			param.put("path_frase", System.getProperty("user.dir")+"/FraseOlab.png");
			param.put("path_logo", System.getProperty("user.dir")+"/Olab.png");
			*/
			param.put("path_firma", fileToBase64(PATH_IMG, "Firma", IMG_FILE_EXTENSION));
			param.put("path_gda", fileToBase64(PATH_IMG, "GDA_white", IMG_FILE_EXTENSION));
			param.put("path_frase", fileToBase64(PATH_IMG, "FraseOlab", IMG_FILE_EXTENSION));
			param.put("path_logo", fileToBase64(PATH_IMG, "Olab", IMG_FILE_EXTENSION));
			JasperReport jasperReport  = JasperCompileManager.compileReport(salivaReport);
			JasperPrint jp=JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
			//Create tmp file
			tempFile=File.createTempFile("Reporte_Amz_", ".pdf");
			logger.debug("tempFile path : {}",tempFile.getAbsolutePath());
			JasperExportManager.exportReportToPdfFile(jp,tempFile.getAbsolutePath());			
			salida=utilBase64.encodeFileToBase64(tempFile.getAbsolutePath());			
		} catch (Exception e) {
			logger.error("Error en método getReporteSalivaAmazon: {} ",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}finally {
			if(tempTemplate!=null && tempTemplate.exists()){
				if(!tempTemplate.delete()){
                    logger.error("Error en al borrar el template Reporte_Amazon.jrxml");
                }
			}
			if(tempFile!=null && tempFile.exists()) {
				if(!tempFile.delete()){
                    logger.error("Error en al borrar el archivo en  getReporteSalivaAmazon");
                }
			}
		}
		return salida;
	}
	
	/* Método para generar el Reporte de Resultados del Evento de Amazon */
	public String getReporteOrinaAmazon(ResultadoJasperAmazon reporte) {
		String salida=null;
		File tempTemplate=null;
		File tempFile=null;
		try {			
			//
			String pattern = "MM/dd/yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String actualDate = simpleDateFormat.format(new Date());
			// Generate Report
			//JasperDesign orinaReport=JRXmlLoader.load(new File(System.getProperty("user.dir")+"/Urine_drug_test.jrxml"));			
			tempTemplate=utilFiles.getFileFromResource(PATH_FILES, "Urine_drug_test", JASPER_FILE_EXTENSION);	
			JasperDesign orinaReport=JRXmlLoader.load(tempTemplate);
			Map<String,Object> param = new HashMap<>();
			param.put("ordenLabcore", reporte.getOrdenLabcore());
			param.put("paciente", reporte.getPaciente());
			param.put("doctor", reporte.getDoctor());
			param.put("kordensucursal",reporte.getKordensucursal());
			param.put("edad",reporte.getEdad_paciente());
			param.put("sexo", reporte.getSexo());
			param.put("fechaEmision",actualDate );
			param.put("resul_AMP", reporte.getRes_orina_amp());
			param.put("resul_BAR", reporte.getRes_orina_bar());
			param.put("resul_BZD", reporte.getRes_orina_bzd());
			param.put("resul_THC", reporte.getRes_orina_thc());
			param.put("resul_COC", reporte.getRes_orina_coc());
			param.put("resul_MET", reporte.getRes_orina_met());
			param.put("fecha_liberacion", reporte.getFecha_orina());
			/* 
			param.put("path_firma", System.getProperty("user.dir")+"/Firma.png");
			param.put("path_gda", System.getProperty("user.dir")+"/GDA_white.png");
			param.put("path_frase", System.getProperty("user.dir")+"/FraseOlab.png");
			param.put("path_logo", System.getProperty("user.dir")+"/Olab.png");
			*/
			param.put("path_firma", fileToBase64(PATH_IMG, "Firma", IMG_FILE_EXTENSION));
			param.put("path_gda", fileToBase64(PATH_IMG, "GDA_white", IMG_FILE_EXTENSION));
			param.put("path_frase", fileToBase64(PATH_IMG, "FraseOlab", IMG_FILE_EXTENSION));
			param.put("path_logo", fileToBase64(PATH_IMG, "Olab", IMG_FILE_EXTENSION));
			JasperReport jasperReport  = JasperCompileManager.compileReport(orinaReport);
			JasperPrint jp=JasperFillManager.fillReport(jasperReport, param,new JREmptyDataSource());
			//Create tmp file
			tempFile=File.createTempFile("Reporte_Amz_", ".pdf");
			logger.debug("tempFile path : {}",tempFile.getAbsolutePath());
			JasperExportManager.exportReportToPdfFile(jp,tempFile.getAbsolutePath());	
			salida=utilBase64.encodeFileToBase64(tempFile.getAbsolutePath());			
		} catch (Exception e) {
			logger.error("Error en método getReporteOrinaAmazon: {} ",e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}finally {
			if(tempTemplate!=null && tempTemplate.exists()){
				if(!tempTemplate.delete()){
                    logger.error("Error en al borrar el template Reporte_Amazon.jrxml");
                }
			}
			if(tempFile!=null && tempFile.exists()) {
				if(!tempFile.delete()){
                    logger.error("Error en al borrar el archivo en  getReporteOrinaAmazon");
                }
			}
		}
		return salida;
	}

	/* Método para obtener el base 64 de un archivo almacenado dentro del JAR */
	public String fileToBase64(String path,String fileName,String extension){
		String salida=null;
		File tmp=null;
		try {
			tmp=utilFiles.getFileFromResource(path, fileName, extension);
			salida=utilBase64.encodeFileToBase64(tmp);
		} catch (Exception e) {
			logger.error("Error en fileToBase64: {} ", e.getMessage());
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}finally{
			if(tmp!=null && tmp.exists()) {
				if(!tmp.delete()){
                    logger.error("Error en al borrar el archivo en  fileToBase64");
                }
			}
		}
		return salida;
	}

}
