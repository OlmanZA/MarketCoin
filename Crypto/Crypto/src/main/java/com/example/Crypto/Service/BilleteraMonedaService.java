package com.example.Crypto.Service;

import com.example.Crypto.Entities.BilleteraMoneda;
import java.util.List;
import java.util.Optional;

;

public interface BilleteraMonedaService {



    BilleteraMoneda crearBilleteraMoneda(BilleteraMoneda billeteraMoneda);

    Optional<BilleteraMoneda> obtenerBilleteraMoneda(Long billeteraId, Long monedaId);

    List<BilleteraMoneda> listarBilleterasMonedas();

    BilleteraMoneda depositar(Long billeteraId, Long monedaId, double monto);

    BilleteraMoneda retirar(Long billeteraId, Long monedaId, double monto);





}
