package com.kinire.proyectointegrador.client.exceptions;

/**
 * Excepción de error desconocido
 */
public class UnknownException extends RuntimeException {
    public UnknownException(String message) {
        super(message);
    }
}
