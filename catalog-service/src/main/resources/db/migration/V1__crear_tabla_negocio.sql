CREATE TABLE negocio (
    id             UUID         PRIMARY KEY,
    nombre         VARCHAR(150) NOT NULL,
    categoria      VARCHAR(50)  NOT NULL,
    ciudad         VARCHAR(100) NOT NULL,
    fecha_creacion TIMESTAMPTZ  NOT NULL DEFAULT now(),
    CONSTRAINT ck_negocio_nombre_no_vacio CHECK (length(trim(nombre)) > 0)
);

CREATE INDEX ix_negocio_ciudad_categoria ON negocio (ciudad, categoria);

