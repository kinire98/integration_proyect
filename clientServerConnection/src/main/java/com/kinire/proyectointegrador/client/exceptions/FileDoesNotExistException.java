package com.kinire.proyectointegrador.client.exceptions;

/**
 * Excepción indicando que el archivo solicitado no existe
 */
public class FileDoesNotExistException extends RuntimeException {
    public FileDoesNotExistException(String message) {
        super(message);
    }
}
