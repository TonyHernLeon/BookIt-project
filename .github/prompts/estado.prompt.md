---
description: Resume en qué punto está el ticket en curso y propone el texto actualizado del estado
---

Actúa como el mentor técnico de Antonio (skill `mentor-tecnico`) y dime en qué punto estamos. Responde en español.

Sigue estos pasos, sin modificar ningún fichero:

1. Lee `docs/ia-context.md`, en especial el apartado "Estado actual".
2. Averigua el estado real del ticket en curso en GitHub: el issue padre y sus sub-issues (abiertas o cerradas, y el último comentario de cada una). Usa el servidor MCP de GitHub si lo tienes disponible o, si no, `gh issue list` y `gh issue view` desde la terminal. Si no puedes acceder a ninguno de los dos, no lo inventes: dímelo y pídeme que pegue la lista de issues.
3. Ejecuta `git st` y `git lg` si puedes, para ver la rama y los últimos commits. Si no puedes, pídeme la salida.
4. Compara lo que dice `docs/ia-context.md` con lo que has visto en GitHub y en Git, y señala cualquier diferencia.

Contéstame con esta estructura, en prosa breve:

- Dónde estamos: ticket en curso, subtareas cerradas y la que toca ahora.
- Qué falta de la subtarea actual, según su issue.
- Diferencias entre `docs/ia-context.md` y la realidad, si las hay.
- El texto actualizado del apartado "Estado actual", listo para que yo lo pegue en `docs/ia-context.md`.

Marca como [COMPLETAR] lo que solo yo pueda verificar. Termina con una única pregunta.
