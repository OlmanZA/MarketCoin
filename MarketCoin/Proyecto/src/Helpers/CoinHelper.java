package Helpers;

public class CoinHelper {

  //Convertir un resultSet en un Coin
  public static Coin mapResultSetToCoin(ResultSet rs) throws SQLException {
        return new Coin(
                rs.getInt("id_moneda"),
                rs.getString("nombre"),
                rs.getString("simbolo"),
                rs.getDouble("cantidad")
        );
    }
}
