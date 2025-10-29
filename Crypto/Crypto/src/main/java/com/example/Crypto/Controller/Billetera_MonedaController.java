package com.example.Crypto.Controller;

import com.example.Crypto.Entities.Billetera_Moneda;
import com.example.Crypto.Service.Billetera_MonedaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/billetera-moneda")
public class Billetera_MonedaController {

    private final Billetera_MonedaService service;

    // Constructor con inyección de dependencia
    public Billetera_MonedaController(Billetera_MonedaService service) {
        this.service = service;
    }

    // Endpoint para crear una nueva Billetera_Moneda
    @PostMapping("/crearBilleteraMoneda")
    public Billetera_Moneda crear(@RequestBody Billetera_Moneda billeteraMoneda) {
        return service.crearBilleteraMoneda(billeteraMoneda);
    }

    // Endpoint para obtener una Billetera_Moneda específica por IDs
    @GetMapping("/obtener")
    public Optional<Billetera_Moneda> obtener(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId
    ) {
        return service.obtenerBilleteraMoneda(billeteraId, monedaId);
    }

    // Endpoint para listar todas las Billeteras_Monedas
    @GetMapping("/listar")
    public List<Billetera_Moneda> listar() {
        return service.listarBilleterasMonedas();
    }

    @PostMapping("/depositar")
    public Billetera_Moneda depositar(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId,
            @RequestParam double monto) {
        return service.depositar(billeteraId, monedaId, monto);
    }

    @PostMapping("/retirar")
    public Billetera_Moneda retirar(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId,
            @RequestParam double monto) {
        return service.retirar(billeteraId, monedaId, monto);
    }
}
