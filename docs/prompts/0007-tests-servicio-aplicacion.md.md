# 0007. Esqueleto de tests del servicio de aplicación del catálogo

Fecha: 2026-11-21
Ticket: BOOK-003 (S6)
Herramienta: GitHub Copilot

## Objetivo

Generar el repositorio en memoria y el esqueleto de `NegocioServiceTest` para demostrar que los casos de uso funcionan sin Spring ni base de datos. Los cuerpos de los tests los escribo yo.

## Prompt

Trabaja en `catalog-service`. Crea SOLO estos dos ficheros, ambos en `src/test/java/com/bookit/catalog/application/service/` y con `package com.bookit.catalog.application.service;`:

1. `NegocioRepositoryEnMemoria.java`: clase que implementa `com.bookit.catalog.application.port.out.NegocioRepository` con un `Map<UUID, Negocio>` (usa `LinkedHashMap`). `guardar` almacena por id y devuelve el mismo `Negocio`. `buscarPorId` devuelve `Optional<Negocio>`. `buscarPorCiudadYCategoria(Ciudad, Categoria)` devuelve `List<Negocio>` con los que coinciden en ciudad Y categoría, y una lista vacía si no hay ninguno.
2. `NegocioServiceTest.java`: una constante `Clock RELOJ_FIJO = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC)`, un repositorio en memoria y un `NegocioService` creados en un método `@BeforeEach`, y estos seis métodos de test con cuerpo vacío y anotados con `@Test` y `@Disabled("Pendiente de implementar")`:
   - registrar_conRelojFijo_guardaConFechaDelReloj
   - registrar_conNombreVacio_lanzaNombreNoValido
   - consultarPorId_conIdConocido_devuelveNegocioGuardado
   - consultarPorId_conIdDesconocido_lanzaNegocioNoEncontrado
   - buscar_conVariosNegocios_devuelveSoloLosDeEsaCiudadYCategoria
   - buscar_sinCoincidencias_devuelveListaVacia

Reglas: no toques ningún otro fichero. Prohibido importar Spring, JPA, Jakarta o Hibernate. Sin Lombok. Sin Mockito. Sin comentarios innecesarios. Nunca uses `Instant.now()`. `UUID`, `List` y `Optional` deben ser de `java.util`. Usa la misma librería de aserciones que los tests existentes (`CategoriaTest`). Termina cada fichero con un salto de línea.

## Resultado

[COMPLETAR: qué generó realmente, si respetó el paquete, los nombres y el `@Disabled`]

## Qué tuve que corregir

[COMPLETAR: correcciones reales tras revisar línea a línea; si no hubo ninguna, dilo]

## Lección

[COMPLETAR: en mis palabras, tras revisarlo]