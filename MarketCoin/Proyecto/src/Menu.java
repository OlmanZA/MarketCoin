import Utilities.IO;
import Helpers.RegisterUser;

public class Menu {
    public static void menu() {
        int op = -1;
        boolean logedIn = false;

        RegisterUser registerUser = new RegisterUser();

        do {
            if (logedIn == false) {
                IO.imp("\t\tBienvenido a CryptoCoin\n\n" +
                        "\tElija una Opción\n" +
                        "1-. Iniciar Sesión\n" +
                        "2-. Registrarse\n" +
                        "3-. Crytos en tendencia\n" +
                        "0-. Cerrar el programa\n");

                op = IO.leaInt("Elija una opción");
            } else {
                // menú alternativo si se loggea
            }

            switch (op) {
                case 1 -> {
                }
                case 2 -> registerUser.registrarNuevoUsuario();
                case 3 -> {
                }
            }

        } while (op != 0);
    }
}
