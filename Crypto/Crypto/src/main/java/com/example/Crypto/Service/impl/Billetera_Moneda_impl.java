package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Billetera_Moneda;
import com.example.Crypto.Repository.Billetera_Moneda_Repositorio;
import com.example.Crypto.Service.Billetera_MonedaService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service

public class Billetera_Moneda_impl implements Billetera_MonedaService {

    private final Billetera_Moneda_Repositorio repository;

    public Billetera_Moneda_impl(Billetera_Moneda_Repositorio repository) {
        this.repository = repository;
    }

    @Override
    public Billetera_Moneda crearBilleteraMoneda(Billetera_Moneda billeteraMoneda) {
        return repository.save(billeteraMoneda);
    }

    @Override
    public Optional<Billetera_Moneda> obtenerBilleteraMoneda(Long billeteraId, Long monedaId) {
        return repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId);
    }

    @Override
    public List<Billetera_Moneda> listarBilleterasMonedas() {
        return repository.findAll();
    }

    @Override
    public Billetera_Moneda depositar(Long billeteraId, Long monedaId, double monto) {
        Billetera_Moneda bm = repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId)
                .orElseThrow(() -> new RuntimeException("Billetera-Moneda no encontrada"));

        // Convertir double a BigDecimal y sumar al saldo actual
        bm.setCantidad(bm.getCantidad().add(BigDecimal.valueOf(monto)));

        // Guardar cambios
        return repository.save(bm);
    }

    @Override
    public Billetera_Moneda retirar(Long billeteraId, Long monedaId, double monto) {
        Optional<Billetera_Moneda> optional = repository.findByBilleteraNumeroBilleteraAndMonedaIdMoneda(billeteraId, monedaId);
        if (optional.isPresent()) {
            Billetera_Moneda bm = optional.get();
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
