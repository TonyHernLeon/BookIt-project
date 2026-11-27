package com.bookit.catalog.domain.exception;

public abstract class DatoInvalidoException extends DominioException {

    protected DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
