package com.bookit.catalog.application.port.in;

import com.bookit.catalog.domain.Categoria;
import com.bookit.catalog.domain.Ciudad;
import com.bookit.catalog.domain.Negocio;

import java.util.List;

public interface BuscarNegociosUseCase {
    List<Negocio> buscar(Ciudad ciudad, Categoria categoria);
}
