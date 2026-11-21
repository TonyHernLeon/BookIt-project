package com.bookit.catalog.application.service;

import com.bookit.catalog.application.port.in.BuscarNegociosUseCase;
import com.bookit.catalog.application.port.in.ConsultarNegocioUseCase;
import com.bookit.catalog.application.port.in.RegistrarNegocioCommand;
import com.bookit.catalog.application.port.in.RegistrarNegocioUseCase;
import com.bookit.catalog.application.port.out.NegocioRepository;
import com.bookit.catalog.domain.Categoria;
import com.bookit.catalog.domain.Ciudad;
import com.bookit.catalog.domain.Negocio;
import com.bookit.catalog.domain.exception.NegocioNoEncontradoException;

import java.time.Clock;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class NegocioService implements RegistrarNegocioUseCase, ConsultarNegocioUseCase, BuscarNegociosUseCase {

    // - Constantes de mensajes de error
    private static final String MENSAJE_COMANDO_NULO = "El comando no puede ser nulo";
    private static final String MENSAJE_ID_NULO = "El id no puede ser nulo";
    private static final String MENSAJE_NEGOCIO_NO_ENCONTRADO = "No existe ningún negocio con ID: ";
    private static final String MENSAJE_CIUDAD_NULA = "La ciudad no puede ser nula";
    private static final String MENSAJE_CATEGORIA_NULA = "La categoría no puede ser nula";

    private final NegocioRepository repositorio;
    private final Clock clock;

    public NegocioService(NegocioRepository repositorio, Clock clock) {
        this.repositorio = repositorio;
        this.clock = clock;
    }

    @Override
    public Negocio registrar(RegistrarNegocioCommand comando) {
        Objects.requireNonNull(comando, MENSAJE_COMANDO_NULO);
        Categoria categoria = new Categoria(comando.categoria());
        Ciudad ciudad = new Ciudad(comando.ciudad());
        Negocio negocio = Negocio.crear(comando.nombre(), categoria, ciudad, clock.instant());
        return repositorio.guardar(negocio);
    }

    @Override
    public Negocio consultarPorId(UUID id) {
        Objects.requireNonNull(id, MENSAJE_ID_NULO);
        return repositorio.buscarPorId(id).orElseThrow(() -> new NegocioNoEncontradoException(MENSAJE_NEGOCIO_NO_ENCONTRADO + id.toString()));
    }

    @Override
    public List<Negocio> buscar(Ciudad ciudad, Categoria categoria) {
        Objects.requireNonNull(ciudad, MENSAJE_CIUDAD_NULA);
        Objects.requireNonNull(categoria, MENSAJE_CATEGORIA_NULA);
        return repositorio.buscarPorCiudadYCategoria(ciudad, categoria);
    }
}

