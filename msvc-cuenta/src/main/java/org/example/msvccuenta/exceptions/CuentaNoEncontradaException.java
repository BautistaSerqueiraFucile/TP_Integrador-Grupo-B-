package org.example.msvccuenta.exceptions;

public class CuentaNoEncontradaException extends  RuntimeException{
    public CuentaNoEncontradaException(String message) {
        super(message);
    }
}
