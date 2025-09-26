package Dao;

import Entities.Coin;

import java.util.List;
import java.util.Optional;

public interface CoinDao {
    void insert(Coin coin);
    List<Coin> findAll();
    Optional<Coin> findBySymbol(String symbol);// ENCONTRAR MONEDA POR SIMBOLO
    boolean deposit(String symbol, double amount);  // DEPOSITAR
    boolean withdraw(String symbol, double amount); // RETIRAR
    double getBalance(String symbol);               // OBTENER BALANCE
}