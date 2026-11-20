# 0002. Dominio del catálogo: Categoria, Ciudad y Negocio

Fecha: 2026-11-xx
Ticket: BOOK-003 (S5, bloque A)
Herramienta: GitHub Copilot

## Objetivo

Generar el modelo de dominio respetando los ADR 0009 a 0014, sin tocar las excepciones existentes.

## Prompt

```text
Genera código Java 21 para el modelo de dominio del catálogo de BookIt. Solo código, sin explicaciones largas. NO cambies ninguna decisión del proyecto. NO toques ficheros existentes (las excepciones ya existen).

## Reglas obligatorias
- Paquete: com.bookit.catalog.domain (las excepciones ya existen en com.bookit.catalog.domain.exception y se importan de ahí).
- Sin Lombok. Sin imports de Spring, JPA, Jakarta ni javax.persistence. Solo JDK y las excepciones propias.
- Una clase por fichero, sin comentarios innecesarios, con salto de línea final.
- Los mensajes de excepción deben decir qué regla se rompió. Sin exponer datos sensibles.

## Excepciones existentes (no modificar, no recrear)
NombreNoValidoException, CategoriaNoValidaException, CiudadNoValidaException (String mensaje), en com.bookit.catalog.domain.exception.

## Clases a generar
1. record Categoria(String valor): valida en el constructor compacto; no nula, vacía ni solo espacios, máximo 50; lanza CategoriaNoValidaException.
2. record Ciudad(String valor): igual, máximo 100 y CiudadNoValidaException.
3. class Negocio (entidad): campos privados final UUID id, String nombre, Categoria categoria, Ciudad ciudad, Instant fechaCreacion. Constructor PRIVADO, sin setters. crear(nombre, categoria, ciudad, fechaCreacion) genera el id con UUID.randomUUID() y NO llama a Instant.now(). reconstruir(id, nombre, categoria, ciudad, fechaCreacion) recibe id y fecha existentes. Validación del nombre en un único método privado (no nulo, no vacío, máximo 150, NombreNoValidoException). Categoria o ciudad nulas lanzan su excepción; id o fechaCreacion nulos, Objects.requireNonNull con mensaje. Getters estilo record. equals y hashCode SOLO por id. toString con id y nombre.

## Salida
Tres ficheros en catalog-service/src/main/java/com/bookit/catalog/domain/. No generes tests, interfaces, servicios ni ficheros adicionales.
```

## Resultado

Generó los tres ficheros, compilaron y respetaron los ADR: constructor privado, `crear` sin `Instant.now()`, `equals` y `hashCode` por id, sin imports prohibidos.

## Qué tuve que corregir

- Dudas de comprensión sobre `Objects.requireNonNull` y `instanceof` con pattern matching: las resolví preguntando antes de aceptar el código.
- Quedó pendiente decidir la normalización con `strip()`.

## Lección para el próximo prompt

Pedir la normalización (`strip()`) desde el principio si la decisión ya está tomada, y no aceptar código cuyas líneas no sé explicar.