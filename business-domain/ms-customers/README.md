# Udemy - Microservicios con spring boot, cloud security Docker y APIs

## Customers microservice

# OpenAPI 3

[Spring docs Swagger OpenAPI](https://springdoc.org/#getting-started)
Usamos la siguiente dependencia

```
 <dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-ui</artifactId>
  <version>1.7.0</version>
</dependency>
```

Para acceder a la interfaz de usuario de Swagger

```
http://localhost:8080/swagger-ui/index.html
```

OpenApi description

```
http://localhost:8080/v3/api-docs
```

Si configuramos el application properties con una nueva url para acceder a swagger

```
springdoc.swagger-ui.path=/swagger.html
```

Entonces, ahora podríamos entrar a la interfaz de swagger de esta manera

```
http://localhost:8080/swagger.html
```

Deshabilitando la documentación api-docs

```
springdoc.api-docs.enabled=false
```

Deshabilitando swagger-ui

```
springdoc.swagger-ui.enabled=false
```

**IMPORTANTE** swagger-ui para que funcione depende del api-docs habilitado.