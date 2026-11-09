# 0002. PostgreSQL 16 como versión de base de datos

Fecha: 2026-11-08
Estado: Aceptada

## Contexto
El ticket BOOK-002 indicaba PostgreSQL 16. Al lanzar la imagen sin especificar etiqueta, Docker descargó `latest`, que en ese momento era la versión 18.6.

La 18 se diferencia de la 16 en el directorio de datos del contenedor (`/var/lib/postgresql/18/docker` frente a `/var/lib/postgresql/data`) y en algunos valores por defecto, como los *checksums* de páginas, que vienen activados en la 18 y desactivados en la 16. Por tanto, la configuración del volumen depende de la versión usada.

## Decisión
Usamos `postgres:16` en el `docker-compose.yml` y montamos el volumen en `/var/lib/postgresql/data`.

## Alternativas consideradas
- **`postgres:latest` (o sin etiqueta)**: descartada. La versión cambia sin avisar, el entorno deja de ser reproducible y no coincide con lo que pide el ticket.
- **`postgres:18` fijada explícitamente**: descartada por ahora. Es la versión más reciente, pero no es la que pide el ticket y obliga a cambiar la ruta del volumen. Queda como candidata para una migración futura.
- **`postgres:16.15` (parche fijo)**: descartada. Da máxima reproducibilidad, pero exige actualizar la etiqueta a mano para recibir parches de seguridad.
- **`postgres:16` (etiqueta flotante)**: elegida. Fija la versión mayor, que es lo que importa para la compatibilidad de datos, y recibe parches menores al volver a descargar la imagen.

## Consecuencias
- **Positivas**: el entorno coincide con el ticket y es reproducible para todo el equipo. La ruta del volumen está documentada y verificada.
- **Negativas**: la 16 llegará a su fin de soporte antes que la 18, así que habrá que planificar una migración de versión mayor. Esa migración requerirá volcar y restaurar los datos o usar `pg_upgrade`, porque los datos de una versión mayor no son compatibles con otra.
- **Obligaciones**: especificar siempre la etiqueta de versión en las imágenes del `docker-compose.yml` y no usar `latest`.