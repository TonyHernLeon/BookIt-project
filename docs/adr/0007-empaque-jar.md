# 0007. JAR ejecutable como artefacto de los servicios

Fecha: 2026-11-09
Estado: Aceptada

## Contexto

Cada servicio es un microservicio independiente construido con Spring Boot y Maven. Más adelante se desplegarán en contenedores Docker, por lo que hay que decidir qué artefacto produce el build.

## Decisión

Cada servicio se empaqueta como JAR ejecutable (`<packaging>jar</packaging>`) con el servidor web embebido, y se ejecuta con `java -jar`. La imagen Docker, cuando llegue, envolverá ese JAR.

## Alternativas consideradas

- **WAR en servidor de aplicaciones externo**: descartada. Obliga a instalar y mantener un servidor aparte y va contra la independencia de despliegue de los microservicios.
- **Imagen de contenedor directa (Buildpacks o Dockerfile)**: no es alternativa, sino el paso siguiente: parte del JAR. La forma de construirla se decidirá en la fase de DevOps.

## Consecuencias

- **Positivas**: un único fichero ejecutable por servicio, sin servidor externo, fácil de contenerizar y de ejecutar en local y en CI.
- **Negativas**: cada JAR incluye su propio servidor embebido, así que pesa más y cada servicio lo actualiza por separado.
- **Obligaciones**: construir con `mvn package` antes de crear la imagen y mantener `<packaging>jar</packaging>` en el `pom.xml`.
