package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.CategoriaNoValidaException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CategoriaTest {

    @Test
    void quitaEspaciosAlPrincipioYAlFinal() {
        assertEquals("Peluquería", new Categoria("  Peluquería  ").valor());
    }

    @Test
    void constructor_conNulo_lanzaCategoriaNoValida() {
        assertThrows(CategoriaNoValidaException.class, () -> new Categoria(null));
    }

    @Test
    void constructor_conVacio_lanzaCategoriaNoValida() {
        assertThrows(CategoriaNoValidaException.class, () -> new Categoria(""));
    }

    @Test
    void constructor_conSoloEspacios_lanzaCategoriaNoValida() {
        assertThrows(CategoriaNoValidaException.class, () -> new Categoria("   "));
    }

    @Test
    void constructor_conLongitudMaxima50_seConstruye() {
        Categoria categoria = new Categoria("a".repeat(50));
        assertEquals(50, categoria.valor().length());
    }

    @Test
    void constructor_conLongitud51_lanzaCategoriaNoValida() {
        assertThrows(CategoriaNoValidaException.class, () -> new Categoria("a".repeat(51)));
    }
}

