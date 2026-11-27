package com.bookit.catalog.application.port.in;

import com.bookit.catalog.domain.Negocio;

public interface RegistrarNegocioUseCase {
    Negocio registrar(RegistrarNegocioCommand comando);
}
