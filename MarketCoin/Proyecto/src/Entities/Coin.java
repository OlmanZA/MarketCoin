package Entities;

public class Coin {
    private Integer idMoneda;
    private String nombre;
    private String simbolo;
    private Double cantidad;

    // Constructor vacío
    public Coin() {}

    // Constructor sin ID (para insertar nuevas monedas)
    public Coin(String nombre, String simbolo) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.cantidad = 0.0;
    }

    // Constructor sin ID pero con cantidad
    public Coin(String nombre, String simbolo, Double cantidad) {
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.cantidad = cantidad;
    }

    // Constructor completo (para consultas de BD)
    public Coin(Integer idMoneda, String nombre, String simbolo, Double cantidad) {
        this.idMoneda = idMoneda;
        this.nombre = nombre;
        this.simbolo = simbolo;
        this.cantidad = cantidad;
    }

    // Getters y Setters
    public Integer getIdMoneda() {
        return idMoneda;
    }

    public void setIdMoneda(Integer idMoneda) {
        this.idMoneda = idMoneda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    // Métodos toString, equals y hashCode
    @Override
    public String toString() {
        return "Coin{" +
                "idMoneda=" + idMoneda +
                ", nombre='" + nombre + '\'' +
                ", simbolo='" + simbolo + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Coin coin = (Coin) obj;
        return simbolo != null ? simbolo.equals(coin.simbolo) : coin.simbolo == null;
    }

    @Override
    public int hashCode() {
        return simbolo != null ? simbolo.hashCode() : 0;
    }
}