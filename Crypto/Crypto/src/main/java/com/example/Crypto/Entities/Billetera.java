package com.example.Crypto.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "billetera")
public class Billetera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long  numeroBilletera;
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "cedula_usuario")
    private Usuario usuario;

    @OneToMany(mappedBy = "billetera", cascade = CascadeType.ALL)
    private List<Billetera_Moneda> billeteraMonedas;

}

