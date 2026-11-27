# Modelo de dominio del catálogo

Resultado de la subtarea S2 de BOOK-003. Recoge los conceptos, las invariantes y la forma de crear un `Negocio`. Las decisiones están justificadas en los ADR 0009 a 0012.

## Conceptos

| Concepto | Entidad o valor | Tipo Java | Invariantes |
|---|---|---|---|
| `Negocio` | Entidad (se identifica por `id`) | Clase | Siempre nace completo y válido |
| `id` | Identidad | `UUID` | Lo genera la aplicación y no cambia nunca |
| `nombre` | Atributo simple | `String` | No nulo, no vacío ni solo espacios, máximo 150 caracteres |
| `Categoria` | Objeto de valor | `record` | No nula, no vacía ni solo espacios, máximo 50 caracteres |
| `Ciudad` | Objeto de valor | `record` | No nula, no vacía ni solo espacios, máximo 100 caracteres |
| `fecha_creacion` | Atributo simple | `Instant` | La decide la aplicación al crear, no el dominio ni la BD |

Los máximos coinciden con las columnas de la migración `V1` (`VARCHAR(150)`, `VARCHAR(50)`, `VARCHAR(100)`). El dominio valida por sí mismo y la BD actúa como segunda red de seguridad.

Regla candidata, no adoptada: rechazar nombres compuestos solo por dígitos. Se reevaluará cuando haya datos reales.

## Formas de crear un Negocio

| Forma | Quién la usa | Id | Fecha |
|---|---|---|---|
| `crear(...)` | Servicio de aplicación, al registrar un negocio nuevo | Genera un `UUID` nuevo | Recibida desde fuera |
| `reconstruir(...)` | Adaptador de persistencia, al leer de la BD | Recibe el id guardado | Recibe la fecha guardada |

Ambas validan las invariantes. No hay constructor vacío ni setters sobre campos con reglas. Si un dato debe cambiar, se hace con un método de negocio (por ejemplo `renombrar(...)`) que vuelve a validar.

## Entidad de dominio y entidad JPA

`Negocio` (en `domain`) no es una entidad JPA. Es una clase sin anotaciones. En `infrastructure.persistence` habrá una clase JPA distinta y el adaptador convierte entre ambas.

## Evolución prevista

- `Categoria` y `Ciudad` podrían pasar a tablas propias. El cambio exigiría una migración `V2`, y gracias al `record` se absorbería en un solo tipo.
- `Ciudad` podría ampliarse con país o provincia si el alcance se vuelve internacional.

## Pendiente (otras subtareas)

- Excepciones de dominio para datos inválidos y negocio inexistente (S4).
- Firmas de los puertos (S3).