# 0003. Decisión de nombre y puerto por servicio

## Fecha: 2026-11-08
## Estado: Aceptada ✅

## Contexto 📝
[¿Qué problema o situación te obliga a decidir? ¿Qué restricciones hay?]
Al crear la base de datos para configurar el `docker-compose.yml` me comenta Claude que lo ideal es que cada servicio tenga su propio nombre y puerto, ya que esto permite una mayor independencia y escalabilidad de cada servicio, evitando conflictos y facilitando la gestión de datos.

## Decisión 💭
[Qué has decidido, en una o dos frases, en presente ("Usamos...").]
Decido hacer caso a la recomendación de Claude y asignar un nombre y puerto único para cada servicio en el proyecto. Esto permitirá que cada servicio tenga su propia configuración de red, mejorando la modularidad y la capacidad de mantenimiento del sistema.


## Alternativas consideradas ⎇
[Qué otras opciones había y por qué las descartas.]

## Consecuencias 🔚
[Qué ganas, qué pierdes y qué obligaciones nuevas aparecen.]
Con esta decisión mejorará la independencia de los servicios y facilitará el mantenimiento, pero también incrementará la complejidad en la gestión y el monitoreo de múltiples servicios con sus propios nombres y puertos.

La configuración de puertos sería:
   - `5433:5342`: para catalog 
   - ....