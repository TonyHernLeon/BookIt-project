package com.bookit.catalog.domain;

import com.bookit.catalog.domain.exception.CategoriaNoValidaException;
import com.bookit.catalog.domain.exception.CiudadNoValidaException;
import com.bookit.catalog.domain.exception.NombreNoValidoException;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Negocio {

    private static final int MAX_NOMBRE = 150;
    private static final String MENSAJE_NOMBRE_INVALIDO = "El nombre no puede ser nulo, vacío ni superar 150 caracteres";
    private static final String MENSAJE_FECHA_NULA = "La fecha de creación del negocio no puede ser nula";
    private static final String MENSAJE_ID_NULO = "El id del negocio no puede ser nulo";

    private final UUID id;
    private final String nombre;
    private final Categoria categoria;
    private final Ciudad ciudad;
    private final Instant fechaCreacion;

    private Negocio(UUID id, String nombre, Categoria categoria, Ciudad ciudad, Instant fechaCreacion) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.ciudad = ciudad;
        this.fechaCreacion = fechaCreacion;
    }

    public static Negocio crear(String nombre, Categoria categoria, Ciudad ciudad, Instant fechaCreacion) {
        String nombreNormalizado = normalizarNombre(nombre);
        validar(categoria, ciudad, fechaCreacion);
        return new Negocio(UUID.randomUUID(), nombreNormalizado, categoria, ciudad, fechaCreacion);
    }

    public static Negocio reconstruir(UUID id, String nombre, Categoria categoria, Ciudad ciudad, Instant fechaCreacion) {
        Objects.requireNonNull(id, MENSAJE_ID_NULO);
        String nombreNormalizado = normalizarNombre(nombre);
        validar(categoria, ciudad, fechaCreacion);
        return new Negocio(id, nombreNormalizado, categoria, ciudad, fechaCreacion);
    }

    private static String normalizarNombre(String nombre) {
        if (nombre == null) {
            throw new NombreNoValidoException(MENSAJE_NOMBRE_INVALIDO);
        }
        String normalizado = nombre.strip();
        if (normalizado.isEmpty() || normalizado.length() > MAX_NOMBRE) {
            throw new NombreNoValidoException(MENSAJE_NOMBRE_INVALIDO);
        }
        return normalizado;
    }

    private static void validar(Categoria categoria, Ciudad ciudad, Instant fechaCreacion) {
        if (categoria == null) {
            throw new CategoriaNoValidaException(Categoria.MENSAJE_INVALIDA);
        }
        if (ciudad == null) {
            throw new CiudadNoValidaException(Ciudad.MENSAJE_INVALIDA);
        }
        Objects.requireNonNull(fechaCreacion, MENSAJE_FECHA_NULA);
    }

    public UUID id() {
        return id;
    }

    public String nombre() {
        return nombre;
    }

    public Categoria categoria() {
        return categoria;
    }

    public Ciudad ciudad() {
        return ciudad;
    }

    public Instant fechaCreacion() {
        return fechaCreacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Negocio otro)) {
            return false;
        }
        return id.equals(otro.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Negocio{id=" + id + ", nombre='" + nombre + "'}";
    }
}

