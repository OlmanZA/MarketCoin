package com.example.Crypto.Repository;

import com.example.Crypto.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Usuario_Repositorio extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);

}
