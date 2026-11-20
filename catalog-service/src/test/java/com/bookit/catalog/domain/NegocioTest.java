package com.bookit.catalog.domain;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NegocioTest {

    @Test
    void quitaEspaciosAlPrincipioYAlFinalDelNombre() {
        Negocio negocio = Negocio.crear(
                "  Peluquería Ana  ",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z"));

        assertEquals("Peluquería Ana", negocio.nombre());
    }
}

