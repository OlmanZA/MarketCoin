package com.example.Crypto.Controller;

import com.example.Crypto.Entities.Billetera;
import com.example.Crypto.Service.BilleteraService;
import com.example.Crypto.Service.MonedaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Crypto")
public class BilleteraController {

    private static final Logger logger = LoggerFactory.getLogger(BilleteraController.class);

    @Autowired
    private BilleteraService billeteraService;

    @PostMapping("/CrearBilletera/{cedulaUsuario}")
    public Billetera crearBilletera(
            @PathVariable Long cedulaUsuario,
            @RequestBody Billetera billetera) {

        logger.info("Creando billetera para usuario con cédula: {}", cedulaUsuario);
        return billeteraService.crearBilletera(cedulaUsuario, billetera);
    }
}
