package Helpers;

import Dao.UserDao;
import Dao.Implements.UserDaoImp;
import Entities.User;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class RegisterUser {
    private UserDao userDao;
    private Scanner scanner;

    public RegisterUser() {
        this.userDao = new UserDaoImp();
        this.scanner = new Scanner(System.in);
    }

    // MÉTODO PÚBLICO para registro - CORREGIDO
    public void registrarNuevoUsuario() {
        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");

        try {
            // 1. Cédula
            long cedula;
            while (true) {
                System.out.print("Cédula: ");
                if (scanner.hasNextLong()) {
                    cedula = scanner.nextLong();
                    scanner.nextLine(); // limpiar buffer

                    if (userDao.verify(cedula)) {
                        System.out.println("❌ Ya existe un usuario con esta cédula. Intente de nuevo.");
                    } else {
                        break; // ✅ cédula válida
                    }
                } else {
                    System.out.println("❌ Por favor, ingrese un número válido para la cédula.");
                    scanner.nextLine(); // limpiar entrada inválida
                }
            }

            // 2. Nombre
            String nombre;
            while (true) {
                System.out.print("Nombre completo: ");
                nombre = scanner.nextLine().trim();

                if (nombre.isEmpty()) {
                    System.out.println("❌ El nombre no puede estar vacío");
                } else if (nombre.length() > 100) {
                    System.out.println("❌ El nombre no puede exceder 100 caracteres");
                } else {
                    break;
                }
            }

            // 3. Email - CORREGIDO para usar el método correcto
            String email;
            while (true) {
                System.out.print("Email: ");
                email = scanner.nextLine().trim();

                if (!validarEmail(email)) {
                    System.out.println("❌ Formato de email inválido. Intente de nuevo.");
                    continue;
                }

                // CORRECCIÓN: Usar findByEmail que devuelve Optional<User>
                Optional<User> usuarioExistente = userDao.findByEmail(email);
                if (usuarioExistente.isPresent()) {
                    System.out.println("❌ Ya existe un usuario con este email. Intente de nuevo.");
                } else {
                    break; // ✅ email válido y único
                }
            }

            // 4. Contraseña
            String password;
            while (true) {
                System.out.print("Contraseña: ");
                password = scanner.nextLine().trim();

                if (password.length() < 4) {
                    System.out.println("❌ La contraseña debe tener al menos 4 caracteres");
                } else if (password.length() > 50) {
                    System.out.println("❌ La contraseña no puede exceder 50 caracteres");
                } else {
                    break;
                }
            }

            // 5. Fecha de nacimiento
            LocalDate fechaNac;
            while (true) {
                System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
                String fechaStr = scanner.nextLine().trim();

                if (!validarFecha(fechaStr)) {
                    System.out.println("❌ Formato de fecha inválido. Use YYYY-MM-DD");
                } else {
                    fechaNac = LocalDate.parse(fechaStr);
                    // Validación adicional de fecha razonable
                    if (fechaNac.isAfter(LocalDate.now())) {
                        System.out.println("❌ La fecha de nacimiento no puede ser futura");
                    } else if (fechaNac.isAfter(LocalDate.now().minusYears(12))) {
                        System.out.println("❌ Debe tener al menos 12 años");
                    } else {
                        break;
                    }
                }
            }

            // 6. País
            String pais;
            while (true) {
                System.out.print("País de nacimiento: ");
                pais = scanner.nextLine().trim();

                if (pais.isEmpty()) {
                    System.out.println("❌ El país no puede estar vacío");
                } else if (pais.length() > 50) {
                    System.out.println("❌ El país no puede exceder 50 caracteres");
                } else {
                    break;
                }
            }

            // Crear y guardar usuario
            User nuevoUsuario = new User(cedula, nombre, email, password, fechaNac, pais);
            userDao.insert(nuevoUsuario);

            // El mensaje de éxito ya lo maneja el DAO, pero podemos agregar confirmación
            System.out.println("✅ Usuario registrado exitosamente!");
            mostrarResumenRegistro(cedula, nombre, email, fechaNac, pais);

        } catch (Exception e) {
            System.out.println("❌ Error durante el registro: " + e.getMessage());
        }
    }

    // MÉTODO PÚBLICO para listar usuarios - CORREGIDO
    public void listarTodosLosUsuarios() {
        System.out.println("\n--- LISTA DE USUARIOS REGISTRADOS ---");
        var usuarios = userDao.findAll();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados");
        } else {
            System.out.println("Total de usuarios: " + usuarios.size());
            System.out.println("==========================================");

            for (int i = 0; i < usuarios.size(); i++) {
                User usuario = usuarios.get(i);
                System.out.println((i + 1) + ". " + usuario.getName() +
                        " - Cédula: " + usuario.getCedula() +
                        " - Email: " + usuario.getEmail());
            }
        }
    }

    // MÉTODO PÚBLICO para buscar por cédula - CORREGIDO
    public void buscarUsuarioPorCedula() {
        System.out.println("\n--- BUSCAR USUARIO POR CÉDULA ---");

        try {
            System.out.print("Ingrese la cédula: ");
            if (scanner.hasNextLong()) {
                long cedula = scanner.nextLong();
                scanner.nextLine();

                Optional<User> usuario = userDao.findByCedula(cedula);
                if (usuario.isPresent()) {
                    mostrarDatosUsuario(usuario.get());
                } else {
                    System.out.println("❌ No se encontró usuario con cédula: " + cedula);
                }
            } else {
                System.out.println("❌ Por favor, ingrese un número de cédula válido.");
                scanner.nextLine(); // limpiar entrada inválida
            }
        } catch (Exception e) {
            System.out.println("❌ Error al buscar usuario: " + e.getMessage());
        }
    }

    // MÉTODO PÚBLICO para eliminar usuario - CORREGIDO
    public boolean eliminarUsuario() {
        System.out.println("\n--- ELIMINAR USUARIO ---");

        try {
            System.out.print("Ingrese la cédula del usuario a eliminar: ");
            if (scanner.hasNextLong()) {
                long cedula = scanner.nextLong();
                scanner.nextLine();

                Optional<User> usuarioOpt = userDao.findByCedula(cedula);
                if (usuarioOpt.isEmpty()) {
                    System.out.println("❌ Usuario no encontrado");
                    return false;
                }

                System.out.println("Usuario a eliminar: ");
                mostrarDatosUsuario(usuarioOpt.get());

                System.out.print("¿Está seguro de eliminar este usuario? (s/n): ");
                String confirmacion = scanner.nextLine();

                if (confirmacion.equalsIgnoreCase("s")) {
                    boolean resultado = userDao.deleteUser(cedula);
                    if (resultado) {
                        System.out.println("✅ Usuario eliminado exitosamente");
                    } else {
                        System.out.println("❌ No se pudo eliminar el usuario");
                    }
                    return resultado;
                } else {
                    System.out.println("Eliminación cancelada");
                    return false;
                }
            } else {
                System.out.println("❌ Por favor, ingrese un número de cédula válido.");
                scanner.nextLine(); // limpiar entrada inválida
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al eliminar usuario: " + e.getMessage());
            return false;
        }
    }

    // MÉTODO ADICIONAL: Actualizar usuario
    public boolean actualizarUsuario() {
        System.out.println("\n--- ACTUALIZAR USUARIO ---");

        try {
            System.out.print("Ingrese la cédula del usuario a actualizar: ");
            if (scanner.hasNextLong()) {
                long cedula = scanner.nextLong();
                scanner.nextLine();

                Optional<User> usuarioOpt = userDao.findByCedula(cedula);
                if (usuarioOpt.isEmpty()) {
                    System.out.println("❌ Usuario no encontrado");
                    return false;
                }

                User usuarioExistente = usuarioOpt.get();
                System.out.println("Usuario encontrado:");
                mostrarDatosUsuario(usuarioExistente);

                // Solicitar nuevos datos
                System.out.println("\nIngrese los nuevos datos (deje vacío para mantener el actual):");

                // Nombre
                System.out.print("Nuevo nombre [" + usuarioExistente.getName() + "]: ");
                String nuevoNombre = scanner.nextLine().trim();
                if (nuevoNombre.isEmpty()) {
                    nuevoNombre = usuarioExistente.getName();
                }

                // Email
                String nuevoEmail;
                while (true) {
                    System.out.print("Nuevo email [" + usuarioExistente.getEmail() + "]: ");
                    nuevoEmail = scanner.nextLine().trim();
                    if (nuevoEmail.isEmpty()) {
                        nuevoEmail = usuarioExistente.getEmail();
                        break;
                    }
                    if (!validarEmail(nuevoEmail)) {
                        System.out.println("❌ Formato de email inválido.");
                        continue;
                    }
                    // Verificar si el email ya existe (excluyendo el usuario actual)
                    Optional<User> usuarioConEmail = userDao.findByEmail(nuevoEmail);
                    if (usuarioConEmail.isPresent() && usuarioConEmail.get().getCedula() != cedula) {
                        System.out.println("❌ Ya existe otro usuario con este email.");
                    } else {
                        break;
                    }
                }

                // Fecha de nacimiento
                LocalDate nuevaFechaNac;
                while (true) {
                    System.out.print("Nueva fecha nacimiento (YYYY-MM-DD) [" + usuarioExistente.getBirthDate() + "]: ");
                    String fechaStr = scanner.nextLine().trim();
                    if (fechaStr.isEmpty()) {
                        nuevaFechaNac = usuarioExistente.getBirthDate();
                        break;
                    }
                    if (!validarFecha(fechaStr)) {
                        System.out.println("❌ Formato de fecha inválido.");
                        continue;
                    }
                    nuevaFechaNac = LocalDate.parse(fechaStr);
                    if (nuevaFechaNac.isAfter(LocalDate.now())) {
                        System.out.println("❌ La fecha no puede ser futura.");
                    } else {
                        break;
                    }
                }

                // País
                System.out.print("Nuevo país [" + usuarioExistente.getCountry() + "]: ");
                String nuevoPais = scanner.nextLine().trim();
                if (nuevoPais.isEmpty()) {
                    nuevoPais = usuarioExistente.getCountry();
                }

                // Crear usuario actualizado
                User usuarioActualizado = new User(cedula, nuevoNombre, nuevoEmail,
                        usuarioExistente.getPassword(), // Mantener contraseña actual
                        nuevaFechaNac, nuevoPais);

                boolean resultado = userDao.updateUser(usuarioActualizado);
                if (resultado) {
                    System.out.println("✅ Usuario actualizado exitosamente");
                }
                return resultado;

            } else {
                System.out.println("❌ Por favor, ingrese un número de cédula válido.");
                scanner.nextLine();
                return false;
            }
        } catch (Exception e) {
            System.out.println("❌ Error al actualizar usuario: " + e.getMessage());
            return false;
        }
    }

    // Métodos de validación (privados) - MEJORADOS
    private boolean validarEmail(String email) {
        // Expresión regular mejorada para validación de email
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$") && email.length() <= 100;
    }

    private boolean validarFecha(String fecha) {
        try {
            LocalDate.parse(fecha);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void mostrarDatosUsuario(User usuario) {
        System.out.println("• Cédula: " + usuario.getCedula());
        System.out.println("• Nombre: " + usuario.getName());
        System.out.println("• Email: " + usuario.getEmail());
        System.out.println("• Fecha Nacimiento: " + usuario.getBirthDate());
        System.out.println("• País: " + usuario.getCountry());
        System.out.println("------------------------------------------");
    }

    private void mostrarResumenRegistro(long cedula, String nombre, String email, LocalDate fechaNac, String pais) {
        System.out.println("\nDatos registrados:");
        System.out.println("   • Cédula: " + cedula);
        System.out.println("   • Nombre: " + nombre);
        System.out.println("   • Email: " + email);
        System.out.println("   • Fecha Nacimiento: " + fechaNac);
        System.out.println("   • País: " + pais);
        System.out.println("==========================================");
    }

    // Método para cerrar recursos
    public void cerrarRecursos() {
        if (scanner != null) {
            scanner.close();
            System.out.println("Recursos liberados correctamente");
        }
    }
}