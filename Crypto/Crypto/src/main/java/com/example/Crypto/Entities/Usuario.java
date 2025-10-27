package com.example.Crypto.Entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuario")
public class Usuario {

    @Id
    private Long cedula;
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private String nombre;
    private String correo;
    private String contraseña;
    private String fecha_nac;
    private String pais_nacimiento;

}
