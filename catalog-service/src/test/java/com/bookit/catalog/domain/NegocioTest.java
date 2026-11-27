package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.NombreNoValidoException;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    void crear_conNombreNulo_lanzaNombreNoValido() {
        assertThrows(NombreNoValidoException.class, () -> Negocio.crear(
                null,
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z")));
    }

    @Test
    void crear_conNombreVacio_lanzaNombreNoValido() {
        assertThrows(NombreNoValidoException.class, () -> Negocio.crear(
                "",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z")));
    }

    @Test
    void crear_conNombreSoloEspacios_lanzaNombreNoValido() {
        assertThrows(NombreNoValidoException.class, () -> Negocio.crear(
                "   ",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z")));
    }

    @Test
    void crear_conNombreDe150Caracteres_seCrea() {
        String nombre = "a".repeat(150);
        Negocio negocio = Negocio.crear(
                nombre,
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z"));

        assertEquals(nombre, negocio.nombre());
    }

    @Test
    void crear_conNombreDe151Caracteres_lanzaNombreNoValido() {
        String nombre = "a".repeat(151);
        assertThrows(NombreNoValidoException.class, () -> Negocio.crear(
                nombre,
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z")));
    }

    @Test
    void crear_dosLlamadasConLosMismosDatos_generanIdsDistintos() {
        Negocio negocio1 = Negocio.crear(
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z"));

        Negocio negocio2 = Negocio.crear(
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z"));

        assertNotEquals(negocio1.id(), negocio2.id());
    }

    @Test
    void reconstruir_conIdDado_conservaElId() {
        UUID id = UUID.randomUUID();
        Negocio negocio = Negocio.reconstruir(
                id,
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z"));

        assertEquals(id, negocio.id());
    }

    @Test
    void reconstruir_conIdNulo_lanzaNullPointer() {
        assertThrows(NullPointerException.class, () -> Negocio.reconstruir(
                null,
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                Instant.parse("2026-01-01T10:00:00Z")));
    }

    @Test
    void crear_conFechaNula_lanzaNullPointer() {
        assertThrows(NullPointerException.class, () -> Negocio.crear(
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                null));
    }

    @Test
    void reconstruir_conFechaNula_lanzaNullPointer() {
        assertThrows(NullPointerException.class, () -> Negocio.reconstruir(
                UUID.randomUUID(),
                "Peluquería Ana",
                new Categoria("Belleza"),
                new Ciudad("Sevilla"),
                null));
    }
}

