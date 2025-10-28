package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Repository.Usuario_Repositorio;
import com.example.Crypto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ServiceUsuario_impl implements UsuarioService {

    @Autowired
    private Usuario_Repositorio usuarioRepositorio;

    public Usuario crearUsuario(Usuario usuario)
    {
        // Verificar si el correo ya existe
        if (usuarioRepositorio.existsByCorreo(usuario.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        if (usuarioRepositorio.existsByCedula(usuario.getCedula())) {
            throw new RuntimeException("El correo ya está registrado");
        }

        // Guardar el nuevo usuario
        return usuarioRepositorio.save(usuario);
    }
    @Override
    public Optional<Usuario> iniciarSesion(String correo, String contraseña) {
        Optional<Usuario> usuario = usuarioRepositorio.findByCorreo(correo);
        if (usuario.isPresent() && usuario.get().getContraseña().equals(contraseña)) {
            return usuario;
        }
        return Optional.empty();
    }



}