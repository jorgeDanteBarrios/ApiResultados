## ApiResultados 🚀

API generada para la consulta de los resultados de las diferentes marcadas que conforman a GDA, este contine los clientes de los diferentes Labcore.


### Pre-requisitos 📋

Para crear y ejecutar la aplicación, necesita:
* JDK 1.8
* Maven 3

### Ejecución en Local 🔧

Hay varias formas de ejecutar una aplicación Spring Boot en su máquina local. Una forma es ejecutar el método principal en la clase **mx.gda.resultados.[Controller-class]**  desde su IDE.

Alternativamente, puede usar el complemento [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) así:

```shell
mvn spring-boot:run
```

Alternativamente, puede usar el complemento [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) así:

```shell
mvn spring-boot:run
```

Antes de ejecutar el proyecto se debe de considerar las siguientes configuraciones en el **properties**:
* Comentar las siguiente linea

```shell
 logging.pattern.console=
```
* Descomentar la siguiente linea 

```shell
 spring.boot.admin.client.enabled=false
```
* Validar que las credenciales de base de datos esten apuntando a un ambiente local/ desarrollo (**spring.datasource**)
* Validar el número máximo de conexiones (**spring.datasource.hikari.maximumPoolSize**)


### Licencia
© GDA 2021