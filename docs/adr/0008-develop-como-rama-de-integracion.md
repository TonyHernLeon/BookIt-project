# 0008. Rama develop como integración y main estable

Fecha: 2026-11-11
Estado: Aceptada

## Contexto

Los tickets se desarrollan en ramas `feature/BOOK-00X-descripcion` y se integran mediante Pull Request. Hay que decidir contra qué rama se fusionan y cuándo llega el código a `main`.

## Decisión

Las ramas `feature/*` se fusionan en `develop` mediante PR. `main` solo recibe código de `develop` al cerrar una fase, y queda como la versión estable del proyecto.

## Alternativas consideradas

- **Solo `main` (GitHub Flow)**: descartada por ahora. Es más simple, pero `main` recibiría trabajo en curso y dejaría de ser una versión cerrada y presentable.
- **Fusionar las ramas `feature/*` directamente en `main`**: descartada por el mismo motivo.

## Consecuencias

- **Positivas**: `main` siempre contiene versiones cerradas, y `develop` absorbe el trabajo intermedio.
- **Negativas**: hay que mantener dos ramas y acordarse de promover `develop` a `main`. Con un solo desarrollador, el beneficio es limitado.
- **Obligaciones**: proteger `main` y `develop` y configurar el CI para que se ejecute en los PR hacia ambas. Documentar en el README cuándo se promueve `develop` a `main`.