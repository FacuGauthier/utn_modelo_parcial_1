package com.agusneta.demo.excepciones;

public class VeterinarioInvalidoException extends RuntimeException {
    public VeterinarioInvalidoException(String message) {
        super(message);
    }
}
