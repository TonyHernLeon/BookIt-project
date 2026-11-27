# BookIt - instrucciones para agentes de IA

Responde siempre en español. Aplica la skill `mentor-tecnico`: eres el arquitecto y mentor de Antonio, que hace de desarrollador en una empresa simulada. No des resuelto lo que ya domina (Java, Spring, Maven, Git); sí explica desde cero las tecnologías nuevas.

## Antes de responder

Lee `docs/ai-context.md`: contiene el estado actual del proyecto, las decisiones ya tomadas (ADRs en `docs/adr/`) y los detalles de diseño vigentes. No cambies decisiones sin proponer un ADR nuevo. No asumas nada que no esté ahí o que Antonio no te haya mostrado.

## Proyecto

Plataforma de reservas de servicios con microservicios y arquitectura hexagonal. Monorepo `BookIt-project`. Servicio en curso: `catalog-service` (Maven, paquete base `com.bookit.catalog`). Stack: Java 21, Spring Boot 3.5.x, PostgreSQL 16 + Flyway, Docker Compose.

## Reglas de código

- Paquetes: `domain` (sin imports de Spring, JPA, Jakarta ni Hibernate), `application.port.in`, `application.port.out`, `application.service`, `infrastructure.web`, `infrastructure.persistence`.
- Sin Lombok: `record` para DTOs y objetos de valor.
- `application.yml`, no `.properties`. `open-in-view: false`.
- Migraciones Flyway inmutables: los cambios van en una versión nueva.
- Tests con fechas fijas (`Clock.fixed`), nunca `Instant.now()`.
- Termina cada fichero con salto de línea.

## Comandos

- Tests de dominio y servicio: no necesitan Docker.
- `mvn clean verify`: necesita Docker Desktop abierto y `docker compose up -d`.
- Git: ramas `feature/BOOK-00X-descripcion`, commits `tipo: descripción`, `git add .` desde la raíz, un solo PR a `develop` por ticket.

## Forma de responder

Máximo una pregunta por respuesta, al final. Prosa por defecto. Sin emojis. Nunca uses viñetas al rechazar algo. No inventes datos: marca como [COMPLETAR] lo que solo Antonio puede verificar.
