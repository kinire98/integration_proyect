package com.kinire.proyectointegrador.client.exceptions;

/**
 * Excepción que indica que no se pudo completar la solicitud
 */
public class RequestNotFulfilledException extends RuntimeException {
    public RequestNotFulfilledException(String message) {
        super(message);
    }
}
