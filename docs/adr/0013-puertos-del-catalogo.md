# 0013. Puertos del catálogo: una interfaz por caso de uso

Fecha: 2026-11-14
Estado: Aceptada

## Contexto

En arquitectura hexagonal, el núcleo de la aplicación (`domain` y `application`) no conoce la tecnología externa: ni la base de datos, ni HTTP, ni Spring. Para comunicarse con el exterior usa **puertos**, que son interfaces Java:

- **Puertos de entrada** (`application.port.in`): lo que el exterior puede pedirle a la aplicación. Por ejemplo, "registra este negocio".
- **Puertos de salida** (`application.port.out`): lo que la aplicación necesita del exterior. Por ejemplo, "guarda este negocio".

El catálogo tiene tres funcionalidades: registrar un negocio, consultarlo por su id y buscar negocios por ciudad y categoría. Hay que decidir cómo se agrupan en interfaces y qué datos entran y salen de cada una.

## Decisión

1. **Una interfaz de entrada por caso de uso**, cada una con un único método:

| Interfaz | Método | Recibe | Devuelve |
|---|---|---|---|
| `RegistrarNegocioUseCase` | `registrar(...)` | Nombre, categoría y ciudad | El `Negocio` creado |
| `ConsultarNegocioUseCase` | `consultarPorId(...)` | Un `UUID` | El `Negocio` (si no existe, lanza un error de dominio) |
| `BuscarNegociosUseCase` | `buscar(...)` | `Ciudad` y `Categoria` | Una `List<Negocio>` (vacía si no hay resultados) |

2. **Un puerto de salida, `NegocioRepository`**, expresado en lenguaje del negocio y no de la base de datos:

| Método | Recibe | Devuelve |
|---|---|---|
| `guardar` | Un `Negocio` | El `Negocio` guardado |
| `obtenerPorId` | Un `UUID` | `Optional<Negocio>` |
| `buscarPorCiudadYCategoria` | `Ciudad` y `Categoria` | `List<Negocio>` |

3. **La entrada recibe datos sueltos; la salida recibe el `Negocio` ya construido.** Quien llama desde fuera (por ejemplo, un controlador) solo tiene un nombre, una categoría y una ciudad. El servicio de aplicación construye el `Negocio` con `Negocio.crear(...)` (ver [ADR 0009](0009-negocio-como-entidad-de-dominio.md)) y se lo pasa ya válido al puerto de salida.

4. **Las firmas solo usan tipos del dominio y del JDK** (`Negocio`, `Ciudad`, `Categoria`, `UUID`, `Optional`, `List`). Nunca aparecen clases de JPA, SQL ni de Spring.

## Alternativas consideradas

- **Una sola interfaz con los tres métodos (por ejemplo, `CatalogoUseCase`)**: descartada. Es más corta y se parece a un `NegocioService` clásico, pero quien solo necesita consultar queda atado también a registrar y a buscar. Además, cada cambio afecta a toda la interfaz y las pruebas se mezclan.
- **Que el puerto de entrada reciba un `Negocio` ya construido**: descartada. Quien llama desde fuera no debería construir objetos del dominio. Además, saltarse `Negocio.crear(...)` rompería la regla de que el id y las validaciones los gestiona el dominio.
- **Dos búsquedas separadas (por ciudad y por categoría)**: descartada por ahora. El ticket pide buscar por ambas a la vez, y el índice de la migración `V1` es `(ciudad, categoria)`. Si hace falta buscar solo por ciudad, será un caso de uso nuevo.
- **`Optional` también en el puerto de entrada**: descartada. En la entrada, "no existe" es una situación de negocio y se expresa con una excepción de dominio (se define en la subtarea S4). El `Optional` se queda en la salida, donde solo indica que la base de datos no encontró nada.
- **Una interfaz por caso de uso y un puerto de salida en lenguaje del dominio**: elegida.

## Consecuencias

- **Positivas**:
  - Cada caso de uso se entiende, se prueba y evoluciona por separado.
  - Se puede sustituir PostgreSQL por un almacenamiento en memoria sin modificar los casos de uso, porque las firmas no mencionan tecnología.
  - Los nombres explican qué hace la aplicación sin leer el código.
- **Negativas**:
  - Más ficheros (tres interfaces de entrada en vez de una) para funcionalidades hoy muy pequeñas.
  - Quien venga de una arquitectura clásica con servicios puede tardar en acostumbrarse a pensar en casos de uso.
- **Obligaciones**:
  - Ninguna firma de `port.in` ni `port.out` puede mencionar JPA, SQL, Spring ni clases de infraestructura.
  - El adaptador de persistencia implementará `NegocioRepository` y convertirá entre el `Negocio` del dominio y la entidad JPA.
  - El servicio de aplicación implementará las tres interfaces de entrada y usará `NegocioRepository`.