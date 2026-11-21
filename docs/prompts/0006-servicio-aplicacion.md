# 0006. Esqueleto del servicio de aplicación del catálogo

Fecha: 2026-11-xx
Ticket: BOOK-003 (S5, bloque C)
Herramienta: GitHub Copilot

## Objetivo

Generar solo la estructura de `NegocioService` (clase, campos, constructor y firmas) para escribir yo la lógica de los tres casos de uso.

## Prompt

```text
Crea UNA clase Java 21 en catalog-service/src/main/java/com/bookit/catalog/application/service/NegocioService.java, con package com.bookit.catalog.application.service. No modifiques ni crees ningún otro fichero.

Reglas obligatorias:
- Java puro: sin Spring (ni @Service ni @Component), sin Lombok, sin JPA, sin Jakarta, sin Hibernate. Solo JDK y clases del propio proyecto.
- Sin comentarios innecesarios y con salto de línea final.

Estructura:
- La clase se llama NegocioService e implementa RegistrarNegocioUseCase, ConsultarNegocioUseCase y BuscarNegociosUseCase (com.bookit.catalog.application.port.in).
- Dos campos private final: NegocioRepository repositorio (com.bookit.catalog.application.port.out) y java.time.Clock clock.
- Un único constructor público que recibe NegocioRepository y Clock, y los asigna.
- Implementa los tres métodos con @Override y estas firmas exactas, importando los tipos de com.bookit.catalog.domain:
  - Negocio registrar(RegistrarNegocioCommand comando)
  - Negocio consultarPorId(UUID id)
  - List<Negocio> buscar(Ciudad ciudad, Categoria categoria)
- IMPORTANTE: NO desarrolles la lógica. El cuerpo de cada método debe ser únicamente: throw new UnsupportedOperationException("Pendiente de implementar");
- No generes tests ni ningún otro fichero.
```

## Resultado

Completar tras ejecutarlo: ¿generó un solo fichero con el paquete correcto, sin anotaciones de Spring y con los tres cuerpos en `UnsupportedOperationException`?

## Qué tuve que corregir

Completar tras revisar el fichero generado.

## Lección para el próximo prompt

Delegar solo la estructura y escribir yo la lógica permite aprender lo importante sin perder tiempo en código repetitivo. Pedir siempre ruta y paquete exactos, y prohibir anotaciones de Spring en `application`.