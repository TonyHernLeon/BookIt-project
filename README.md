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
```

## Puertos en local

| Servicio | Puerto host | Puerto contenedor |
|---|---|---|
| catalog-db | 5433 | 5432 |

## Convenciones

- Columnas de fecha de creación: `fecha_creacion` (tipo `TIMESTAMPTZ`) en todas las tablas.

## Decisiones de arquitectura

- [0001. Monorepo para todos los servicios](docs/adr/0001-monorepo.md)
- [0002. PostgreSQL 16 como versión de base de datos](docs/adr/0002-postgresql-16.md)
- [0003. Una base de datos por servicio](docs/adr/0003-base-de-datos-por-servicio.md)
- [0004. Volumen con nombre y puerto por servicio](docs/adr/0004-volumen-con-nombre-y-puerto-por-servicio.md)
- [0005. Flyway gestiona el esquema y Hibernate solo valida](docs/adr/0005-flyway-y-ddl-auto-validate.md)
- [0006. UUID como identificador de las entidades](docs/adr/0006-uuid-como-identificador.md)
- [0007. Empaque JAR para despliegue de servicios](docs/adr/0007-empaque-jar.md)
- [0008. Rama develop como integración y main estable](docs/adr/0008-develop-como-rama-de-integracion.md)
- [0009. Negocio como entidad de dominio que nace válida](docs/adr/0009-negocio-como-entidad-de-dominio.md)
- [0010. Categoria y Ciudad como records con invariantes propias](docs/adr/0010-categoria-ciudad-como-records.md)
- [0011. La fecha de creación se recibe desde fuera del dominio](docs/adr/0011-fecha-de-creacion-fuera-del-dominio.md)
- [0012. El nombre del negocio es un String validado en Negocio](docs/adr/0012-nombre-de-negocio-como-string.md)
- [0013. Puertos del catálogo: una interfaz por caso de uso](docs/adr/0013-puertos-del-catalogo.md)
- [0014. Excepciones de dominio: jerarquía unchecked con una excepción por campo](docs/adr/0014-excepciones-de-dominio.md)


# Biblioteca de prompts

Prompts usados con GitHub Copilot en BookIt, con su resultado real y lo aprendido. No es una decisión de arquitectura, así que no lleva ADR.

## Reglas

1. Solo se guardan prompts que se hayan usado, con su resultado real.
2. Cada fichero enlaza su ticket y subtarea.
3. Nunca se guardan tokens ni credenciales.
4. Se escribe primero la parte de "Qué tuve que corregir" y "Lección": es lo que mejora los siguientes prompts.

## Buenas prácticas acumuladas

- Indicar paquete, ruta y ficheros permitidos, y decir "no toques nada más".
- Prohibir imports concretos (Spring, JPA, Jakarta) y Lombok.
- Dar nombres exactos de constantes, clases y métodos, no solo describirlos.
- Pedir fechas fijas (nunca `Instant.now()`) en tests.
- Pedir un único sitio para cada lógica, para que no se duplique.
- Pedir a la IA que redacte, y revisar yo cada línea de código generado.

## Índice

- [0000. Plantilla](docs/prompts/0000-plantilla.md)
- [0001. Issue de BOOK-003](docs/prompts/0001-issue-book-003.md)
- [0002. Dominio, bloque A](docs/prompts/0002-dominio-bloque-a.md)
- [0003. strip() en Categoria y Ciudad](docs/prompts/0003-strip-categoria-ciudad.md)
- [0004. strip() en Negocio](docs/prompts/0004-strip-negocio.md)
- [0005. Tests de strip()](docs/prompts/0005-tests-strip.md)

