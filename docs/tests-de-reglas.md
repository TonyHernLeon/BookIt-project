# Tests de reglas de dominio

Guía de BOOK-003 (S6). Explica qué es un test de reglas, en qué se diferencia de los tests de servicio que ya tenemos y dónde y cómo se escriben los del catálogo.

## Qué es un test de reglas

Un test de reglas comprueba que una clase del dominio cumple lo que promete sobre los datos que acepta y los que rechaza. Se centra en una sola clase (`Categoria`, `Ciudad` o `Negocio`), no usa colaboradores y es una entrada con una salida: le doy un dato y compruebo si lo acepta, lo normaliza o lanza una excepción.

La idea se entiende con un portero de discoteca con la norma "mayores de 18". No basta con probar que entra uno de 30 y que no entra uno de 5, porque los errores viven en la frontera: lo que importa es qué hace con uno de 17 y con uno de 18. Con el código pasa igual. Si alguien escribe `>` donde debía ir `>=`, solo lo detecta un test que pruebe justo el límite y justo el siguiente valor.

## Diferencia con los tests de servicio

| Aspecto | Test de reglas (dominio) | Test de servicio (aplicación) |
|---|---|---|
| Qué prueba | Una clase: sus datos válidos e inválidos | Un caso de uso: cómo se coordinan las piezas |
| Clases implicadas | Solo la que se prueba | `NegocioService` + repositorio en memoria + `Clock` fijo |
| Pregunta que responde | "¿Acepta y rechaza lo que debe?" | "¿Hace el flujo correcto y guarda o devuelve lo esperado?" |
| Dónde se rompe | En la propia clase (límite, nulo, vacío) | En la orquestación (no guarda, no filtra, no lanza) |
| Preparación | Casi ninguna: `new Categoria("...")` | `@BeforeEach` con repositorio y servicio |
| Ejemplo | `Categoria` con 51 caracteres lanza excepción | `consultarPorId` con id desconocido lanza `NegocioNoEncontradoException` |
| Paquete de test | `com.bookit.catalog.domain` | `com.bookit.catalog.application.service` |
| Necesita Docker o BD | No | No |
| Ejercicio de romper | Cambiar el límite o quitar una comprobación en la clase | Cambiar `orElseThrow` por `orElse(null)` o `&&` por `||` |

Los dos tipos se complementan. Los de reglas garantizan que ningún `Negocio`, `Categoria` o `Ciudad` inválido llega a existir. Los de servicio garantizan que, con objetos válidos, el caso de uso los guarda, los busca y los filtra bien. Si falla un test de reglas, el error está en el dominio. Si falla uno de servicio con el dominio en verde, el error está en la orquestación.

## Dos tipos de error en el dominio (ADR 0014 y 0009)

| Origen del dato | Qué lanza | Ejemplo | Por qué |
|---|---|---|---|
| Usuario o petición externa | Excepción de dominio (`NombreNoValidoException`, `CategoriaNoValidaException`, `CiudadNoValidaException`) | Nombre vacío, categoría de 51 caracteres | Es un dato malo que se puede corregir: la capa web lo traducirá a un 400 |
| Código del programador | `NullPointerException` vía `Objects.requireNonNull` | `id` o `fechaCreacion` nulos en `Negocio` | Es un error de programación: no tiene sentido recuperarse |

Es una pregunta típica de entrevista: por qué un nombre nulo lanza una excepción de dominio y un `id` nulo lanza `NullPointerException`.

## Casos que hay que cubrir

### `Categoria` (máximo 50) y `Ciudad` (máximo 100)

Los dos records siguen la misma plantilla. Solo cambian la excepción y el número.

| Entrada | Resultado esperado | Qué protege |
|---|---|---|
| `null` | Lanza la excepción de dominio | La rama del nulo |
| `""` | Lanza la excepción de dominio | La rama de vacío |
| `"   "` (solo espacios) | Lanza la excepción de dominio | Que el vacío se evalúa después del `strip()` |
| Longitud máxima (50 o 100) | Se construye y `valor()` tiene esa longitud | El límite superior es válido |
| Longitud máxima + 1 (51 o 101) | Lanza la excepción de dominio | El límite superior se rechaza |

### `Negocio` (nombre máximo 150)

| Caso | Resultado esperado | Qué protege |
|---|---|---|
| Nombre `null` | Lanza `NombreNoValidoException` | La rama del nulo |
| Nombre `""` | Lanza `NombreNoValidoException` | La rama de vacío |
| Nombre de solo espacios | Lanza `NombreNoValidoException` | Vacío evaluado después del `strip()` |
| Nombre de 150 caracteres | Se crea y `nombre()` tiene 150 | El límite es válido |
| Nombre de 151 caracteres | Lanza `NombreNoValidoException` | El límite se rechaza |
| Dos llamadas a `crear` con los mismos datos | Los `id()` son distintos | El UUID lo genera la aplicación (ADR 0006) |
| `reconstruir` con un UUID dado | `id()` devuelve ese mismo UUID | Rehidratar no inventa un id nuevo |
| `reconstruir` con `id` nulo | Lanza `NullPointerException` | Error de programación (opcional) |
| `crear` o `reconstruir` con `fechaCreacion` nula | Lanza `NullPointerException` | Error de programación (opcional) |

## Técnica de la frontera

Con un máximo de 50, se prueban siempre dos longitudes: 50 (debe aceptarse) y 51 (debe rechazarse). Con esas dos pruebas la frontera queda fijada en su sitio exacto. Probar solo 10 y 200 no detecta que alguien haya cambiado `> 50` por `>= 50`.

Para fabricar textos de longitud exacta no los escribas a mano, usa `"a".repeat(50)`. Así la longitud se lee en el propio test y no hay que contar letras.

## Cómo se escribe cada tipo de test

Para los casos que deben fallar, se usa `assertThrows` con la excepción concreta, no con la clase padre:

```java
assertThrows(CategoriaNoValidaException.class, () -> new Categoria(null));
```

Para los casos que deben funcionar, no basta con que no explote: hay que añadir una aserción. Un test sin aserción solo demuestra que no se lanzó nada.

```java
Categoria categoria = new Categoria("a".repeat(50));
assertEquals(50, categoria.valor().length());
```

Para `crear` con ids distintos, compara los dos ids con `assertNotEquals`. Para `reconstruir`, compara el id recibido con `negocio.id()`.

## Dónde se crean

No se crean ficheros nuevos: cada clase de reglas va en la clase de test que ya existe, junto al test de `strip()`. Todas están en el módulo `catalog-service`, en `src/test`, y en el mismo paquete que la clase que prueban.

| Clase probada | Fichero de test (existente) |
|---|---|
| `Categoria` | `catalog-service/src/test/java/com/bookit/catalog/domain/CategoriaTest.java` |
| `Ciudad` | `catalog-service/src/test/java/com/bookit/catalog/domain/CiudadTest.java` |
| `Negocio` | `catalog-service/src/test/java/com/bookit/catalog/domain/NegocioTest.java` |

Los tests de servicio siguen en `src/test/java/com/bookit/catalog/application/service/`. Que los tests de reglas estén en el mismo paquete que su clase les permite probar sin necesidad de abrir nada y refleja la estructura de `src/main`.

## Convenciones

| Tema | Regla |
|---|---|
| Nombre del test | `metodo_condicion_resultado`, por ejemplo `constructor_conLongitud51_lanzaCategoriaNoValida` |
| Fechas | Siempre fijas con `Instant.parse("2026-01-01T10:00:00Z")`, nunca `Instant.now()` |
| Frameworks | Sin Spring ni Mockito: son clases Java puras |
| Lombok | No se usa |
| Un test, una idea | Un caso por método, con su propio nombre |
| Esqueleto | Los métodos sin escribir llevan `@Disabled("Pendiente de implementar")` para que el verde no mienta |
| Ejecución | `mvn test -Dtest=CategoriaTest` (no necesita Docker) |

## Ejercicio de romper

Un test que nunca ha estado en rojo no demuestra que pueda fallar. Para cada grupo, sabotea el código de `src/main` y comprueba el rojo. Después restaura y comprueba con `git st` que ese fichero no aparece modificado.

| Test | Sabotaje temporal | Rojo esperado |
|---|---|---|
| Longitud máxima válida | Bajar el límite en la clase (por ejemplo, de 50 a 49) | Falla el test del límite válido |
| Longitud máxima + 1 | Subir el límite en la clase (por ejemplo, de 50 a 51) | Falla el test de rechazo |
| Nulo | Quitar la comprobación de nulo | Falla con otra excepción, no con la de dominio |
| Vacío | Quitar la comprobación de `isEmpty()` | Falla el test de vacío y el de solo espacios |
| Solo espacios | Evaluar el vacío antes del `strip()` | Falla el test de solo espacios |
| Ids distintos | Hacer que `crear` use un UUID fijo | Falla el test de ids distintos |
| `reconstruir` conserva el id | Hacer que `reconstruir` genere un UUID nuevo | Falla el test del id |

## Errores frecuentes

| Error | Consecuencia |
|---|---|
| Probar solo valores lejanos del límite (10 y 200) | No detecta un `>=` en lugar de `>` |
| Usar `DominioException.class` en `assertThrows` | Pasa aunque salte otra excepción de dominio |
| Test del caso válido sin aserción | Solo demuestra que no explotó |
| Escribir los textos largos a mano | Longitudes mal contadas |
| Olvidar el caso de solo espacios | No se prueba que el `strip()` ocurre antes del vacío |
| Confundir `NullPointerException` con excepción de dominio | Se rompe la distinción de ADR 0014 |

## Preguntas de revisión

1. ¿Qué dos longitudes pruebas para un máximo de 50 y por qué esas?
2. ¿Por qué `"   "` es un caso distinto de `""`?
3. ¿Por qué `id` nulo lanza `NullPointerException` y nombre nulo lanza `NombreNoValidoException`?
4. ¿Qué detecta un test de reglas que no detecta uno de servicio, y al revés?
5. ¿Por qué `reconstruir` conserva el id y `crear` genera uno nuevo?
