package com.bookit.catalog.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CategoriaTest {

    @Test
    void quitaEspaciosAlPrincipioYAlFinal() {
        assertEquals("Peluquería", new Categoria("  Peluquería  ").valor());
    }
}

