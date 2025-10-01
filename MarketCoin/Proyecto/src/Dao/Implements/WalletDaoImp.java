package Dao.Implements;

import Dao.WalletDao;
import Entities.CoinWallet;
import Entities.Wallet;
import Helpers.WalletHelper;
import Utilities.IO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class WalletDaoImp implements WalletDao {

    private final String url = "jdbc:mysql://localhost:3306/crypto";
    private final String user = "root";
    private final String password = "1036449931";



    @Override
    public void createWallet(long cedulaUsuario, String nombreBilletera) {

        String walletNumber = WalletHelper.generateWalletNumber(cedulaUsuario);
        IO.imp("🔧 Número de billetera generado: " + walletNumber);

        String sql = "INSERT INTO billetera (numero_billetera, cedula_usuario, nombre) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, walletNumber);
            ps.setLong(2, cedulaUsuario);
            ps.setString(3, nombreBilletera);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                IO.imp("✅ Billetera guardada exitosamente en la BD");
                IO.imp("📋 Datos guardados:");
                IO.imp("   - Número: " + walletNumber);
                IO.imp("   - Cédula: " + cedulaUsuario);
                IO.imp("   - Nombre: " + nombreBilletera);
            } else {
                System.out.println("❌ No se insertó ninguna fila en la BD");
            }

        } catch (SQLException e) {
            System.out.println("❌ Error SQL al crear billetera:");
            System.out.println("   - Código de error: " + e.getErrorCode());
            System.out.println("   - Mensaje: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Método para obtener todas las billeteras de un usuario
    @Override
    public List<Wallet> findAllByUser(long cedulaUsuario) {
        List<Wallet> wallets = new ArrayList<>();
        String sql = "SELECT numero_billetera, cedula_usuario, nombre FROM billetera WHERE cedula_usuario = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, cedulaUsuario);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Wallet wallet = new Wallet(
                            rs.getString("numero_billetera"),
                            rs.getLong("cedula_usuario"),
                            rs.getString("nombre")
                    );
                    wallets.add(wallet);
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al listar billeteras del usuario: " + e.getMessage());
        }

        return wallets;
    }

    // Método para verificar si una moneda existe
    public boolean existeMoneda(String simbolo) {
        String sql = "SELECT COUNT(*) FROM moneda WHERE simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, simbolo.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al verificar moneda: " + e.getMessage());
        }

        return false;
    }

    // Método mejorado para depositar
    @Override
    public boolean depositar(String numeroBilletera, String simboloMoneda, double cantidad) {
        // Primero verificar que la moneda exista
        if (!existeMoneda(simboloMoneda)) {
            System.out.println("❌ La moneda '" + simboloMoneda + "' no existe en el sistema");
            return false;
        }

        String sql = "INSERT INTO billetera_moneda (numero_billetera, id_moneda, cantidad) " +
                "VALUES (?, (SELECT id_moneda FROM moneda WHERE simbolo = ?), ?) " +
                "ON DUPLICATE KEY UPDATE cantidad = cantidad + ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroBilletera);
            ps.setString(2, simboloMoneda.toUpperCase());
            ps.setDouble(3, cantidad);
            ps.setDouble(4, cantidad);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(" Error al depositar: " + e.getMessage());
            return false;
        }
    }

    // En WalletDaoImp, agrega estos métodos:

    // Método para obtener el saldo de una moneda específica en una billetera
    public double obtenerSaldoMoneda(String numeroBilletera, String simboloMoneda) {
        String sql = "SELECT bm.cantidad FROM billetera_moneda bm " +
                "JOIN moneda m ON bm.id_moneda = m.id_moneda " +
                "WHERE bm.numero_billetera = ? AND m.simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, numeroBilletera);
            ps.setString(2, simboloMoneda.toUpperCase());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("cantidad");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener saldo: " + e.getMessage());
        }

        return 0.0;
    }

    // Método mejorado para retirar
    @Override
    public boolean retirar(String numeroBilletera, String simboloMoneda, double cantidad) {
        // Primero verificar que la moneda exista
        if (!existeMoneda(simboloMoneda)) {
            System.out.println("❌ La moneda '" + simboloMoneda + "' no existe en el sistema");
            return false;
        }

        // Verificar saldo suficiente
        double saldoActual = obtenerSaldoMoneda(numeroBilletera, simboloMoneda);
        if (saldoActual < cantidad) {
            System.out.println("❌ Saldo insuficiente. Tienes: " + saldoActual + " " + simboloMoneda);
            return false;
        }

        String sql = "UPDATE billetera_moneda bm " +
                "JOIN moneda m ON bm.id_moneda = m.id_moneda " +
                "SET bm.cantidad = bm.cantidad - ? " +
                "WHERE bm.numero_billetera = ? AND m.simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, cantidad);
            ps.setString(2, numeroBilletera);
            ps.setString(3, simboloMoneda.toUpperCase());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println(" Error al retirar: " + e.getMessage());
            return false;
        }
    }


    @Override
    public List<CoinWallet> getCoinsByWallet(String walletNumber) {
        List<CoinWallet> coins = new ArrayList<>();
        String sql = "SELECT m.simbolo, m.nombre, bm.cantidad " +
                "FROM billetera_moneda bm " +
                "JOIN moneda m ON bm.id_moneda = m.id_moneda " +
                "WHERE bm.numero_billetera = ? AND bm.cantidad > 0";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, walletNumber);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    CoinWallet coin = new CoinWallet(
                            rs.getString("simbolo"),
                            rs.getString("nombre"),
                            rs.getBigDecimal("cantidad")
                    );
                    coins.add(coin);
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener monedas: " + e.getMessage());
        }

        return coins;
    }

    @Override
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
            System.out.println(" Error al buscar billetera: " + e.getMessage());
        }

        return Optional.ofNullable(walletFound);
    }

    @Override
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
            System.out.println(" Error al verificar billetera: " + e.getMessage());
        }

        return false;
    }

}