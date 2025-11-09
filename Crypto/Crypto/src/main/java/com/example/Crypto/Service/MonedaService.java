package com.example.Crypto.Service;

import com.example.Crypto.Entities.Moneda;

import java.util.List;

public interface MonedaService {

    Moneda crearMoneda(Moneda moneda);
    List<Moneda> listarMonedas();
}
