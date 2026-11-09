# 0004. Volumen con nombre y puerto por servicio

Fecha: 2026-11-08
Estado: Aceptada

## Contexto

El [ADR 0003](0003-base-de-datos-por-servicio.md) establece una base de datos por servicio. Al probar con `docker run`, Docker creó un volumen anónimo (nombre de hash largo) que persistía los datos, pero era difícil de identificar y quedaba huérfano al borrar el contenedor. Además, varias bases de datos no pueden publicar el mismo puerto en el host.

## Decisión

Cada base de datos usa un volumen con nombre declarado en `docker-compose.yml` (para el catálogo, `catalog-data`, montado en `/var/lib/postgresql/data`) y un puerto distinto en el host. El catálogo publica `5433:5432`: 5433 en el host y 5432 dentro del contenedor, que no cambia.

## Alternativas consideradas

- **Volumen anónimo**: descartada. Persiste los datos, pero no se identifica en `docker volume ls` y se acumula como basura al borrar contenedores.
- **Bind mount (carpeta del host)**: descartada. Depende de rutas y permisos de cada máquina y es menos portable entre equipos.
- **Todas las BDs en el puerto 5432 del host**: descartada. Es imposible: dos servicios no pueden publicar el mismo puerto.
- **Volumen con nombre y puerto propio por BD**: elegida. Es identificable, portable y sin conflictos.

## Consecuencias

- **Positivas**: los datos sobreviven a `docker compose down`, cada volumen es reconocible y no hay conflictos de puertos.
- **Negativas**: `docker compose down -v` elimina los datos de forma irreversible. Hay que llevar el control de qué puerto usa cada BD.
- **Obligaciones**: cada nueva BD define su propio volumen con el patrón `<servicio>-db-data` y un puerto libre en el host (5433 catálogo, 5434 reservas...). Los puertos asignados se documentan en el `README.md`.