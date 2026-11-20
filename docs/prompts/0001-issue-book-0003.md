# 0001. Redactar la issue de BOOK-003 y sus subtareas

Fecha: 2026-11-xx
Ticket: BOOK-003
Herramienta: GitHub Copilot

## Objetivo

Que Copilot redactara la issue y las ocho subtareas (S1 a S8) sin resolver el ticket ni cambiar decisiones.

## Prompt

```text
Redacta una issue de GitHub en español para el ticket BOOK-003. Tu trabajo es SOLO redactar la issue y sus subtareas. NO escribas código, NO resuelvas el ticket y NO cambies ninguna decisión del proyecto. No uses emojis. Respeta exactamente el contenido y el reparto indicados abajo.

## Contexto del proyecto
BookIt es una plataforma de reservas de servicios (clínicas, peluquerías, pistas deportivas), construida como microservicios con arquitectura hexagonal en un monorepo. El alumno (Antonio, desarrollador Java con experiencia, nuevo en Docker, Flyway, Testcontainers, Kafka, Keycloak y similares) simula entrar en una empresa nueva. Tú redactas como el arquitecto del equipo.

Decisiones ya tomadas (no modificar): ADR 0003 una BD por servicio; ADR 0005 Flyway dueño del esquema y Hibernate ddl-auto: validate; ADR 0006 UUID como identificador, generado por la aplicación y no por la BD. Paquete base com.bookit.catalog. Estructura: domain (sin imports de Spring, JPA ni Jakarta), application.port.in, application.port.out, infrastructure.web, infrastructure.persistence. Sin Lombok: usar record donde proceda. La tabla negocio (V1) tiene: id UUID, nombre, categoria, ciudad, fecha_creacion TIMESTAMPTZ, y un índice (ciudad, categoria). Convención de commits tipo: descripción. Ramas feature/BOOK-00X-descripcion. Un solo PR a develop al terminar.

## Título de la issue
BOOK-003: Dominio y casos de uso del catálogo

## Contexto de negocio
El catálogo guarda los negocios que ofrecen servicios. Antes de exponer nada por HTTP o persistir en BD, hay que modelar qué es un negocio y qué se puede hacer con él. En hexagonal, el dominio y los casos de uso se construyen primero, sin depender de Spring ni de JPA, y los adaptadores se enchufan después.

## Alcance
Modelo de dominio y casos de uso del catálogo, con sus puertos, sin infraestructura. Fuera de alcance: controladores, entidades JPA y repositorios reales.

## Requisitos
1. Dominio (domain): concepto Negocio con identificador, nombre, categoría, ciudad y fecha de creación. Reglas propias, como que el nombre no puede estar vacío. El alumno decide si Categoria es un tipo propio (enum, record) y qué otros objetos de valor crear.
2. Puertos de entrada (application.port.in): casos de uso como interfaces. Mínimo: registrar un negocio, consultar uno por id y buscar por ciudad y categoría.
3. Puerto de salida (application.port.out): interfaz para guardar y recuperar negocios, sin saber cómo.
4. Servicio de aplicación: implementa los casos de uso usando el puerto de salida.
5. Reglas de dominio y errores: qué ocurre si el negocio no existe o los datos no son válidos, con excepciones propias del dominio (no de Spring).
6. ADR: registrar las decisiones tomadas.

## Criterios de aceptación (checklist)
- domain no importa nada de Spring, JPA ni Jakarta.
- Sin Lombok: record donde proceda.
- El id (UUID) lo genera la aplicación, no la BD.
- Los casos de uso se pueden ejecutar y probar sin arrancar Spring.
- Rama feature/BOOK-003-catalog-domain, commits tipo: descripción, un solo PR a develop al terminar.
- mvn clean verify termina en BUILD SUCCESS.

## Subtareas (créalas como checklist dentro de la issue)
Para cada una, indica objetivo, qué investigar, primer paso y criterio de hecho. NO des código ni la solución:
S1 investigar arquitectura hexagonal; S2 modelo de dominio; S3 puertos de entrada y salida; S4 errores del dominio; S5 servicio de aplicación; S6 comprobar sin Spring; S7 ADR; S8 cierre.

## Reparto: qué hacer solo y qué con IA
(tabla de reparto S1 a S8, solo/IA, tal como se definió en el ticket)

## Formato de salida
Markdown listo para pegar como issue de GitHub, con checklists y la tabla de reparto. Sin emojis.
```

## Resultado

Copilot respetó requisitos, criterios y reparto, y creó las ocho sub-issues (#10 a #17) sin código ni cambios de decisiones.

## Qué tuve que corregir

- Los campos "Qué investigar" de S2, S4, S5, S7 y S8 eran genéricos y casi repetían el objetivo.
- S1 y S3 no tenían términos de búsqueda concretos.
- Faltaba el orden recomendado entre subtareas.

## Lección para el próximo prompt

Pedir términos de búsqueda concretos solo para las subtareas de investigación, y añadir explícitamente "orden recomendado S1 a S8". Revisar que la issue no quede asignada a Copilot como agente.