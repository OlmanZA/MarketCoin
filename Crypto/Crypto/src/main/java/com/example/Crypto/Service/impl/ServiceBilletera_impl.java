package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Billetera;
import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Repository.Billetera_Repositorio;
import com.example.Crypto.Repository.Usuario_Repositorio;
import com.example.Crypto.Service.BilleteraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ServiceBilletera_impl implements BilleteraService {

    @Autowired
    private Billetera_Repositorio billeteraRepositorio;

    @Autowired
    private Usuario_Repositorio usuarioRepository;

    @Override
    public Billetera crearBilletera(Long cedulaUsuario, Billetera billetera) {
        // Buscar el usuario por cédula
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(cedulaUsuario);

        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        // Asignar el usuario a la billetera
        billetera.setUsuario(usuarioOpt.get());

        return billeteraRepositorio.save(billetera);
    }

    // Método para obtener las billeteras de un usuario
    public List<Billetera> obtenerBilleterasPorUsuario(Long cedulaUsuario) {
        return billeteraRepositorio.findByUsuarioCedula(cedulaUsuario);
    }

}
