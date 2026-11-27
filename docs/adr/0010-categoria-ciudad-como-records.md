# 0010. Categoria y Ciudad como records con invariantes propias

Fecha: 2026-11-13
Estado: Aceptada

## Contexto

La `V1` guarda `categoria` y `ciudad` como `VARCHAR`. Una categoría como "clínica" puede subdividirse (clínica dental, dermoestética), y varias ciudades comparten nombre (Córdoba, Mérida, Cartagena). Hay que decidir su tipo en el dominio sin ampliar el alcance de BOOK-003.

## Decisión

`Categoria` y `Ciudad` son `record` con una única propiedad de texto. Cada uno valida sus invariantes: no nulo, no vacío ni solo espacios, y máximo 50 y 100 caracteres respectivamente. Se guardan como `VARCHAR` sin cambiar la `V1`.

`Categoria` y `Ciudad` normalizan el texto con `strip()` antes de validar, y la longitud máxima se mide sobre el valor ya normalizado. Cada `record` expone una constante pública con su mensaje de error, que `Negocio` reutiliza.

## Alternativas consideradas

- **`String` validado dentro de `Negocio`**: descartada. Mezcla las reglas de todos los campos en un solo constructor y su evolución afectaría a `Negocio`.
- **`enum` de categorías**: descartada. Es un conjunto cerrado y la lista crecerá, así que cada categoría nueva exigiría recompilar y desplegar.
- **Tablas propias de categoría y ciudad**: aplazada. Es una evolución razonable, pero exige una `V2`, nuevas entidades y casos de uso, y excede el alcance del ticket.
- **`record` con reglas**: elegida. Empieza simple y absorbe la evolución en un único tipo.

## Consecuencias

- **Positivas**: es imposible crear una categoría o ciudad inválida, y pasar a tablas o añadir país a `Ciudad` solo cambia ese tipo.
- **Negativas**:
  - Más clases para reglas hoy simples.
  - `strip()` elimina los espacios sobrantes, pero el texto libre sigue permitiendo variantes de mayúsculas y tildes ("Peluquería" y "peluqueria") hasta que se normalice o se pase a tablas propias.
- **Obligaciones**: si se pasa a tablas propias, se escribirá un ADR nuevo y una migración `V2`; la `V1` no se edita.