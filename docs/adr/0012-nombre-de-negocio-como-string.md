# 0012. El nombre del negocio es un String validado en Negocio

Fecha: 2026-11-13
Estado: Aceptada

## Contexto

El nombre no tiene identidad propia y sus reglas son pocas y simples. La `V1` exige `NOT NULL`, un `CHECK` de no vacío y `VARCHAR(150)`.

## Decisión

`nombre` es un `String` dentro de `Negocio`. El método de creación valida que no sea nulo, vacío ni solo espacios, y que no supere 150 caracteres.

## Alternativas consideradas

- **`record NombreNegocio`**: descartada por ahora. Con tres reglas, aporta más código que valor. Se extraerá si las reglas crecen.
- **Prohibir nombres solo numéricos**: aplazada. Existen marcas así y es una regla de negocio que conviene decidir con datos reales.
- **`String` validado en `Negocio`**: elegida.

## Consecuencias

- **Positivas**: menos clases y reglas visibles en un solo sitio.
- **Negativas**: la validación vive en `Negocio`, así que cualquier otra clase que use un nombre debe repetirla o extraer el tipo.
- **Obligaciones**: un nombre inválido lanza una excepción de dominio, que se define en S4.