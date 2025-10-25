package com.example.Crypto.Controller;


import com.example.Crypto.Entities.Usuario;
import com.example.Crypto.Service.UsuarioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
