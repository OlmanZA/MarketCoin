package Entities;

import java.time.LocalDate;

public class User {

    private long cedula;
    private String name;
    private String email;

    private String password;
    private LocalDate birthDate;
    private String country;

    public User(){}

    //Constructor de la clase
    public User(long cedula, String name, String email,String password, LocalDate birthDate, String country) {
        this.cedula = cedula;
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthDate = birthDate;
        this.country = country;
    }

    //GETTERS AND SETTERS

    public long getCedula() {
        return cedula;
    }

    public void setCedula(long cedula) {
        this.cedula = cedula;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
