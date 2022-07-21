package mx.gda.resultados;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;

@SpringBootApplication
@EnableScheduling
public class ApiResultadosApplication {

	@Value("${info.app.version}")
	private String APP_VERSION;
	@Value("${info.app.name}")
	private String APP_NAME;
	@Value("${info.app.description}")
	private String APP_DESCRIPTION;
	@Value("${info.app.organization}")
	private String ORGANIZATION_NAME;
	@Value("${info.app.organization.url}")
	private String ORGANIZATION_URL;
	@Value("${app.ssl.url}")
	private String APP_SSL_URL;
	
	public static void main(String[] args) {
		SpringApplication.run(ApiResultadosApplication.class, args);
	}

	/*  configuration bean for swagger properties */
	@Bean
	public OpenAPI springShopOpenAPI() {
		Contact gdaContact = new Contact();
		gdaContact.email("marco.sosa@gda.mx");
		gdaContact.setName("Arquitectura TI");
		if (APP_SSL_URL == null || APP_SSL_URL.isEmpty()) {
			return new OpenAPI()
					.info(new Info()
							.title(APP_NAME)
							.description(APP_DESCRIPTION)
							.version(APP_VERSION)
							.contact(gdaContact)
							.license(new License().name(ORGANIZATION_NAME)
									.url(ORGANIZATION_URL)
									)
							);
		}
		List<Server> s = new ArrayList<>();
		Server tmpServer = new Server();
		tmpServer.setUrl(APP_SSL_URL);
		s.add(tmpServer);
		return new OpenAPI()
				.info(new Info()
						.title(APP_NAME)
						.description(APP_DESCRIPTION)
						.version(APP_VERSION)
						.contact(gdaContact)
						.license(
								new License().name(ORGANIZATION_NAME).url(ORGANIZATION_URL)))
				.servers(s);
	}

}
