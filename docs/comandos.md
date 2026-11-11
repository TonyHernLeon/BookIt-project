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
| Compilar y pasar tests | `catalog-service/` (con la BD levantada) | `mvn clean verify` |
| Arrancar el servicio | `catalog-service/` | `mvn spring-boot:run` |
| Comprobar salud | Navegador | `localhost:8080/actuator/health` |

## Git

| Para qué | Comando |
|---|---|
| Estado resumido | `git st` (alias) |
| Quitar algo del staging | `git restore --staged <fichero>` |
| Deshacer cambios de un fichero | `git restore <fichero>` |
| Ver qué cambié | `git diff <fichero>` |

## Cuando algo falla

| Síntoma | Causa habitual | Qué hago |
|---|---|---|
| Connection refused al arrancar | La BD no está levantada | `docker compose up -d` |
| Checksum mismatch de Flyway | Edité una migración ya aplicada | Revertir el fichero, nunca editarla |
| No encuentra `@SpringBootConfiguration` | Test en otro paquete que la clase principal | Mover el test al mismo paquete |