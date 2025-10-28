package com.example.Crypto.Repository;

import com.example.Crypto.Entities.Billetera;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface Billetera_Repositorio extends JpaRepository<Billetera,Long> {

    boolean existsByNombre(String nombre);
    List<Billetera> findByUsuarioCedula(Long cedulaUsuario);

}
