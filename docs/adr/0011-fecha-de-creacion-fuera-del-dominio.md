# 0011. La fecha de creación se recibe desde fuera del dominio

Fecha: 2026-11-13
Estado: Aceptada

## Contexto

La fecha de creación la decide la aplicación y no la BD. Si `Negocio` llamara directamente a `Instant.now()`, un test no podría fijar una fecha concreta.

## Decisión

El servicio de aplicación obtiene el instante actual con un `java.time.Clock` y se lo pasa a `Negocio.crear(...)`. `reconstruir(...)` recibe la fecha guardada. El dominio nunca consulta el reloj.

## Alternativas consideradas

- **`Instant.now()` dentro de `Negocio`**: descartada. Hace los tests no deterministas.
- **Fecha generada por la BD (`DEFAULT now()`)**: descartada. El objeto existiría sin fecha hasta guardarse.
- **`Clock` inyectado en el servicio de aplicación**: elegida. Es del JDK, no requiere Spring y permite un reloj fijo en tests.

## Consecuencias

- **Positivas**: casos de uso deterministas y comprobables sin Spring.
- **Negativas**: el servicio debe recibir un `Clock` en su construcción.
- **Obligaciones**: en producción se configura el reloj del sistema y en tests un reloj fijo. El `DEFAULT now()` de la `V1` queda como red de seguridad.