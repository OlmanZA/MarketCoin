package Helpers;

import java.util.Random;

public class WalletHelper {

    public static String generateWalletNumber(long cedulaUsuario) {
        Random rand = new Random();
        int random = rand.nextInt(9000) + 1000; // número aleatorio de 4 cifras
        return "WAL-" + cedulaUsuario + "-" + random;
    }
}
