package com.agusneta.demo.excepciones;

public class TurnoInvalidoException extends RuntimeException {
    public TurnoInvalidoException(String message) {
        super(message);
    }
}
