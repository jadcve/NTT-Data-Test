
# bci-test API

## Descripción
Este proyecto es una API RESTful desarrollada con Spring Boot para la creación y manejo de usuarios. Utiliza una base de datos en memoria H2 para simplificar el desarrollo y las pruebas.

## Configuración de la Base de Datos
Existen configuradas las dependencias para las bases de datos H2 y Mysql con la configuración dentro del archivo application.properties. No requiere configuración adicional.

## PARA H2 
```properties
spring.application.name=bci-test
spring.h2.console.enabled=true
spring.datasource.url=jdbc:h2:mem:db_bci_test
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.path=/h2-console
spring.jpa.hibernate.ddl-auto=create
```
- **URL de acceso a la consola H2:** `/h2-console`
- **JDBC URL:** `jdbc:h2:mem:db_bci_test`
- **Usuario:** `sa`
- **Contraseña:** `password`


## PARA MYSQL
```properties
 spring.datasource.url=jdbc:mysql://localhost:3306/db_bci_test
 spring.datasource.username=root
 spring.datasource.password=password
 spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
 spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
 spring.jpa.show-sql=true
 spring.jpa.properties.hibernate.format_sql=true
```

## Endpoints
La aplicación expone los siguientes endpoints para interactuar con el sistema de usuarios los cuales se adjuntaran las colecciones:

- **Crear Usuario:** `POST /api/usuario/crear`
- **Actualizar Usuario:** `PUT /api/usuario/actualizar`
- **Listar Usuarios:** `GET /api/usuario/listar`
- **Login:** `POST /login`

## Validaciones
Se realizan validaciones personalizadas para los campos `name`, `email`, y `password`. Además, el campo `phones[]` deben cumplir con el formato especificado.

## Pruebas
Para probar la aplicación, puedes utilizar herramientas como Postman o cURL. Asegúrate de que la aplicación esté ejecutándose y envía las peticiones a los endpoints proporcionados.

## Construcción y Ejecución
Para construir y ejecutar la aplicación, utiliza Maven.

## Repositorio
El código fuente está disponible en GitHub: [bci-test GitHub Repository](https://github.com/jadcve/bci_test/)

## Carpeta adicionales 
Se agrego una carpeta de nombre **adicionales** donde se encuentra los script para la construcción de las tablas (por defecto se generan solas) y las colecciones de Postman para realizar las pruebas de la aplicación

## Contribuir
Para contribuir al proyecto, por favor envía un pull request o abre un issue para discutir los cambios que te gustaría implementar.
