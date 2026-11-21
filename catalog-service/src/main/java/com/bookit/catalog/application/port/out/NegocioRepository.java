package com.bookit.catalog.application.port.out;

import com.bookit.catalog.domain.Categoria;
import com.bookit.catalog.domain.Ciudad;
import com.bookit.catalog.domain.Negocio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NegocioRepository {
    Negocio guardar(Negocio negocio);
    Optional<Negocio> buscarPorId(UUID id);
    List<Negocio> buscarPorCiudadYCategoria(Ciudad ciudad, Categoria categoria);
}
