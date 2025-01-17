package com.kinire.proyectointegrador.client.exceptions;

public class RequestNotFulfilledException extends RuntimeException {
    public RequestNotFulfilledException(String message) {
        super(message);
    }
}
