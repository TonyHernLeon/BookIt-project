# 0015. Repositorio en memoria solo en src/test

Fecha: 2026-11-27

Estado: Aceptada

## Contexto

`NegocioService` forma parte de la capa de aplicación y depende del puerto de salida `NegocioRepository`. Para ejecutar sus casos de uso necesita una implementación de ese contrato: el adaptador JPA en producción o un doble de prueba en los tests.

Los tests del servicio comprueban la lógica de aplicación sin arrancar Spring, PostgreSQL ni Docker. `NegocioRepositoryEnMemoria` implementa el puerto guardando los datos en memoria. Se encuentra en `src/test`, que Maven no incluye en el JAR de producción.

## Decisión

`NegocioRepositoryEnMemoria` se mantiene en `src/test/java/com/bookit/catalog/application/service/`, con visibilidad package-private, y se utiliza como doble de prueba para ejecutar los casos de uso sin infraestructura real. Su ubicación evita que Maven lo empaquete en el JAR de producción.

## Alternativas consideradas

- **Repositorio en memoria en `src/main`**: se descarta porque quedaría disponible en el JAR de producción y podría inyectarse accidentalmente en lugar del adaptador persistente. Si eso ocurriera, la aplicación aparentemente funcionaría, pero los datos se perderían al reiniciar.
- **Mock con Mockito**: se descarta para estos tests porque necesitamos comprobar la interacción con una colección con estado: guardar negocios y recuperarlos o filtrarlos. Configurar cada respuesta y verificación con un mock describiría principalmente las llamadas esperadas, mientras que el repositorio en memoria ejercita las operaciones del puerto sobre un estado compartido.
- **PostgreSQL real con Testcontainers en todos los tests**: se descarta para los tests de aplicación porque obliga a arrancar PostgreSQL y Docker, aumentando el tiempo de ejecución y las posibles fuentes de fallo sin aportar al comportamiento que se quiere verificar. Testcontainers se reserva para probar el adaptador JPA por separado en BOOK-005.

## Consecuencias

### Positivas

Los tests de `NegocioService` pueden guardar y buscar negocios mediante el repositorio en memoria sin arrancar Spring ni depender de PostgreSQL o Docker. Esto permite probar la lógica de aplicación de forma más rápida y aislada. Además, al estar el repositorio en `src/test`, no se incluye en el JAR de producción.

### Negativas

Estos tests solo comprueban que el servicio funciona con el doble de prueba. No detectan fallos en el adaptador JPA, las consultas ni el comportamiento de PostgreSQL.

### Obligaciones

El adaptador JPA debe probarse por separado con Testcontainers en BOOK-005. Además, `NegocioRepositoryEnMemoria` debe mantenerse actualizado cuando cambien los métodos de `NegocioRepository`.
