package Dao.Implements;

import Dao.UserDao;
import Entities.User;
import Utilities.IO;
import Helpers.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDaoImp implements UserDao {

    @Override
    public void insert(User usuario) {

        if (usuario == null) {
            IO.imp(" El usuario no puede ser nulo");
            return;
        }

        if (verify(usuario.getCedula())) {
            IO.imp(" Ya existe un usuario con cédula: " + usuario.getCedula());
            return;
        }

        String sql = "INSERT INTO usuario (cedula, nombre, correo, contraseña, fecha_nac, pais_nacimiento) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, usuario.getCedula());
            ps.setString(2, usuario.getName());
            ps.setString(3, usuario.getEmail());
            ps.setString(4, usuario.getPassword());
            ps.setDate(5, Date.valueOf(usuario.getBirthDate()));
            ps.setString(6, usuario.getCountry());

            ps.executeUpdate();
            IO.imp(" Usuario registrado correctamente: " + usuario.getName());

        } catch (SQLException e) {
            IO.imp(" Error al insertar usuario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public boolean verify(long cedula) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE cedula = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al verificar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public Optional<User> findByCedula(long cedula) {
        String sql = "SELECT cedula, nombre, correo, fecha_nac, pais_nacimiento FROM usuario WHERE cedula = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedula);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User usuario = new User(
                            rs.getLong("cedula"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            null, // NO devolver contraseña por seguridad
                            rs.getDate("fecha_nac").toLocalDate(),
                            rs.getString("pais_nacimiento")
                    );
                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {
            IO.imp(" Error al buscar usuario por cédula: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {
        String sql = "SELECT cedula, nombre, correo, fecha_nac, pais_nacimiento FROM usuario WHERE correo = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User usuario = new User(
                            rs.getLong("cedula"),
                            rs.getString("nombre"),
                            rs.getString("correo"),
                            null, // NO devolver contraseña por seguridad
                            rs.getDate("fecha_nac").toLocalDate(),
                            rs.getString("pais_nacimiento")
                    );
                    return Optional.of(usuario);
                }
            }

        } catch (SQLException e) {
            IO.imp(" Error al buscar usuario por email: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public boolean validateCredentials(String email, String password) {
        String sql = "SELECT COUNT(*) FROM usuario WHERE correo = ? AND contraseña = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            IO.imp(" Error al validar credenciales: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateUser(User usuario) {
        if (!verify(usuario.getCedula())) {
            IO.imp(" Usuario no encontrado con cédula: " + usuario.getCedula());
            return false;
        }

        String sql = "UPDATE usuario SET nombre = ?, correo = ?, fecha_nac = ?, pais_nacimiento = ? WHERE cedula = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario.getName());
            ps.setString(2, usuario.getEmail());
            ps.setDate(3, Date.valueOf(usuario.getBirthDate()));
            ps.setString(4, usuario.getCountry());
            ps.setLong(5, usuario.getCedula());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                IO.imp(" Usuario actualizado correctamente: " + usuario.getName());
                return true;
            } else {
                IO.imp(" No se pudo actualizar el usuario");
                return false;
            }

        } catch (SQLException e) {
            IO.imp(" Error al actualizar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean changePassword(long cedula, String oldPassword, String newPassword) {
        String verifySQL = "SELECT COUNT(*) FROM usuario WHERE cedula = ? AND contraseña = ?";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement verifyPs = conn.prepareStatement(verifySQL)) {
                verifyPs.setLong(1, cedula);
                verifyPs.setString(2, oldPassword);

                try (ResultSet rs = verifyPs.executeQuery()) {
                    if (!rs.next() || rs.getInt(1) == 0) {
                        IO.imp(" Contraseña actual incorrecta");
                        return false;
                    }
                }
            }

            String updateSQL = "UPDATE usuario SET contraseña = ? WHERE cedula = ?";
            try (PreparedStatement updatePs = conn.prepareStatement(updateSQL)) {
                updatePs.setString(1, newPassword);
                updatePs.setLong(2, cedula);

                int rowsAffected = updatePs.executeUpdate();

                if (rowsAffected > 0) {
                    IO.imp(" Contraseña actualizada correctamente");
                    return true;
                } else {
                    IO.imp(" No se pudo actualizar la contraseña");
                    return false;
                }
            }

        } catch (SQLException e) {
            IO.imp(" Error al cambiar contraseña: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<User> findAll() {
        List<User> usuarios = new ArrayList<>();
        String sql = "SELECT cedula, nombre, correo, fecha_nac, pais_nacimiento FROM usuario ORDER BY nombre";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(new User(
                        rs.getLong("cedula"),
                        rs.getString("nombre"),
                        rs.getString("correo"),
                        null, // NO devolver contraseñas
                        rs.getDate("fecha_nac").toLocalDate(),
                        rs.getString("pais_nacimiento")
                ));
            }

        } catch (SQLException e) {
            IO.imp(" Error al listar usuarios: " + e.getMessage());
            e.printStackTrace();
        }

        return usuarios;
    }

    public boolean deleteUser(long cedula) {
        if (!verify(cedula)) {
            IO.imp(" Usuario no encontrado con cédula: " + cedula);
            return false;
        }

        String sql = "DELETE FROM usuario WHERE cedula = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedula);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                IO.imp(" Usuario eliminado correctamente");
                return true;
            } else {
                IO.imp(" No se pudo eliminar el usuario");
                return false;
            }

        } catch (SQLException e) {
            IO.imp(" Error al eliminar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
