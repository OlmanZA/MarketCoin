package com.example.Crypto.Controller;

import com.example.Crypto.Dtos.BilleteraResponse;
import com.example.Crypto.Entities.Billetera;
import com.example.Crypto.Service.BilleteraService;
import com.example.Crypto.Service.MonedaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Crypto")
public class BilleteraController {

    private static final Logger logger = LoggerFactory.getLogger(BilleteraController.class);

    @Autowired
    private BilleteraService billeteraService;


    @PostMapping("/CrearBilletera/{cedulaUsuario}")
    public ResponseEntity<BilleteraResponse> crearBilletera(
            @PathVariable Long cedulaUsuario,
            @RequestBody Billetera billetera) {

        logger.info("Creando billetera para usuario con cédula: {}", cedulaUsuario);
        Billetera nueva = billeteraService.crearBilletera(cedulaUsuario, billetera);

        if (nueva != null) {
            return ResponseEntity.ok(new BilleteraResponse(
                    true,
                    "Billetera creada correctamente",
                    nueva
            ));
        } else {
            return ResponseEntity.status(400).body(new BilleteraResponse(
                    false,
                    "No se pudo crear la billetera. Verifica el usuario o los datos enviados.",
                    null
            ));
        }
    }
}

