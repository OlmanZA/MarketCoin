package com.example.Crypto.Service;

import com.example.Crypto.Entities.Usuario;
import org.springframework.stereotype.Service;
import java.util.Optional;


public interface UsuarioService {

    Usuario crearUsuario(Usuario usuario);
    Optional<Usuario> iniciarSesion(String correo, String contraseña);

}
