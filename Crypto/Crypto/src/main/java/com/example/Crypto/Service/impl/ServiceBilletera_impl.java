package com.example.Crypto.Service.impl;

import com.example.Crypto.Entities.Billetera;
import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Repository.Billetera_Repositorio;
import com.example.Crypto.Repository.Usuario_Repositorio;
import com.example.Crypto.Service.BilleteraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceBilletera_impl implements BilleteraService {

    private static final Logger logger = LoggerFactory.getLogger(ServiceBilletera_impl.class);

    @Autowired
    private Billetera_Repositorio billeteraRepository;

    @Autowired
    private Usuario_Repositorio usuarioRepository;

    @Override
    @Transactional
    public Billetera crearBilletera(Long cedulaUsuario, Billetera billetera) {
        logger.info("Creando billetera '{}' para usuario con cédula: {}",
                billetera.getNombre(), cedulaUsuario);

        // Validar que el usuario existe
        Usuario usuario = usuarioRepository.findById(cedulaUsuario)
                .orElseThrow(() -> {
                    logger.error("Usuario con cédula {} no encontrado", cedulaUsuario);
                    return new RuntimeException("Usuario no encontrado con cédula: " + cedulaUsuario);
                });

        // Establecer la relación
        billetera.setUsuario(usuario);

        // Guardar y retornar
        Billetera billeteraGuardada = billeteraRepository.save(billetera);
        logger.info("Billetera creada exitosamente con ID: {}", billeteraGuardada.getNumeroBilletera());

        return billeteraGuardada;
    }

    @Override
    public List<Billetera> obtenerBilleterasPorUsuario(Long cedulaUsuario) {
        logger.info("Obteniendo billeteras del usuario con cédula: {}", cedulaUsuario);

        // Verificar que el usuario existe
        if (!usuarioRepository.existsById(cedulaUsuario)) {
            logger.warn("Usuario con cédula {} no encontrado", cedulaUsuario);
            throw new RuntimeException("Usuario no encontrado con cédula: " + cedulaUsuario);
        }

        List<Billetera> billeteras = billeteraRepository.findByUsuarioCedula(cedulaUsuario);
        logger.info("Se encontraron {} billeteras para el usuario {}", billeteras.size(), cedulaUsuario);

        return billeteras;
    }
}