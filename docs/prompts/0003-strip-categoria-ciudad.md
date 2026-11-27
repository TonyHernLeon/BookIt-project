# 0003. Normalizar Categoria y Ciudad con strip()

Fecha: 2026-11-xx
Ticket: BOOK-003 (S5, bloque A)
Herramienta: GitHub Copilot

## Objetivo

Normalizar el valor de `Categoria` y `Ciudad` con `strip()` antes de validar, y exponer el mensaje como constante pública.

## Prompt

```text
En catalog-service/src/main/java/com/bookit/catalog/domain/, modifica los records Categoria y Ciudad. No cambies nada más ni toques otros ficheros.

En el constructor compacto de cada record, en este orden:
1. Si valor es null, lanza la excepción del campo (CategoriaNoValidaException en Categoria, CiudadNoValidaException en Ciudad).
2. Reasigna valor = valor.strip().
3. Si valor.isEmpty() o valor.length() supera el máximo (50 en Categoria, 100 en Ciudad), lanza la misma excepción.

Reglas:
- Usa isEmpty(), no isBlank(), porque el valor ya está normalizado.
- Define una constante pública de mensaje en cada record (public static final String MENSAJE_INVALIDA) y úsala en todos los throws del record. El texto actual es "La categoría no puede ser nula, vacía ni superar 50 caracteres" y "La ciudad no puede ser nula, vacía ni superar 100 caracteres".
- Sin Lombok, sin imports de Spring, JPA ni Jakarta, con salto de línea final.
```

## Resultado

Aplicó el orden correcto (null, `strip()`, `isEmpty()` y longitud) y creó la constante. Compiló.

## Qué tuve que corregir

La constante salió con el nombre `MENSAJE_NULO` en lugar de `MENSAJE_INVALIDA`, y engaña porque el texto cubre nulo, vacío y demasiado largo. Pendiente de renombrar con *Refactor → Rename*. También sobraba un comentario `// - Constantes` solo en `Categoria`.

Renombré después la constante `MENSAJE_NULO` a `MENSAJE_INVALIDA` con Refactor → Rename de IntelliJ (fecha: 2026-11-27), porque el texto cubre nulo, vacío y longitud excesiva. Comprobé con `grep -rn "MENSAJE_" catalog-service/src` que `Categoria`, `Ciudad` y `Negocio` usan ya el nombre nuevo y que `docs/ai-context.md` seguía con el antiguo, así que lo corregí también.

## Lección para el próximo prompt

Dar el nombre exacto de la constante y repetirlo ("la constante se llama exactamente MENSAJE_INVALIDA"). Pedir también "sin comentarios".