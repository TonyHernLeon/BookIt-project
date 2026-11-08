# 0003. Decisión de usar una base de datos por servicio

## Fecha: 2026-11-08
## Estado: Aceptada ✅

## Contexto 📝
[¿Qué problema o situación te obliga a decidir? ¿Qué restricciones hay?]
Mientras estaba configurando la parte de docker compose, hablé con Claude y me indicó que lo ideal es tener una base de datos por servicio, ya que esto permite una mayor independencia y escalabilidad de cada servicio, evitando conflictos y facilitando la gestión de datos.

## Decisión 💭
[Qué has decidido, en una o dos frases, en presente ("Usamos...").]
Decido implementar una base de datos separada para cada servicio en el proyecto, siguiendo la recomendación de Claude. Esto permitirá que cada servicio tenga su propio esquema de datos, mejorando la modularidad y la capacidad de mantenimiento del sistema.

## Alternativas consideradas ⎇
[Qué otras opciones había y por qué las descartas.]

## Consecuencias 🔚
[Qué ganas, qué pierdes y qué obligaciones nuevas aparecen.]
Esta decisión mejorará la independencia de los servicios y facilitará el mantenimiento, pero también incrementará la complejidad en la gestión y el monitoreo de múltiples bases de datos.