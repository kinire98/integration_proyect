package com.kinire.proyectointegrador.client.exceptions;

public class FileDoesNotExistException extends RuntimeException {
    public FileDoesNotExistException(String message) {
        super(message);
    }
}
