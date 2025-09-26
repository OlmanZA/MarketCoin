package Dao;

import Entities.Wallet;
import java.util.List;
import java.util.Optional;

public interface WalletDao {

    //CREAR BILLETERA
    public void createWallet(long cedulaUsuario, String nombreBilletera);

    //ENCONTRAR BILLETERA POR NUMERO
    Optional<Wallet> findByWalletNumber(String walletNumber);

    //ACTUALIZAR BILLETERA
    public boolean updateWalletName(String walletNumber, String newName);

    // Métodos adicionales útiles
    Optional<Wallet> findByUserCedula(long cedulaUsuario);

    //MOSTRAR TODAS LAS BILLETERAS DEL USUARIO
    List<Wallet> findAllByUser(long cedulaUsuario);

    //BORRAR BILLETERA
    public boolean deleteWallet(String walletNumber);



    //VERIFICAR SI USUARIO TIENE CARTERA
    boolean hasWallet(long cedulaUsuario);

}