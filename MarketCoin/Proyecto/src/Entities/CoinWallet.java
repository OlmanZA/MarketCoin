package Entities;

import java.math.BigDecimal;

public class CoinWallet {
    private String simbolo;
    private String nombre;
    private BigDecimal cantidad;

    public CoinWallet(String simbolo, String nombre, BigDecimal cantidad) {
        this.simbolo = simbolo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    // Getters
    public String getSimbolo() {
        return simbolo;
    }

    public String getNombre() {
        return nombre;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    @Override
    public String toString() {
        return String.format("%.8f %s (%s)", cantidad, simbolo, nombre);
    }
}