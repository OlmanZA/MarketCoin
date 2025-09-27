package Helpers;

public class CoinHelper {

  // Conviertir un ResultSet en un objeto Moneda
    public static Coin convertirResultSetAMoneda(ResultSet rs) throws SQLException {
        return new Coin(
                rs.getInt("id_moneda"),
                rs.getString("nombre"),
                rs.getString("simbolo"),
                rs.getDouble("cantidad")
        );
    }

    public static void mostrarMoneda(Coin coin) {
        System.out.printf("│ %-15s │ %-7s │ %16.8f │%n",
                coin.getNombre(),
                coin.getSimbolo(),
                coin.getCantidad());
    }
  
}
