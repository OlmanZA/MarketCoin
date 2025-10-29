package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.BilleteraMoneda;
import com.example.Crypto.Repository.BilleteraMoneda_Repositorio;
import com.example.Crypto.Service.BilleteraMonedaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service

public class BilleteraMoneda_impl implements BilleteraMonedaService {

    private final BilleteraMoneda_Repositorio repository;

    public BilleteraMoneda_impl(BilleteraMoneda_Repositorio repository) {
        this.repository = repository;
    }

    @Override
    public BilleteraMoneda crearBilleteraMoneda(BilleteraMoneda billeteraMoneda) {
        return repository.save(billeteraMoneda);
    }

    @Override
    public Optional<BilleteraMoneda> obtenerBilleteraMoneda(Long billeteraId, Long monedaId) {
        return repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId);
    }

    @Override
    public List<BilleteraMoneda> listarBilleterasMonedas() {
        return repository.findAll();
    }

    @Override
    public BilleteraMoneda depositar(Long billeteraId, Long monedaId, double monto) {
        BilleteraMoneda bm = repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId)
                .orElseThrow(() -> new RuntimeException("Billetera-Moneda no encontrada"));

        // Convertir double a BigDecimal y sumar al saldo actual
        bm.setCantidad(bm.getCantidad().add(BigDecimal.valueOf(monto)));

        // Guardar cambios
        return repository.save(bm);
    }

    @Override
    public BilleteraMoneda retirar(Long billeteraId, Long monedaId, double monto) {
        Optional<BilleteraMoneda> optional = repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId);
        if (optional.isPresent()) {
            BilleteraMoneda bm = optional.get();
            BigDecimal cantidadARetirar = BigDecimal.valueOf(monto);

            if (bm.getCantidad().compareTo(cantidadARetirar) < 0) {
                throw new RuntimeException("Saldo insuficiente para retirar");
            }

            // Restar monto al saldo actual
            bm.setCantidad(bm.getCantidad().subtract(cantidadARetirar));
            return repository.save(bm);
        } else {
            throw new RuntimeException("Billetera_Moneda no encontrada");
        }
    }
}
