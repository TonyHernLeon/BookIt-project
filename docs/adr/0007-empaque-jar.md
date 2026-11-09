# 0007. Empaque JAR para despliegue de servicios

Fecha: 2026-11-09
Estado: Aceptada

## Contexto

`catalog-service` se construye como un microservicio independiente, con su propio dominio y persistencia. En el ticket BOOK-002 se establece que el despliegue de los servicios se hará en contenedores Docker. En estos contendeores se usa un JAR ejecutables empaquetado con Maven.

## Decisión

`catalog-service` se empaqueta como un JAR ejecutable para su despliegue en contenedores Docker.

## Alternativas consideradas

- **Empaquetar como JAR ejecutable**: elegida. Es la forma más sencilla de empaquetar y desplegar un microservicio independiente, con su propio dominio y persistencia. Se puede ejecutar con `java -jar catalog-service.jar` y se puede construir con Maven.
- **Empaquetar como WAR**: descartada. Es más compleja de empaquetar y desplegar, y requiere un servidor de aplicaciones como Tomcat o Jetty. No es necesario para un microservicio independiente, y añade complejidad innecesaria.
- **Empaquetar como imagen Docker**: descartada. Es más compleja de empaquetar y desplegar, y requiere un Dockerfile y un registro de imágenes. No es necesario para un microservicio independiente, y añade complejidad innecesaria. Además, el despliegue en contenedores Docker se puede hacer con un JAR ejecutable, y es más sencillo de construir y desplegar.

## Consecuencias

- **Positivas**: 
  - Sencillo de empaquetar y desplegar.
  - Independiente del servidor de aplicaciones.
  - Independiente del registro de imágenes Docker.
- **Negativas**: 
  - No se puede ejecutar en un servidor de aplicaciones como Tomcat o Jetty.
  - No se puede desplegar en un registro de imágenes Docker.
- **Obligaciones**: 
  - Se debe construir el JAR ejecutable con Maven antes de desplegarlo en contenedores Docker.
  - Se debe ejecutar el JAR ejecutable con `java -jar catalog-service.jar` en contenedores Docker.

