package Dao.Implements;

import Dao.CoinDao;
import Entities.Coin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CoinDaoImp implements CoinDao {

    private final String url = "jdbc:mysql://localhost:3306/crypto";
    private final String user = "root";
    private final String password = "1021922910";

    @Override
    public void insert(Coin coin) {
        String sql = "INSERT INTO moneda (nombre, simbolo, cantidad) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coin.getNombre());
            ps.setString(2, coin.getSimbolo());
            ps.setDouble(3, coin.getCantidad() != null ? coin.getCantidad() : 0.0);
            ps.executeUpdate();

            System.out.println("✅ Moneda registrada: " + coin.getNombre() + " (" + coin.getSimbolo() + ")");

        } catch (SQLException e) {
            System.out.println("❌ Error al insertar moneda: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Coin> findAll() {
        List<Coin> coins = new ArrayList<>();
        String sql = "SELECT id_moneda, nombre, simbolo, cantidad FROM moneda ORDER BY nombre";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                coins.add(new Coin(
                        rs.getInt("id_moneda"),
                        rs.getString("nombre"),
                        rs.getString("simbolo"),
                        rs.getDouble("cantidad")
                ));
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al listar monedas: " + e.getMessage());
            e.printStackTrace();
        }

        return coins;
    }

    @Override
    public Optional<Coin> findBySymbol(String symbol) {
        String sql = "SELECT id_moneda, nombre, simbolo, cantidad FROM moneda WHERE simbolo = ?";
        Coin coin = null;

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, symbol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    coin = new Coin(
                            rs.getInt("id_moneda"),
                            rs.getString("nombre"),
                            rs.getString("simbolo"),
                            rs.getDouble("cantidad")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al buscar moneda por símbolo: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.ofNullable(coin);
    }

    @Override
    public boolean deposit(String symbol, double amount) {
        if (amount <= 0) {
            System.out.println(" La cantidad debe ser mayor a cero");
            return false;
        }

        // Verificar que la moneda existe
        if (findBySymbol(symbol).isEmpty()) {
            System.out.println(" Moneda no encontrada: " + symbol);
            return false;
        }

        String sql = "UPDATE moneda SET cantidad = cantidad + ? WHERE simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, symbol);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                double newBalance = getBalance(symbol);
                System.out.println(" Depósito exitoso: +" + amount + " " + symbol +
                        " | Nuevo balance: " + newBalance);
                return true;
            } else {
                System.out.println(" No se pudo realizar el depósito");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error en depósito: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean withdraw(String symbol, double amount) {
        if (amount <= 0) {
            System.out.println("❌ La cantidad debe ser mayor a cero");
            return false;
        }

        if (findBySymbol(symbol).isEmpty()) {
            System.out.println("❌ Moneda no encontrada: " + symbol);
            return false;
        }

        double currentBalance = getBalance(symbol);
        if (currentBalance < amount) {
            System.out.println(" Fondos insuficientes. Balance actual: " +
                    currentBalance + " " + symbol + " | Solicitado: " + amount);
            return false;
        }

        String sql = "UPDATE moneda SET cantidad = cantidad - ? WHERE simbolo = ? AND cantidad >= ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDouble(1, amount);
            ps.setString(2, symbol);
            ps.setDouble(3, amount);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                double newBalance = getBalance(symbol);
                System.out.println("✅ Retiro exitoso: -" + amount + " " + symbol +
                        " | Nuevo balance: " + newBalance);
                return true;
            } else {
                System.out.println(" No se pudo realizar el retiro");
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error en retiro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public double getBalance(String symbol) {
        String sql = "SELECT cantidad FROM moneda WHERE simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, symbol);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("cantidad");
                }
            }

        } catch (SQLException e) {
            System.out.println(" Error al obtener balance: " + e.getMessage());
            e.printStackTrace();
        }

        return 0.0;
    }


    public boolean updateCoin(Coin coin) {
        String sql = "UPDATE moneda SET nombre = ?, simbolo = ?, cantidad = ? WHERE id_moneda = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, coin.getNombre());
            ps.setString(2, coin.getSimbolo());
            ps.setDouble(3, coin.getCantidad());
            ps.setInt(4, coin.getIdMoneda());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Moneda actualizada: " + coin.getNombre());
                return true;
            } else {
                System.out.println("❌ No se pudo actualizar la moneda");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("❌ Error al actualizar moneda: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteByCoin(String symbol) {
        String sql = "DELETE FROM moneda WHERE simbolo = ?";

        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, symbol);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println(" Moneda eliminada: " + symbol);
                return true;
            } else {
                System.out.println(" Moneda no encontrada para eliminar: " + symbol);
                return false;
            }

        } catch (SQLException e) {
            System.out.println(" Error al eliminar moneda: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Método para mostrar el portafolio completo
    public void showPortfolio() {
        List<Coin> coins = findAll();

        if (coins.isEmpty()) {
            System.out.println("📊 Portafolio vacío");
            return;
        }

        System.out.println("\n📊 === PORTAFOLIO DE CRIPTOMONEDAS ===");
        System.out.println("┌─────────────────┬─────────┬──────────────────┐");
        System.out.println("│     MONEDA      │ SÍMBOLO │     CANTIDAD     │");
        System.out.println("├─────────────────┼─────────┼──────────────────┤");

        for (Coin coin : coins) {
            System.out.printf("│ %-15s │ %-7s │ %16.8f │%n",
                    coin.getNombre(),
                    coin.getSimbolo(),
                    coin.getCantidad());
        }

        System.out.println("└─────────────────┴─────────┴──────────────────┘");
        System.out.println("Total de monedas: " + coins.size());
    }
}