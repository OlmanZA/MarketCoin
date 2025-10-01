package Entities;

public class Wallet {
    private String numeroBilletera;
    private long cedulaUsuario;
    private String nombreBilletera;  // Este campo debe existir

    public Wallet(String numeroBilletera, long cedulaUsuario, String nombreBilletera) {
        this.numeroBilletera = numeroBilletera;
        this.cedulaUsuario = cedulaUsuario;
        this.nombreBilletera = nombreBilletera;
    }

    // Getters y Setters
    public String getNumeroBilletera() {
        return numeroBilletera;
    }

    public void setNumeroBilletera(String numeroBilletera) {
        this.numeroBilletera = numeroBilletera;
    }

    public long getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(long cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getNombreBilletera() {
        return nombreBilletera;
    }

    public void setNombreBilletera(String nombreBilletera) {
        this.nombreBilletera = nombreBilletera;
    }

    @Override
    public String toString() {
        return "Wallet{" +
                "numeroBilletera='" + numeroBilletera + '\'' +
                ", cedulaUsuario=" + cedulaUsuario +
                ", nombreBilletera='" + nombreBilletera + '\'' +
                '}';
    }
}