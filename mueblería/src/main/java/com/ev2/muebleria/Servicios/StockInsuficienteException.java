package com.ev2.muebleria.Servicios;

public class StockInsuficienteException extends RuntimeException {
    public StockInsuficienteException() { super(); }
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
    public StockInsuficienteException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }
}
