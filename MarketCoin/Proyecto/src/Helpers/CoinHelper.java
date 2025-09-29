package Helpers;

import Entities.Coin;
import Utilities.IO;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoinHelper {

    // Convertir un ResultSet en un objeto Moneda
    public static Coin convertirResultSetAMoneda(ResultSet rs) throws SQLException {
        return new Coin(
                rs.getInt("id_moneda"),
                rs.getString("nombre"),
                rs.getString("simbolo"),
                rs.getDouble("cantidad")
        );
    }

    public static void mostrarMoneda(Coin coin) {
        IO.imp(String.format("│ %-15s │ %-7s │ %16.8f │",
                coin.getNombre(),
                coin.getSimbolo(),
                coin.getCantidad()));
    }

    // Mostrar lista de monedas en formato tabla
    public static void mostrarListaMonedas(java.util.List<Coin> monedas) {
        if (monedas.isEmpty()) {
            IO.imp("No hay monedas disponibles.");
            return;
        }

        IO.imp("│ Nombre          │ Símbolo │ Cantidad         │");

        for (Coin coin : monedas) {
            mostrarMoneda(coin);
        }
    }

    public static void verMonedasPorSimbolo() {

        CoinDaoImp Co  = new CoinDaoImp();

        // Obtener todas las monedas
        List<Coin> monedas = Co.findAll();

        if (monedas.isEmpty()) {
            IO.imp("❌ No hay criptomonedas registradas en el catálogo.");
            return;
        }

        IO.imp("\n=== CATÁLOGO DE MONEDAS POR SÍMBOLO ===");
        IO.imp("-----------------------------------------");
        for (Coin m : monedas) {
            IO.imp(String.format("ID: %-3d | Nombre: %-15s | Símbolo: %s",
                    m.getIdMoneda(), m.getNombre(), m.getSimbolo()));
        }
        IO.imp("-----------------------------------------");
    }
}
