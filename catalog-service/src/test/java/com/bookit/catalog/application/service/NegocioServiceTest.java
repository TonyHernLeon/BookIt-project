package com.bookit.catalog.application.service;

import com.bookit.catalog.application.port.in.RegistrarNegocioCommand;
import com.bookit.catalog.domain.Categoria;
import com.bookit.catalog.domain.Ciudad;
import com.bookit.catalog.domain.Negocio;
import com.bookit.catalog.domain.exception.NegocioNoEncontradoException;
import com.bookit.catalog.domain.exception.NombreNoValidoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NegocioServiceTest {

    private static final Clock RELOJ_FIJO = Clock.fixed(Instant.parse("2026-01-01T10:00:00Z"), ZoneOffset.UTC);

    private NegocioRepositoryEnMemoria repositorio;
    private NegocioService servicio;

    @BeforeEach
    void preparar() {
        repositorio = new NegocioRepositoryEnMemoria();
        servicio = new NegocioService(repositorio, RELOJ_FIJO);
    }

    @Test
    void registrar_conRelojFijo_guardaConFechaDelReloj() {
        RegistrarNegocioCommand command = new RegistrarNegocioCommand("Peluquería Pepe", "Peluquería", "Madrid");
        Negocio negocio = servicio.registrar(command);

        Instant fechaEsperada = Instant.parse("2026-01-01T10:00:00Z");
        assertEquals(fechaEsperada, negocio.fechaCreacion());
        assertEquals(fechaEsperada, repositorio.buscarPorId(negocio.id()).orElseThrow().fechaCreacion());
    }

    @Test
    void registrar_conNombreVacio_lanzaNombreNoValido() {
        RegistrarNegocioCommand command = new RegistrarNegocioCommand("", "Peluquería", "Madrid");
        assertThrows(NombreNoValidoException.class, () -> servicio.registrar(command));
        assertTrue(repositorio.buscarPorCiudadYCategoria(new Ciudad("Madrid"), new Categoria("Peluquería")).isEmpty());
    }

    @Test
    void consultarPorId_conIdConocido_devuelveNegocioGuardado() {
        Negocio negocio = Negocio.crear("Peluquería Pepe", new Categoria("Peluquería"), new Ciudad("Madrid"), RELOJ_FIJO.instant());
        Negocio negocioGuardado = repositorio.guardar(negocio);

        Negocio negocioConsultado = servicio.consultarPorId(negocioGuardado.id());

        assertEquals(negocioGuardado.id(), negocioConsultado.id());
        assertEquals(negocioGuardado.nombre(), negocioConsultado.nombre());
        assertEquals(negocioGuardado.categoria(), negocioConsultado.categoria());
        assertEquals(negocioGuardado.ciudad(), negocioConsultado.ciudad());
        assertEquals(negocioGuardado.fechaCreacion(), negocioConsultado.fechaCreacion());
    }

    @Test
    void consultarPorId_conIdDesconocido_lanzaNegocioNoEncontrado() {
        Ciudad ciudad = new Ciudad("Madrid");
        Categoria categoria = new Categoria("Peluquería");
        repositorio.guardar(Negocio.crear("Peluquería Ramón", categoria, ciudad, RELOJ_FIJO.instant()));
        UUID idDesconocido = UUID.randomUUID();

        assertThrows(NegocioNoEncontradoException.class, () -> servicio.consultarPorId(idDesconocido));
    }

    @Test
    void buscar_conVariosNegocios_devuelveSoloLosDeEsaCiudadYCategoria() {
        Negocio pepe = Negocio.crear("Peluquería Pepe", new Categoria("Peluquería"), new Ciudad("Madrid"), RELOJ_FIJO.instant());
        repositorio.guardar(pepe);
        repositorio.guardar(Negocio.crear("Peluquería Lola", new Categoria("Peluquería"), new Ciudad("Sevilla"), RELOJ_FIJO.instant()));
        repositorio.guardar(Negocio.crear("Clínica Juan", new Categoria("Clínica"), new Ciudad("Madrid"), RELOJ_FIJO.instant()));
        repositorio.guardar(Negocio.crear("Pista Norte", new Categoria("Pádel"), new Ciudad("Valencia"), RELOJ_FIJO.instant()));

        List<Negocio> resultado = servicio.buscar(new Ciudad("Madrid"), new Categoria("Peluquería"));

        assertEquals(1, resultado.size());
        Negocio encontrado = resultado.get(0);
        assertEquals(pepe.id(), encontrado.id());
        assertEquals(pepe.nombre(), encontrado.nombre());
        assertEquals(pepe.ciudad(), encontrado.ciudad());
        assertEquals(pepe.categoria(), encontrado.categoria());
    }

    @Test
    void buscar_sinCoincidencias_devuelveListaVacia() {
        repositorio.guardar(Negocio.crear("Peluquería Pepe", new Categoria("Peluquería"), new Ciudad("Madrid"), RELOJ_FIJO.instant()));

        List<Negocio> resultado = servicio.buscar(new Ciudad("Sevilla"), new Categoria("Clínica"));

        assertTrue(resultado.isEmpty());
    }
}

