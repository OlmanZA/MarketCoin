package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Repository.Usuario_Repositorio;
import com.example.Crypto.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        // Guardar el nuevo usuario
        return usuarioRepositorio.save(usuario);
    }



}