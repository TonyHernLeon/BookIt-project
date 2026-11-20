package com.bookit.catalog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CiudadTest {

    @Test
    void quitaEspaciosAlPrincipioYAlFinal() {
        assertEquals("Sevilla", new Ciudad("  Sevilla  ").valor());
    }
}

