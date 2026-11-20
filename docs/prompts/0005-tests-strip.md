# 0005. Tests de strip() en el dominio

Fecha: 2026-11-xx
Ticket: BOOK-003 (S5/S6)
Herramienta: GitHub Copilot

## Objetivo

Comprobar con un test por clase que `strip()` funciona, sin Spring ni base de datos.

## Prompt

```text
Crea tres clases de test JUnit 5 en catalog-service/src/test/java/com/bookit/catalog/domain/: CategoriaTest, CiudadTest y NegocioTest, con package com.bookit.catalog.domain. No modifiques ni crees ningún otro fichero.

Reglas:
- Un único método @Test por clase.
- Solo JUnit 5 (org.junit.jupiter.api) y assertEquals. Sin Spring, sin @SpringBootTest, sin Mockito, sin Lombok.
- Nombre de método descriptivo en español, sin tildes, por ejemplo quitaEspaciosAlPrincipioYAlFinal.
- Salto de línea final en cada fichero.

Casos:
- CategoriaTest: new Categoria("  Peluquería  ").valor() debe ser "Peluquería".
- CiudadTest: new Ciudad("  Sevilla  ").valor() debe ser "Sevilla".
- NegocioTest: Negocio.crear("  Peluquería Ana  ", new Categoria("Belleza"), new Ciudad("Sevilla"), Instant.parse("2026-01-01T10:00:00Z")).nombre() debe ser "Peluquería Ana". No uses Instant.now().
```

## Resultado

Pendiente de completar cuando lo ejecutes: ¿generó tres ficheros con un solo `@Test`? ¿Pasaron en verde con `mvn test`?

## Qué tuve que corregir

Pendiente de completar tras revisar los ficheros generados.

## Lección para el próximo prompt

Un solo test por clase comprueba `strip()`, pero no que sigan vivas las reglas de nulo, vacío y longitud. Pedir esos casos en S6.