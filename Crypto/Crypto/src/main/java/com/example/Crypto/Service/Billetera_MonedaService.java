package com.example.Crypto.Service;

import com.example.Crypto.Entities.Billetera_Moneda;
import java.util.List;
import java.util.Optional;

;

public interface Billetera_MonedaService {



    Billetera_Moneda crearBilleteraMoneda(Billetera_Moneda billeteraMoneda);

    Optional<Billetera_Moneda> obtenerBilleteraMoneda(Long billeteraId, Long monedaId);

    List<Billetera_Moneda> listarBilleterasMonedas();

    Billetera_Moneda depositar(Long billeteraId, Long monedaId, double monto);

    Billetera_Moneda retirar(Long billeteraId, Long monedaId, double monto);





}
