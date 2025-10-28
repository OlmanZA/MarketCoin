package com.example.Crypto.Service;

import com.example.Crypto.Entities.Billetera;
import java.util.List;

public interface BilleteraService {
    Billetera crearBilletera(Long cedulaUsuario, Billetera billetera);  // Cambiado a 2 parámetros
    List<Billetera> obtenerBilleterasPorUsuario(Long cedulaUsuario);
}