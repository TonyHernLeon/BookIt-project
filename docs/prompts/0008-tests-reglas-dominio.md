# 0008. Esqueleto de tests de reglas del dominio

Fecha: 2026-11-24

## Objetivo

Generar solo la estructura de los tests de reglas de `Categoria`, `Ciudad` y `Negocio` (métodos con nombre, sin cuerpo), para escribir yo el contenido de cada test, comprobarlo en rojo con el ejercicio de romper y explicarlo línea a línea.

## Prompt

````text
Actúa como desarrollador Java senior. Trabajo en el módulo catalog-service (Java 21, JUnit 5, Maven). Necesito SOLO el esqueleto de tests de reglas para tres clases de dominio. Yo escribiré el cuerpo de cada test.

Ruta y paquete exactos:
- Directorio: catalog-service/src/test/java/com/bookit/catalog/domain/
- Paquete: com.bookit.catalog.domain

Ficheros permitidos (ya existen, añade métodos a la clase existente sin borrar ni modificar el test de strip() que ya tiene):
- CategoriaTest.java
- CiudadTest.java
- NegocioTest.java
No toques nada más. No crees ficheros nuevos ni modifiques ningún fichero de src/main.

Contexto de las clases (no las modifiques):
- Categoria y Ciudad son records con un único componente String llamado valor. Máximo 50 y 100 caracteres. Lanzan CategoriaNoValidaException y CiudadNoValidaException (paquete com.bookit.catalog.domain.exception).
- Negocio tiene los métodos estáticos crear(nombre, categoria, ciudad, fechaCreacion) y reconstruir(id, nombre, categoria, ciudad, fechaCreacion). El nombre admite máximo 150 caracteres y lanza NombreNoValidoException. Los nulos de id y fechaCreacion lanzan NullPointerException.

Qué quiero en cada clase: un método @Test por caso, con el cuerpo VACÍO y la anotación @Disabled("Pendiente de implementar"). Sin aserciones, sin datos de prueba y sin lógica. Solo firma y anotaciones.

CategoriaTest (añadir estos métodos):
- constructor_conNulo_lanzaCategoriaNoValida
- constructor_conVacio_lanzaCategoriaNoValida
- constructor_conSoloEspacios_lanzaCategoriaNoValida
- constructor_conLongitudMaxima50_seConstruye
- constructor_conLongitud51_lanzaCategoriaNoValida

CiudadTest (añadir estos métodos):
- constructor_conNulo_lanzaCiudadNoValida
- constructor_conVacio_lanzaCiudadNoValida
- constructor_conSoloEspacios_lanzaCiudadNoValida
- constructor_conLongitudMaxima100_seConstruye
- constructor_conLongitud101_lanzaCiudadNoValida

NegocioTest (añadir estos métodos):
- crear_conNombreNulo_lanzaNombreNoValido
- crear_conNombreVacio_lanzaNombreNoValido
- crear_conNombreSoloEspacios_lanzaNombreNoValido
- crear_conNombreDe150Caracteres_seCrea
- crear_conNombreDe151Caracteres_lanzaNombreNoValido
- crear_dosLlamadasConLosMismosDatos_generanIdsDistintos
- reconstruir_conIdDado_conservaElId
- reconstruir_conIdNulo_lanzaNullPointer
- crear_conFechaNula_lanzaNullPointer
- reconstruir_conFechaNula_lanzaNullPointer

Reglas obligatorias:
- Prohibido importar Spring, JPA, Jakarta, Hibernate o Mockito. Solo JUnit 5 (org.junit.jupiter.api.Test y org.junit.jupiter.api.Disabled) y las clases del propio dominio si hacen falta.
- Sin Lombok.
- Sin comentarios innecesarios ni Javadoc.
- En NegocioTest, si hace falta una fecha, declara una única constante con este nombre exacto: private static final Instant FECHA_FIJA = Instant.parse("2026-01-01T10:00:00Z"); Si el fichero ya tiene una constante equivalente, no la dupliques y dímelo. Nunca uses Instant.now().
- No añadas imports que no se usen.
- Deja los métodos en el orden en que los he listado, tras los tests que ya existen.
- Cada fichero debe terminar con un salto de línea final.

Entrega: el contenido completo de cada uno de los tres ficheros, uno detrás de otro, indicando la ruta sobre cada uno. Nada más: sin explicaciones ni sugerencias de código extra.

A continuación te pego el contenido actual de CategoriaTest.java, CiudadTest.java y NegocioTest.java:
[PEGAR LOS TRES FICHEROS]
````

## Resultado

La IA añadió los métodos pedidos en `CategoriaTest` (5), `CiudadTest` (5) y `NegocioTest` (10), con los nombres exactos y en el orden indicado, cada uno con `@Test` y `@Disabled("Pendiente de implementar")`. Los tests de `strip()` que ya existían se conservaron. No se tocó ningún fichero de `src/main`. El esqueleto compila con `./mvnw test-compile` sin errores.

Después se revisó cada caso contra los ADR 0006, 0009, 0011, 0012 y 0014 para decidir qué datos usar en cada test (nulo, vacío, solo espacios incluidos tabuladores y saltos de línea, 150/151 caracteres con y sin espacios alrededor, ids fijos y fecha fija) y cuál es la excepción esperada.

## Qué tuve que corregir

- No se declaró la constante `FECHA_FIJA` que pedía el prompt: `NegocioTest` usa `Instant.parse("2026-01-01T10:00:00Z")` en línea en el test existente. Al implementar los tests restantes conviene extraerla a una constante para no repetirla.
- `NegocioTest` importa `Disabled`, `Test`, `Instant` y `assertEquals`, y todos se usan. Al implementar los cuerpos habrá que añadir `assertThrows`, `assertNotEquals`, `UUID` y las excepciones.
- El esqueleto no cubre categoría nula, ciudad nula ni `reconstruir` con nombre inválido, casos que sí prevén el ADR 0009 y el código. Quedan como mejora.

## Lección

Un esqueleto con `@Disabled("Pendiente de implementar")` evita que un test vacío salga en verde sin comprobar nada. Pedir a la IA solo la estructura, con nombres exactos, me deja a mí lo importante: elegir los datos, las aserciones y comprobar cada test en rojo con el ejercicio de romper. Al analizar los casos aprendí que los errores viven en la frontera (150 frente a 151 caracteres) y en el orden de las operaciones (el `strip()` va antes de validar vacío y longitud). También que un `id` o una fecha nulos son errores de programación (`NullPointerException`), mientras que un nombre nulo es un dato de usuario inválido (`NombreNoValidoException`), y que al probar un campo el resto de datos debe ser válido para que el test falle por la causa correcta.
