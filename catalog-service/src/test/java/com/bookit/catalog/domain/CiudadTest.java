package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.CiudadNoValidaException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CiudadTest {

    @Test
    void quitaEspaciosAlPrincipioYAlFinal() {
        assertEquals("Sevilla", new Ciudad("  Sevilla  ").valor());
    }

    @Test
    void constructor_conNulo_lanzaCiudadNoValida() {
        assertThrows(CiudadNoValidaException.class, () -> new Ciudad(null));
    }

    @Test
    void constructor_conVacio_lanzaCiudadNoValida() {
        assertThrows(CiudadNoValidaException.class, () -> new Ciudad(""));
    }

    @Test
    void constructor_conSoloEspacios_lanzaCiudadNoValida() {
        assertThrows(CiudadNoValidaException.class, () -> new Ciudad("   "));
    }

    @Test
    void constructor_conLongitudMaxima100_seConstruye() {
        Ciudad ciudad = new Ciudad("a".repeat(100));
        assertEquals(100, ciudad.valor().length());
    }

    @Test
    void constructor_conLongitud101_lanzaCiudadNoValida() {
        assertThrows(CiudadNoValidaException.class, () -> new Ciudad("a".repeat(101)));
    }
}

