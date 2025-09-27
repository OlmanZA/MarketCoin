import Helpers.LoginUser;
import Utilities.IO;
import Helpers.RegisterUser;

public class Menu {
    public static void menu() {
        int op = -1;
        boolean logedIn = false;

        RegisterUser registerUser = new RegisterUser();
        LoginUser loginUser = new LoginUser();

        do {
            // Verificar estado de login
            if (!logedIn) {
                IO.imp("\n1. Iniciar Sesión");
                IO.imp("2. Registrarse");
                IO.imp("0. Salir");

                op = IO.leaInt("Elija una opción");

                switch (op) {
                    case 1 -> {
                        if (loginUser.iniciarSesion()) {
                            logedIn = true;
                        } else {
                            IO.imp("❌ Login fallido");
                        }
                    }
                    case 2 -> registerUser.registrarNuevoUsuario();
                    case 0 -> IO.imp("Saliendo...");
                    default -> IO.imp("Opción inválida");
                }
            } else {
                IO.imp("Usuario: " + loginUser.getUsuarioLogueado().getName());

                IO.imp("1. Depositar");
                IO.imp("2. Retirar");
                IO.imp("3. Ver monedas por simbolo");
                IO.imp("4. Ver billetera");
                IO.imp("5. Transformar a dinero");
                IO.imp("6. Ver mi perfil");
                IO.imp("9. Cerrar sesión");
                IO.imp("0. Salir");

                op = IO.leaInt("Elija una opción");

                switch (op) {

                    case 6 -> loginUser.mostrarPerfil();
                    case 9 -> {
                        loginUser.cerrarSesion();
                        logedIn = false;
                        IO.imp("✅ Sesión cerrada.");
                    }
                    case 0 -> IO.imp("Saliendo...");
                    default -> IO.imp("Opción inválida");
                }
            }
        } while (op != 0);
    }
}