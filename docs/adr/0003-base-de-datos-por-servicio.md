# 0003. Una base de datos por servicio

Fecha: 2026-11-08
Estado: Aceptada

## Contexto
BookIt se construye como un conjunto de microservicios (catálogo, reservas, usuarios y notificaciones). El ticket BOOK-002 establece que cada servicio debe tener su propia base de datos, levantada en local con `docker-compose.yml`.

Hay que decidir si los servicios comparten una única base de datos o si cada uno es dueño de la suya.

## Decisión
Cada microservicio tiene su propia base de datos y su propio usuario, y ningún servicio accede a la base de datos de otro. Seguimos el patrón de nombres `<servicio>` para la BD y el usuario, `<servicio>-db` para el servicio de Compose y `<servicio>-db-data` para el volumen. El primer caso es `catalog-db`, con BD y usuario `catalog`.

## Alternativas consideradas
- **Una única base de datos compartida por todos los servicios**: descartada. Es más sencilla de montar, pero acopla los servicios por el esquema: un cambio de tabla en uno puede romper a otro y ya no se pueden desplegar ni evolucionar de forma independiente.
- **Una única instancia de PostgreSQL con un esquema (`schema`) por servicio**: descartada por ahora. Aísla los datos lógicamente, pero comparte servidor, recursos y punto de fallo, y es más fácil que un servicio acabe consultando el esquema de otro.
- **Una instancia de PostgreSQL por servicio**: elegida. Máximo aislamiento y coherente con la independencia de despliegue de los microservicios.

## Consecuencias
- **Positivas**: los servicios son independientes. Cada uno puede cambiar su esquema, desplegarse o escalar sin afectar a los demás, y un fallo en una base de datos no tumba el resto.
- **Negativas**: más contenedores y más consumo de recursos en local. No se pueden hacer `JOIN` entre datos de servicios distintos, así que la información compartida se resuelve por API o por eventos, y la consistencia entre servicios pasa a ser eventual.
- **Obligaciones**: cada BD necesita un puerto distinto en el host (5433 para el catálogo, 5434 para reservas, etc.) y sus propias migraciones de Flyway. Las credenciales de cada servicio deben ser independientes.