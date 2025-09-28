import Helpers.LoginUser;
import Utilities.IO;
import Helpers.RegisterUser;
import Helpers.WalletHelper;
import Entities.User;

public class Menu {
    public static void menu() {
        int op = -1;
        boolean logedIn = false;

        RegisterUser registerUser = new RegisterUser();
        LoginUser loginUser = new LoginUser();

        do {
            if (!logedIn) {
                IO.imp("\n=== CRYPTOCOIN ===");
                IO.imp("1. Iniciar Sesión");
                IO.imp("2. Registrarse");
                IO.imp("0. Salir");

                op = IO.leaInt("Elija una opción: ");

                switch (op) {
                    case 1 -> {
                        if (loginUser.iniciarSesion()) {
                            logedIn = true;
                            IO.imp("✅ ¡Bienvenido!");
                        } else {
                            IO.imp("❌ Login fallido");
                        }
                    }
                    case 2 -> registerUser.registrarNuevoUsuario();
                    case 0 -> IO.imp("👋 ¡Hasta pronto!");
                    default -> IO.imp("❌ Opción inválida");
                }
            } else {
                User usuario = loginUser.getUsuarioLogueado();
                IO.imp("\n=== MENÚ PRINCIPAL ===");
                IO.imp("Usuario: " + usuario.getName());

                IO.imp("1. Crear billetera");
                IO.imp("2. Depositar");
                IO.imp("3. Retirar");
                IO.imp("4. Ver billetera");
                IO.imp("9. Cerrar sesión");
                IO.imp("0. Salir");

                op = IO.leaInt("Elija una opción: ");

                switch (op) {
                    case 1 -> WalletHelper.crearBilletera(usuario);
                    case 2 -> WalletHelper.depositar(usuario);
                    case 3 -> WalletHelper.retirar(usuario);
                    case 4 -> WalletHelper.verBilletera(usuario);
                    case 9 -> {
                        loginUser.cerrarSesion();
                        logedIn = false;
                        IO.imp("🔒 Sesión cerrada");
                    }
                    case 0 -> {
                        IO.imp("👋 ¡Hasta pronto!");
                        return;
                    }
                    default -> IO.imp("❌ Opción inválida");
                }
            }
        } while (op != 0);
    }
}