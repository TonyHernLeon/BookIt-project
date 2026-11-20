# 0014. Excepciones de dominio: jerarquía unchecked con una excepción por campo

Fecha: 2026-11-16
Estado: Aceptada

## Contexto

El dominio protege sus reglas (invariantes) y debe avisar cuando alguien las rompe. Para eso lanza excepciones. Una excepción de dominio habla de negocio ("el nombre no es válido"), no de tecnología ("NullPointerException"), y por eso no puede depender de Spring ni de HTTP.

En el catálogo hay dos situaciones de error de naturaleza distinta:

- **Dato inválido**: quien llama envía algo que rompe una invariante (nombre vacío, ciudad en blanco).
- **No encontrado**: el dato es válido, pero el negocio no existe (consultar un id que no está guardado).

Hay que decidir qué excepciones existen, cómo se organizan, si son *checked* o *unchecked*, y quién las lanza.

## Decisión

1. **Todas las excepciones de dominio son unchecked** (extienden de `RuntimeException`). Los puertos de entrada no declaran `throws`, y con *checked* habría que añadirlo a cada interfaz y a todo el que las llame.

2. **Jerarquía con una raíz común y una excepción concreta por cada campo:**

| Excepción | Extiende de | Significa |
|---|---|---|
| `DominioException` | `RuntimeException` | Cualquier error de negocio |
| `DatoInvalidoException` | `DominioException` | Un dato rompe una invariante |
| `NombreNoValidoException` | `DatoInvalidoException` | El nombre es nulo, vacío o supera 150 caracteres |
| `CategoriaNoValidaException` | `DatoInvalidoException` | La categoría es nula, vacía o supera 50 caracteres |
| `CiudadNoValidaException` | `DatoInvalidoException` | La ciudad es nula, vacía o supera 100 caracteres |
| `NegocioNoEncontradoException` | `DominioException` | El negocio buscado no existe |

3. **Las bases son abstractas y las excepciones viven en un subpaquete.** `DominioException` y `DatoInvalidoException` son `abstract`, con constructor `protected`: solo se lanzan excepciones concretas. Las seis viven en `com.bookit.catalog.domain.exception`, dentro de `domain`, así que no pueden importar Spring, JPA ni Jakarta.

4. **Quién lanza cada una:**

| Excepción | Quién la lanza |
|---|---|
| `NombreNoValidoException`, `CategoriaNoValidaException`, `CiudadNoValidaException` | `Negocio` (en `crear(...)` y `reconstruir(...)`) y los `record` `Categoria` y `Ciudad` |
| `NegocioNoEncontradoException` | El servicio de aplicación, cuando `NegocioRepository.obtenerPorId(...)` devuelve un `Optional` vacío |

5. **El dominio no conoce HTTP.** Traducir estas excepciones a respuestas (por ejemplo, 400 para `DatoInvalidoException` y 404 para `NegocioNoEncontradoException`) será trabajo de `infrastructure.web` en otro ticket.

## Alternativas consideradas

- **Excepciones *checked***: descartada. Obligan a declarar `throws` en las tres interfaces de entrada y a todo el código que las use, ensuciando firmas que queremos limpias.
- **Una única excepción para todos los datos inválidos, con un mensaje que diga qué campo falló**: descartada. Es más corta, pero obliga a leer el mensaje para saber qué pasó. Una excepción por campo se entiende por su nombre.
- **Excepciones sueltas por campo, sin raíz común**: descartada. Quien quiera tratar "cualquier dato inválido" tendría que capturar tres excepciones y añadir otra cada vez que aparezca un campo nuevo. La jerarquía lo resuelve con una sola.
- **Usar excepciones estándar de Java (`IllegalArgumentException`)**: descartada. No distinguen un dato inválido de otros errores y no hablan el lenguaje del negocio.
- **Que el dominio lance `NegocioNoEncontradoException`**: descartada. "No existe" es el resultado de una consulta al almacenamiento, no una regla interna de `Negocio`. Coordinar esa consulta es trabajo del servicio de aplicación.
- **Bases instanciables (`public class`)**: descartada. Permitirían lanzar una excepción genérica y se perdería el nombre que dice qué pasó.
- **Jerarquía unchecked con bases abstractas y una excepción por campo**: elegida.

## Consecuencias

- **Positivas**:
  - Los nombres explican el error sin leer el código.
  - La capa web podrá tratar con una sola captura todos los datos inválidos (`DatoInvalidoException`) y con otra el caso no encontrado.
  - Los puertos conservan firmas limpias, sin `throws`.
  - El dominio sigue libre de Spring y de HTTP.
- **Negativas**:
  - Seis clases para cuatro situaciones de error.
  - Con *unchecked*, el compilador no obliga a tratar los errores: hay que documentarlos y recordar capturarlos en la capa web.
  - Añadir un campo nuevo con reglas implica crear su excepción concreta.
- **Obligaciones**:
  - Toda excepción de dominio nueva debe extender de `DominioException` y no importar frameworks.
  - Los mensajes de las excepciones deben explicar qué regla se rompió, sin exponer datos sensibles.
  - Los tests de los casos de uso comprobarán qué excepción se lanza en cada situación.
  - Las reglas de longitud máxima coinciden con la migración `V1` (150, 50 y 100 caracteres), según el [ADR 0010](0010-categoria-ciudad-como-records.md) y el [ADR 0012](0012-nombre-de-negocio-como-string.md).
  - Los mensajes de las excepciones son para desarrolladores. La capa web traducirá por tipo de excepción, con ficheros `.properties` si hace falta mostrar textos al usuario o soportar varios idiomas.