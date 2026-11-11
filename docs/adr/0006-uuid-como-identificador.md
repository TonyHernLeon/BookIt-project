# 0006. UUID como identificador de las entidades

Fecha: 2026-11-09
Estado: Aceptada

## Contexto

Los servicios tienen bases de datos separadas y se referencian entre sí (reservas apunta a un negocio del catálogo). Un identificador autoincremental depende de la secuencia de una BD concreta.

## Decisión

Las entidades usan `UUID` como clave primaria, generado por la aplicación y no por la base de datos.

## Alternativas consideradas

- **`BIGSERIAL` (autoincremental)**: descartada. Acopla el id a la BD, puede colisionar entre servicios y revela cuántos registros existen.
- **UUID generado por la base de datos**: descartada. Mantiene la unicidad, pero el id no se conoce hasta insertar y el dominio depende de la BD para crear una entidad.
- **UUID v7 (ordenable por tiempo)**: aplazada. Reduce la fragmentación del índice, pero requiere una librería o una versión reciente de Java. Se reevaluará si el rendimiento de inserción lo pide.
- **UUID v4 generado por la aplicación**: elegida. Se conoce el id antes de insertar y es único globalmente.

## Consecuencias

- **Positivas**: no hay colisiones entre servicios, se pueden referenciar entidades sin acoplarse a una secuencia y no se exponen volúmenes de datos.
- **Negativas**: ocupan 16 bytes frente a 8, son más incómodos de leer al depurar y los UUID v4 aleatorios fragmentan el índice de la clave primaria.
- **Obligaciones**: el dominio genera el id y la columna es `UUID` en PostgreSQL. La migración `V1` ya lo cumple (`id UUID PRIMARY KEY`, sin `DEFAULT`).
