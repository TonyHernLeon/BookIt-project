# 0006. UUID como identificador de las entidades

Fecha: 2026-11-09
Estado: Aceptada

## Contexto

Los servicios tienen bases de datos separadas y se referencian entre sí (reservas apunta a un negocio del catálogo). Un identificador autoincremental depende de la secuencia de una BD concreta.

## Decisión

Las entidades usan `UUID` como clave primaria, generado por la aplicación y no por la base de datos.

## Alternativas consideradas

- **`BIGSERIAL`/autoincremental**: descartada. Acopla el id a la BD, puede colisionar entre servicios y revela cuántos registros existen.
- **UUID generado por la aplicación**: elegida. Se conoce el id antes de insertar y es único globalmente.

## Consecuencias

- **Positivas**: no hay colisiones entre servicios, se pueden referenciar entidades sin acoplarse a una secuencia y no se exponen volúmenes de datos.
- **Negativas**: ocupan 16 bytes frente a 8 y los UUID aleatorios fragmentan algo más el índice de la clave primaria.
- **Obligaciones**: el dominio genera el id; la columna es `UUID` en PostgreSQL.

