# 0001. Monorepo para todos los servicios

Fecha: 2026-11-08
Estado: Aceptada

## Contexto

BookIt se compone de varios servicios (catálogo, reservas, usuarios, notificaciones, gateway y frontend) que evolucionan a la vez y comparten infraestructura local (`docker-compose.yml`), documentación y, más adelante, pipeline de CI. El proyecto lo desarrolla una sola persona.

Hay que decidir si cada servicio vive en su propio repositorio o si todos comparten uno.

## Decisión

Usamos un único repositorio (monorepo) con un directorio por servicio en la raíz y la infraestructura común (`docker-compose.yml`, `docs/adr/`) junto a ellos.

## Alternativas consideradas

- **Un repositorio por servicio (multirepo)**: descartada. Aporta permisos y ciclos de despliegue independientes, pero aquí no hay equipos separados, y obligaría a duplicar o repartir el `docker-compose.yml`, el CI y los ADRs.
- **Monorepo**: elegida. Un solo `git clone` levanta todo el sistema, y los cambios que afectan a varios servicios (por ejemplo, un contrato entre catálogo y reservas) van en un único commit y un único PR.

## Consecuencias

- **Positivas**: infraestructura, documentación y convenciones compartidas en un solo sitio, y cambios atómicos entre servicios.
- **Negativas**: el historial de Git mezcla todos los servicios y el repositorio crece con el tiempo. Existe más riesgo de acoplamiento accidental entre servicios, por lo que hace falta disciplina.
- **Obligaciones**: el pipeline de CI debe configurarse para construir solo el servicio afectado por cada cambio. Cada servicio mantiene su propio `pom.xml` y su propio ciclo de build, sin depender del código de otro.