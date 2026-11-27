# Comandos de uso habitual

Guía personal. Se amplía a medida que aparecen comandos nuevos.

## Entorno local

| Para qué | Dónde ejecutarlo | Comando |
|---|---|---|
| Levantar la BD | Raíz del repo | `docker compose up -d` |
| Ver qué corre | Raíz del repo | `docker compose ps` |
| Ver logs de la BD | Raíz del repo | `docker compose logs catalog-db` |
| Parar sin perder datos | Raíz del repo | `docker compose down` |
| Parar y BORRAR datos | Raíz del repo | `docker compose down -v` |

## Build y arranque del servicio

| Para qué | Dónde ejecutarlo | Comando |
|---|---|---|
| Levantar la BD | Raíz del repo | `docker compose up -d` |
| Compilar y pasar tests | `catalog-service/` (con la BD levantada) | `mvn clean verify` |
| Ejecutar solo los tests del dominio | `catalog-service/` (no necesita Docker) | `mvn test -Dtest='CategoriaTest,CiudadTest,NegocioTest'` |
| Arrancar el servicio | `catalog-service/` | `mvn spring-boot:run` |
| Comprobar salud | Navegador | `localhost:8080/actuator/health` |
Antes de commitear: levanta la BD (`docker compose up -d` en la raíz) y ejecuta `mvn clean verify` en `catalog-service/`. Debe acabar en `BUILD SUCCESS`.

## Git

| Para qué | Comando |
|---|---|
| Estado resumido | `git st` (alias) |
| Quitar algo del staging | `git restore --staged <fichero>` |
| Deshacer cambios de un fichero | `git restore <fichero>` |
| Ver qué cambié | `git diff <fichero>` |
| Que git pagine solo cuando la salida no cabe en pantalla | `git config --global core.pager "less -FRX"` |

## Cuando algo falla

| Síntoma | Causa habitual | Qué hago |
|---|---|---|
| Connection refused al arrancar | La BD no está levantada | `docker compose up -d` |
| Checksum mismatch de Flyway | Edité una migración ya aplicada | Revertir el fichero, nunca editarla |
| No encuentra `@SpringBootConfiguration` | Test en otro paquete que la clase principal | Mover el test al mismo paquete |
| Aparece `failed to connect to the docker API ... docker.sock` | Docker Desktop no está arrancado | Abrir Docker Desktop y esperar a Engine running |
| Un test pasa pero no debería, o el log parece repetido | Se pegó una salida antigua o el test no comprueba lo que creemos | Mirar la hora de `Finished at` (debe ser nueva) y romper a propósito la línea que prueba: el test debe ponerse en rojo. Después, revertir |
| `git add .` no añade un fichero y `git commit` dice "no changes added" | Estás en una subcarpeta y el fichero está fuera de ella | Subir a la raíz del repo antes de `git add .`, o dar la ruta del fichero |
| IntelliJ ofrece dos clases con el mismo nombre al importar | Hay librerías en el classpath con clases homónimas | Elegir la del JDK (`java.*`) para tipos básicos, y comprobar que los imports no son de Spring, JPA, Jakarta ni Hibernate en `domain` y `port` |
| Tras `git diff`, `git log` o `git show` la terminal se queda en pantalla completa con `~` en cada línea y `(END)` abajo | Git abre la salida en un paginador (`less`) cuando es más larga que la pantalla. Si sale vacía con `(END)`, no había nada que mostrar, a menudo porque la ruta no existe desde donde estás | Pulso `q` para salir. Si estaba vacío, reviso desde qué carpeta lancé el comando y uso rutas relativas a ella. Para ver la salida directamente: `git --no-pager diff` |