package com.bookit.catalog.application.port.in;

import com.bookit.catalog.domain.Negocio;

import java.util.UUID;

public interface ConsultarNegocioUseCase {
    Negocio consultarPorId(UUID id);
}
