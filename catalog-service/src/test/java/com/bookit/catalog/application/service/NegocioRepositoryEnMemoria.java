package com.bookit.catalog.application.service;

import com.bookit.catalog.application.port.out.NegocioRepository;
import com.bookit.catalog.domain.Categoria;
import com.bookit.catalog.domain.Ciudad;
import com.bookit.catalog.domain.Negocio;

import java.util.*;

class NegocioRepositoryEnMemoria implements NegocioRepository {

    private final Map<UUID, Negocio> negocios = new LinkedHashMap<>();

    @Override
    public Negocio guardar(Negocio negocio) {
        negocios.put(negocio.id(), negocio);
        return negocio;
    }

    @Override
    public Optional<Negocio> buscarPorId(UUID id) {
        return Optional.ofNullable(negocios.get(id));
    }

    @Override
    public List<Negocio> buscarPorCiudadYCategoria(Ciudad ciudad, Categoria categoria) {
        return negocios.values().stream()
                .filter(negocio -> negocio.ciudad().equals(ciudad) && negocio.categoria().equals(categoria))
                .toList();
    }
}

