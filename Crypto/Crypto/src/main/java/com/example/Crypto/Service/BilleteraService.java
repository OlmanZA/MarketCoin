package com.example.Crypto.Service;

import com.example.Crypto.Entities.Billetera;
import java.util.List;

public interface BilleteraService {
    Billetera crearBilletera(Long cedulaUsuario, Billetera billetera);
    List<Billetera> obtenerBilleterasPorUsuario(Long cedulaUsuario);
}