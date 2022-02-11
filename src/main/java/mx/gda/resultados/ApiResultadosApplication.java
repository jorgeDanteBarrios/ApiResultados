package mx.gda.resultados;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableScheduling
@EnableSwagger2   //tag to enable swagger
public class ApiResultadosApplication {

	@Value("${info.app.version}")
	private String APP_VERSION;
	@Value("${info.app.name}")
	private String APP_NAME;
	@Value("${info.app.description}")
	private String APP_DESCRIPTION;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiResultadosApplication.class, args);
	}

	/*  configuration bean for swagger properties */
	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.paths(PathSelectors.ant("/ApiResultados/**"))     //take  the methods of the path
				.apis(RequestHandlerSelectors.basePackage("mx.gda"))  //take only the models used in this package
				.build()
				.apiInfo(myApiDetails());
	}
	
	/* Overwrite apiInfo for set our information  */
	private ApiInfo myApiDetails() {
		return new ApiInfo(
				APP_NAME,   //title
				APP_DESCRIPTION,           //description
				APP_VERSION,				  //version
				null,//"API constructed for GDA, use internal only", //termsOfServiceUrl
				new springfox.documentation.service.Contact("Equipo de Desarrollo de TI",null, "marco.sosa@gda.mx"),   //name,url, email
				"Grupo Diagnóstico Aries",        //license
				"https://grupodiagnosticoaries.com/",  		//licenseUrl
				Collections.emptyList()						//vendorExtensions
				);
	}
}
