package com.example.Crypto.Controller;


import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("Crypto")//ESTO GENERA EL HTTP://LOCALHOST:8080/CRYPTO
//Esto se usaria cuando tuvieramos angular@CrossOrigin(value = )
public class UsuarioController {

    private static final Logger logger = LoggerFactory.getLogger(UsuarioController.class);

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/CrearUsuario")
    public Usuario crearUsuario(@RequestBody Usuario usuario){
        return usuarioService.crearUsuario(usuario);
    }

    @PostMapping("/IniciarSesion")
    public ResponseEntity<?> iniciarSesion(@RequestBody Usuario credenciales) {
        Optional<Usuario> usuario = usuarioService.iniciarSesion(
                credenciales.getCorreo(),
                credenciales.getContraseña()
        );

        if (usuario.isPresent()) {
            // Crear respuesta con información del usuario (sin contraseña)
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "Inicio de sesión exitoso");
            response.put("usuario", Map.of(
                    "cedula", usuario.get().getCedula(),
                    "nombre", usuario.get().getNombre(),
                    "correo", usuario.get().getCorreo()
            ));
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(401).body("Credenciales incorrectas");
        }
    }
}
