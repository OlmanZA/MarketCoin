package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Moneda;
import com.example.Crypto.Repository.Moneda_Repositorio;
import com.example.Crypto.Service.MonedaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceMoneda_impl implements MonedaService{

        @Autowired
        private Moneda_Repositorio monedaRepositorio;

        public Moneda crearMoneda(Moneda moneda)
        {
            // Verificar si el correo ya existe
            if (monedaRepositorio.existsBySimbolo(moneda.getSimbolo())) {
                throw new RuntimeException("La moneda ya está registrada");
            }

            if (monedaRepositorio.existsByNombre(moneda.getNombre())) {
                throw new RuntimeException("La moneda ya está registrada");
            }

            // Guardar el nuevo usuario
            return monedaRepositorio.save(moneda);
        }

        @Override
        public List<Moneda> listarMonedas() {
        return monedaRepositorio.findAll();
         }

}
