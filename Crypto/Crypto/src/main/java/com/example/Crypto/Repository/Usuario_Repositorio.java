package com.example.Crypto.Repository;

import com.example.Crypto.Entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface Usuario_Repositorio extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByCedula(Long cedula);
    Optional<Usuario> findByCorreo(String correo);

}


