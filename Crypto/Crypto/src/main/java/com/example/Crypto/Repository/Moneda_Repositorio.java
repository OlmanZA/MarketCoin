package com.example.Crypto.Repository;

import com.example.Crypto.Entities.Moneda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Moneda_Repositorio extends JpaRepository<Moneda, Long>{
    boolean existsBySimbolo(String simbolo);
    boolean existsByNombre(String nombre);
}
