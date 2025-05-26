# NTTData-Test API

## 📋 Descripción
`NTTData-Test` es una API RESTful desarrollada con **Spring Boot** para la creación, autenticación y gestión de usuarios. Incluye validaciones personalizadas, generación de JWT y soporte para pruebas en **H2** o despliegue en **MySQL**.

---

## ⚙️ Configuración de la Base de Datos

### 🧪 H2 (por defecto)
La aplicación utiliza una base de datos en memoria H2 para facilitar el desarrollo y las pruebas:

```properties
spring.datasource.url=jdbc:h2:mem:db_bci_test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
---

### 🛠️ MySQL (opcional)
Para entornos persistentes, puedes configurar conexión a MySQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_bci_test
spring.datasource.username=root
spring.datasource.password=password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

---

## 🔐 Seguridad
- Autenticación vía `POST /api/login` con correo y contraseña.
- Respuesta incluye un token JWT del tipo `Bearer`.
- Los endpoints protegidos deben incluir el header:

```
Authorization: Bearer <token>
```

---

## 📌 Endpoints Disponibles

| Método | Endpoint                  | Descripción               | Autenticación |
|--------|---------------------------|---------------------------|----------------|
| POST   | `/api/usuario/crear`      | Crear nuevo usuario       | ❌ No requerida |
| POST   | `/api/login`              | Login y obtención de token| ❌ No requerida |
| PUT    | `/api/usuario/actualizar` | Actualizar un usuario     | ✅ Requiere JWT |
| GET    | `/api/usuario/listar`     | Listar usuarios registrados| ✅ Requiere JWT |

---

## 🧪 Ejemplos de Peticiones

### Crear Usuario – `POST /api/usuario/crear`
```json
{
  "name": "Usuario prueba",
  "email": "user@dominio.cl",
  "password": "Abc*1234!",
  "phones": [
    {
      "number": "1234567",
      "citycode": "+235",
      "contrycode": "+56"
    }
  ]
}
```

### Login – `POST /api/login`
```json
{
  "email": "user@dominio.cl",
  "password": "Abc*1234!"
}
```

### Actualizar Usuario – `PUT /api/usuario/actualizar`
```json
{
  "name": "Nombre Usuario Modificado",
  "email": "user@dominio.cl",
  "password": "Abc*1234!",
  "phones": [
    {
      "number": "1234567",
      "citycode": "+235",
      "contrycode": "+56"
    }
  ]
}
```

### Listar Usuarios – `GET /api/usuario/listar`

> 🔐 **Requiere token `Bearer`** obtenido desde el endpoint de login.

---

## ✅ Validaciones
Se realizan validaciones para los campos:

- `name`: obligatorio.
- `email`: obligatorio, único y con formato válido.
- `password`: debe cumplir con reglas de seguridad (letras, números, símbolos).
- `phones[]`: cada objeto debe contener `number`, `citycode`, `contrycode`.

---

## 🚀 Construcción y Ejecución

```bash

./mvnw clean install
./mvnw spring-boot:run
```

---

## 📁 Carpeta `adicionales`
Incluye:

- Script SQL para crear tablas (opcional, ya se generan automáticamente).
- Colecciones Postman para pruebas manuales de los endpoints.

---


## 🔗 Repositorio

Puedes encontrar el código fuente aquí:  
👉 [https://github.com/jadcve/NTT-Data-Test](https://github.com/jadcve/NTT-Data-Test)

## 📄 Documentación

La API cuenta con documentación interactiva generada automáticamente mediante **Swagger UI**, lo que facilita la exploración de los endpoints disponibles, sus parámetros, respuestas y pruebas en tiempo real desde el navegador.

Una vez que la aplicación esté en ejecución, puedes acceder a la documentación completa en:

👉 [http://localhost:8080/swagger-ui/index.html#/](http://localhost:8080/swagger-ui/index.html#/)

Esta interfaz permite:

- Visualizar todos los endpoints REST disponibles.
- Ver ejemplos de request/response.
- Ejecutar llamadas a la API directamente desde el navegador.
- Validar el correcto funcionamiento de los servicios sin herramientas externas.