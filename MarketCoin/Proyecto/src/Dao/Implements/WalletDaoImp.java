package Dao.Implements;

import Dao.WalletDao;
import Entities.Wallet;
import Helpers.WalletHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletDaoImp implements WalletDao {

    private final String url = "jdbc:mysql://localhost:3306/crypto";
    private final String user = "root";
    private final String password = "1021922910";

    @Override
    // crear billetera con nombre personalizado
    public void createWallet(long cedulaUsuario, String nombreBilletera) {

        String walletNumber = WalletHelper.generateWalletNumber(cedulaUsuario);
        String sql = "INSERT INTO billetera (numero_billetera, cedula_usuario, nombre_billetera) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, walletNumber);
            ps.setLong(2, cedulaUsuario);
            ps.setString(3, nombreBilletera);

            ps.executeUpdate();
            System.out.println(" Billetera '" + nombreBilletera + "' creada para usuario " + cedulaUsuario + ": " + walletNumber);

        } catch (SQLException e) {
            System.out.println(" Error al crear billetera: " + e.getMessage());
            e.printStackTrace();
        }
    }

    //encontrar cartera por numero
    @Override
    public Optional<Wallet> findByWalletNumber(String walletNumber) {
        String sql = "SELECT numero_billetera, cedula_usuario, nombre_billetera FROM billetera WHERE numero_billetera = ?";
        Wallet walletFound = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, walletNumber);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    walletFound = new Wallet(
                            rs.getString("numero_billetera"),
                            rs.getLong("cedula_usuario"),
                            rs.getString("nombre_billetera")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al buscar billetera: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.ofNullable(walletFound);
    }

    // Metodo para buscar billetera por cédula de usuario
    public Optional<Wallet> findByUserCedula(long cedulaUsuario) {
        String sql = "SELECT numero_billetera, cedula_usuario, nombre_billetera FROM billetera WHERE cedula_usuario = ?";
        Wallet walletFound = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedulaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    walletFound = new Wallet(
                            rs.getString("numero_billetera"),
                            rs.getLong("cedula_usuario"),
                            rs.getString("nombre_billetera")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al buscar billetera por usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.ofNullable(walletFound);
    }

    // Metodo para listar todas las billeteras de un usuario
    public List<Wallet> findAllByUser(long cedulaUsuario) {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT numero_billetera, cedula_usuario, nombre_billetera FROM billetera WHERE cedula_usuario = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedulaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    wallets.add(new Wallet(
                            rs.getString("numero_billetera"),
                            rs.getLong("cedula_usuario"),
                            rs.getString("nombre_billetera")
                    ));
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al listar billeteras del usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return wallets;
    }

    // Verificar si un usuario ya tiene billetera
    public boolean hasWallet(long cedulaUsuario) {
        String sql = "SELECT COUNT(*) FROM billetera WHERE cedula_usuario = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedulaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al verificar existencia de billetera: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Actualizar nombre de billetera
    public boolean updateWalletName(String walletNumber, String newName) {
        String sql = "UPDATE billetera SET nombre_billetera = ? WHERE numero_billetera = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, newName);
            ps.setString(2, walletNumber);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(" Nombre de billetera actualizado: " + newName);
                return true;
            } else {
                System.out.println(" Billetera no encontrada: " + walletNumber);
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error al actualizar nombre de billetera: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar billetera
    public boolean deleteWallet(String walletNumber) {
        String sql = "DELETE FROM billetera WHERE numero_billetera = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, walletNumber);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Billetera eliminada: " + walletNumber);
                return true;
            } else {
                System.out.println("Billetera no encontrada: " + walletNumber);
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error al eliminar billetera: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}