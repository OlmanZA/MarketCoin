package Dao;

import Entities.CoinWallet;
import Entities.Wallet;
import java.util.List;
import java.util.Optional;

public interface WalletDao {
    void createWallet(long cedulaUsuario, String nombreBilletera);
    boolean depositar(String numeroBilletera, String simboloMoneda, double cantidad);
    boolean retirar(String numeroBilletera, String simboloMoneda, double cantidad);
    List<CoinWallet> getCoinsByWallet(String walletNumber);
    Optional<Wallet> findByUserCedula(long cedulaUsuario);
    boolean hasWallet(long cedulaUsuario);
    List<Wallet> findAllByUser(long cedulaUsuario);
    boolean existeMoneda(String simbolo);
    double obtenerSaldoMoneda(String numeroBilletera, String simboloMoneda);
}