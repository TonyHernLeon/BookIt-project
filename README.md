# BookIt

Plataforma de reservas de servicios (clínicas, peluquerías, pistas deportivas), construida como microservicios con arquitectura hexagonal.

## Requisitos previos

| Herramienta | Versión instalada | Comprobación |
|---|---|---|
| Java (JDK) | `java 21.0.10 2026-01-20 LTS` | `java 21.0.10 2026-01-20 LTS` |
| Maven | `Apache Maven 3.9.16 (2bdd9fddda4b155ebf8000e807eb73fd829a51d5)` | `Apache Maven 3.9.16git` |
| Git | `git version 2.56.0` | `git version 2.56.0` |
| Docker | `Docker version 29.9.2, build a82e528` | `Docker version 29.9.2, build a82e528` |
| Docker Compose | `Docker Compose version v5.6.1` | `Docker Compose version v5.6.1` |
| IDE | IntelliJ IDEA `2026.3` | |
| Cliente de BD | DataGrip `2025.3.5` | |
| Cliente HTTP | Postman `12.1.4` | |

## Arranque en local

Desde la raíz del repositorio:

```bash
docker compose up -d
```cd .. 

## Puertos en local

| Servicio | Puerto host | Puerto contenedor |
|---|---|---|
| catalog-db | 5433 | 5432 |

## Decisiones de arquitectura

- [0001. Monorepo para todos los servicios](docs/adr/0001-monorepo.md)
- [0002. PostgreSQL 16 como versión de base de datos](docs/adr/0002-postgresql-16.md)
- [0003. Una base de datos por servicio](docs/adr/0003-base-de-datos-por-servicio.md)
- [0004. Volumen con nombre y puerto por servicio](docs/adr/0004-volumen-con-nombre-y-puerto-por-servicio.md)
- [0005. Flyway gestiona el esquema y Hibernate solo valida](docs/adr/0005-flyway-y-ddl-auto-validate.md)
- [0006. UUID como identificador de las entidades](docs/adr/0006-uuid-como-identificador.md)
- [0007. Empaque JAR para despliegue de servicios](docs/adr/0007-empaque-jar.md)