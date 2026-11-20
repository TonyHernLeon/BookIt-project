package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.CiudadNoValidaException;

public record Ciudad(String valor) {

    private static final int MAX_LONGITUD = 100;
    public static final String MENSAJE_INVALIDA = "La ciudad no puede ser nula, vacía ni superar 100 caracteres";

    public Ciudad {
        if (valor == null) {
            throw new CiudadNoValidaException(MENSAJE_INVALIDA);
        }
        valor = valor.strip();
        if (valor.isEmpty() || valor.length() > MAX_LONGITUD) {
            throw new CiudadNoValidaException(MENSAJE_INVALIDA);
        }
    }
}

