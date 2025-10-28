package com.example.Crypto.Controller;

import com.example.Crypto.Entities.Moneda;
import com.example.Crypto.Service.MonedaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("Crypto")
public class MonedaController {

    private static final Logger logger = LoggerFactory.getLogger(MonedaController.class);

    @Autowired
    private MonedaService monedaService;

    @PostMapping("/CrearMoneda")
    public Moneda crearMoneda(@RequestBody Moneda moneda){
        return monedaService.crearMoneda(moneda);
    }

}
