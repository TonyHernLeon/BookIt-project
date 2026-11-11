# 0005. Flyway gestiona el esquema y Hibernate solo valida

Fecha: 2026-11-09
Estado: Aceptada

## Contexto

Cada servicio tiene su propia base de datos ([ADR 0003](0003-base-de-datos-por-servicio.md)) y el esquema debe llegar al mismo estado en local, CI y producción. Hibernate puede generar o modificar tablas (`ddl-auto: update`), pero lo hace sin versionar y de forma difícil de predecir.

## Decisión

Flyway es el único dueño del esquema, con migraciones `V{versión}__{descripción}.sql` en `src/main/resources/db/migration`. Hibernate usa `ddl-auto: validate`: solo comprueba que entidades y tablas coinciden.

## Alternativas consideradas

- `ddl-auto: update`: descartada. Cambia el esquema sin control ni historial y puede divergir entre entornos.
- `ddl-auto: create` y `create-drop`: descartadas. Destruyen datos; solo sirven para pruebas desechables.
- **Scripts SQL manuales**: descartada. No hay registro de qué se aplicó ni orden garantizado.
- **Liquibase**: descartada. Es válida, pero Flyway con SQL plano es más simple y está integrada en Spring Boot.
- **Flyway con `validate`**: elegida. Esquema versionado como código y fallo temprano si algo no coincide.

## Consecuencias

- **Positivas**: esquema reproducible e historial en `flyway_schema_history`. La aplicación falla al arrancar si entidad y tabla divergen.
- **Negativas**: toda modificación requiere una migración nueva. Las ya aplicadas no se pueden editar: el checksum lo detecta y aborta el arranque, y el cambio va en una `V2`.
- **Obligaciones**: cada servicio replica esta convención. Se necesita `flyway-database-postgresql` además de `flyway-core`. En local, si la BD queda en un estado inconsistente, se recrea con `docker compose down -v`.