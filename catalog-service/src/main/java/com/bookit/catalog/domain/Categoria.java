package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.CategoriaNoValidaException;

public record Categoria(String valor) {

    // - Constantes
    private static final int MAX_LONGITUD = 50;
    public static final String MENSAJE_INVALIDA = "La categoría no puede ser nula, vacía ni superar 50 caracteres";

    public Categoria {
        if (valor == null) {
            throw new CategoriaNoValidaException(MENSAJE_INVALIDA);
        }
        valor = valor.strip();
        if (valor.isEmpty() || valor.length() > MAX_LONGITUD) {
            throw new CategoriaNoValidaException(MENSAJE_INVALIDA);
        }
    }
}

