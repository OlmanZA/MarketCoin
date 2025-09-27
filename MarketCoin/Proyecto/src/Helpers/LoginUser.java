package Helpers;

import Dao.Implements.UserDaoImp;
import Entities.User;
import Utilities.IO;

import java.util.Optional;
import java.util.Scanner;

public class LoginUser {
    private UserDaoImp userDao;
    private Scanner scanner;
    private User usuarioLogueado;

    public LoginUser() {
        this.userDao = new UserDaoImp();
        this.scanner = new Scanner(System.in);
        this.usuarioLogueado = null;
    }

    // MÉTODO PRINCIPAL DE LOGIN
    public boolean iniciarSesion() {
        System.out.println("\n=== INICIAR SESIÓN ===");

        try {

            System.out.print("Email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Contraseña: ");
            String password = scanner.nextLine().trim();

            // Validar credenciales
            if (validarCredenciales(email, password)) {
                IO.imp("✅ ¡Inicio de sesión exitoso!\n");
                IO.imp("Bienvenido/a: " + usuarioLogueado.getName() + "\n");
                return true;
            }


        } catch (Exception e) {
            IO.imp("❌ Error durante el inicio de sesión: " + e.getMessage());
        }

        return false;
    }

    // VALIDAR CREDENCIALES
    private boolean validarCredenciales(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            IO.imp("❌ Email y contraseña son obligatorios");
            return false;
        }

        // Validar usando el método del DAO
        boolean credencialesValidas = userDao.validateCredentials(email, password);

        if (credencialesValidas) {
            // Obtener los datos del usuario
            Optional<User> usuarioOpt = userDao.findByEmail(email);
            if (usuarioOpt.isPresent()) {
                this.usuarioLogueado = usuarioOpt.get();
                return true;
            }
        }

        return false;
    }

    // CERRAR SESIÓN
    public void cerrarSesion() {
        if (usuarioLogueado != null) {
            IO.imp("👋 Sesión cerrada para: " + usuarioLogueado.getName());
            this.usuarioLogueado = null;
        } else {
            IO.imp("ℹ️ No hay sesión activa");
        }
    }

    // VERIFICAR SI HAY USUARIO LOGUEADO
    public boolean hayUsuarioLogueado() {
        return usuarioLogueado != null;
    }

    // OBTENER USUARIO ACTUAL
    public User getUsuarioLogueado() {
        return usuarioLogueado;
    }

    // CAMBIAR CONTRASEÑA
    public boolean cambiarContraseña() {
        if (!hayUsuarioLogueado()) {
            IO.imp("❌ Debe iniciar sesión primero");
            return false;
        }

        IO.imp("\n--- CAMBIAR CONTRASEÑA ---");
        IO.imp("Usuario: " + usuarioLogueado.getName());

        try {
            // Verificar contraseña actual
            System.out.print("Contraseña actual: ");
            String contraseñaActual = scanner.nextLine().trim();

            System.out.print("Nueva contraseña: ");
            String nuevaContraseña = scanner.nextLine().trim();

            System.out.print("Confirmar nueva contraseña: ");
            String confirmarContraseña = scanner.nextLine().trim();

            // Validaciones
            if (nuevaContraseña.length() < 4) {
                IO.imp("❌ La nueva contraseña debe tener al menos 4 caracteres");
                return false;
            }

            if (!nuevaContraseña.equals(confirmarContraseña)) {
                IO.imp("❌ Las contraseñas no coinciden");
                return false;
            }

            // Cambiar contraseña usando el DAO
            boolean resultado = userDao.changePassword(
                    usuarioLogueado.getCedula(),
                    contraseñaActual,
                    nuevaContraseña
            );

            if (resultado) {
                IO.imp("✅ Contraseña cambiada exitosamente");
            }

            return resultado;

        } catch (Exception e) {
            IO.imp("❌ Error al cambiar contraseña: " + e.getMessage());
            return false;
        }
    }

    // RECUPERAR CONTRASEÑA (por cédula y email)
    public void recuperarContraseña() {
        IO.imp("\n--- RECUPERAR CONTRASEÑA ---");

        try {
            System.out.print("Ingrese su cédula: ");
            long cedula = scanner.nextLong();
            scanner.nextLine(); // Limpiar buffer

            System.out.print("Ingrese su email: ");
            String email = scanner.nextLine().trim();

            // Verificar que exista el usuario con esa cédula y email
            Optional<User> usuarioOpt = userDao.findByCedula(cedula);

            if (usuarioOpt.isPresent()) {
                User usuario = usuarioOpt.get();

                if (usuario.getEmail().equalsIgnoreCase(email)) {
                    IO.imp("\n✅ Datos verificados correctamente");
                    IO.imp("Usuario: " + usuario.getName());
                    IO.imp("Email: " + usuario.getEmail());
                    IO.imp("\nPor seguridad, contacte al administrador para restablecer su contraseña.");
                } else {
                    IO.imp("❌ El email no coincide con la cédula proporcionada");
                }
            } else {
                IO.imp("❌ No se encontró usuario con la cédula proporcionada");
            }

        } catch (Exception e) {
            IO.imp("❌ Error en recuperación: " + e.getMessage());
            scanner.nextLine(); // Limpiar buffer en caso de error
        }
    }

    // MOSTRAR PERFIL DEL USUARIO
    public void mostrarPerfil() {
        if (!hayUsuarioLogueado()) {
            IO.imp("❌ Debe iniciar sesión primero");
            return;
        }

        IO.imp("\n--- MI PERFIL ---");
        IO.imp("• Cédula: " + usuarioLogueado.getCedula());
        IO.imp("• Nombre: " + usuarioLogueado.getName());
        IO.imp("• Email: " + usuarioLogueado.getEmail());
        IO.imp("• Fecha Nacimiento: " + usuarioLogueado.getBirthDate());
        IO.imp("• País: " + usuarioLogueado.getCountry());
        IO.imp("----------------------------");
    }

    // MENÚ DE USUARIO LOGUEADO
    public void menuUsuario() {
        if (!hayUsuarioLogueado()) {
            IO.imp("❌ Debe iniciar sesión primero");
            return;
        }

        boolean salir = false;

        while (!salir) {
            IO.imp("\n=== MENÚ USUARIO ===");
            IO.imp("Usuario actual: " + usuarioLogueado.getName());
            IO.imp("1. Ver mi perfil");
            IO.imp("2. Cambiar contraseña");
            IO.imp("3. Cerrar sesión");
            IO.imp("0. Volver al menú principal");

            System.out.print("Seleccione una opción: ");
            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    mostrarPerfil();
                    break;

                case "2":
                    cambiarContraseña();
                    break;

                case "3":
                    cerrarSesion();
                    salir = true;
                    break;

                case "0":
                    salir = true;
                    break;

                default:
                    IO.imp("❌ Opción inválida");
            }
        }
    }

    // Liberar recursos
    public void cerrarRecursos() {
        if (scanner != null) {
            scanner.close();
        }
    }
}