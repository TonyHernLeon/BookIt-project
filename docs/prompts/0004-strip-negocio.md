# 0004. Normalizar el nombre de Negocio con strip()

Fecha: 2026-11-xx
Ticket: BOOK-003 (S5, bloque A)
Herramienta: GitHub Copilot

## Objetivo

Normalizar el nombre en `Negocio` sin duplicar la lógica entre `crear` y `reconstruir`.

## Prompt

```text
En catalog-service/src/main/java/com/bookit/catalog/domain/Negocio.java, normaliza el nombre con strip(). No cambies nada más ni toques otros ficheros.

Cambios:
1. Extrae la validación del nombre a un método privado estático que reciba el String nombre y devuelva el nombre normalizado, en este orden:
   a) Si nombre es null, lanza NombreNoValidoException.
   b) Aplica nombre.strip().
   c) Si el resultado isEmpty() o su length() supera MAX_NOMBRE (150), lanza NombreNoValidoException.
   d) Devuelve el nombre normalizado.
2. En crear(...) y reconstruir(...), usa el nombre devuelto por ese método al llamar al constructor privado, de forma que se guarde el valor normalizado.
3. Mantén en validar(...) las comprobaciones de categoria, ciudad y fechaCreacion, sin duplicar la validación del nombre.
4. En los throws de categoría y ciudad nulas, usa Categoria.MENSAJE_INVALIDA y Ciudad.MENSAJE_INVALIDA (constantes públicas que ya existen).
5. Usa un único mensaje para el nombre: "El nombre no puede ser nulo, vacío ni superar 150 caracteres".

Reglas: sin Lombok, sin imports de Spring, JPA ni Jakarta, con salto de línea final. No modifiques equals, hashCode, toString ni los getters.
```

## Resultado

Creó `normalizarNombre` como único sitio de la lógica, y `crear` y `reconstruir` usan el valor limpio. Además extrajo constantes para los mensajes de id y fecha.

## Qué tuve que corregir

Como la constante de `Categoria` y `Ciudad` se llamaba `MENSAJE_NULO`, `Negocio` usó ese nombre en lugar del que pedía el prompt. Los nombres `MENSAJE_FECHA_INVALIDO` y `MENSAJE_ID_INVALIDO` no son fieles (son de "nulo").

## Lección para el próximo prompt

Comprobar antes que las constantes que el prompt da por existentes tienen ese nombre exacto. Nombrar las constantes por lo que realmente comprueban.