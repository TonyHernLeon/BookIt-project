# 0009. Negocio como entidad de dominio que nace válida

Fecha: 2026-11-13
Estado: Aceptada

## Contexto

`Negocio` se identifica por su `id`: dos negocios con los mismos datos pueden ser distintos. El [ADR 0006](0006-uuid-como-identificador.md) establece que el `UUID` lo genera la aplicación. Hay que decidir quién lo genera y cómo se construye un `Negocio` nuevo frente a uno recuperado de la BD.

## Decisión

`Negocio` es una entidad de dominio sin anotaciones de frameworks. Se crea con `crear(...)`, que genera el `UUID` y valida las invariantes, o con `reconstruir(...)`, que recibe el id y la fecha ya existentes. No hay constructor vacío ni setters sobre campos con reglas. El id no cambia nunca.

## Alternativas consideradas

- **Constructor vacío y setters (estilo JavaBean)**: descartada. Deja el objeto en estado inválido hasta que se rellena y permite romper las invariantes después.
- **Id generado por la BD**: descartada. Contradice el ADR 0006 y deja el objeto sin identidad hasta guardarlo, lo que impide probar los casos de uso sin BD.
- **El servicio de aplicación genera el id**: descartada. La regla "todo negocio nuevo tiene identidad única" quedaría fuera del dominio y otro código podría crear negocios con ids arbitrarios.
- **`crear(...)` y `reconstruir(...)`**: elegida. Cada momento de la vida del negocio tiene su método y el id nunca se inventa para un negocio existente.

## Consecuencias

- **Positivas**: el objeto siempre es válido y los casos de uso se prueban sin BD.
- **Negativas**: dos formas de construcción que hay que distinguir bien. `reconstruir(...)` es público y podría usarse por error fuera del adaptador de persistencia.
- **Obligaciones**: la clase JPA vive en `infrastructure.persistence` y el adaptador convierte entre ambas usando `reconstruir(...)`.