package com.example.Crypto.Controller;

import com.example.Crypto.Entities.BilleteraMoneda;
import com.example.Crypto.Service.BilleteraMonedaService;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/billetera-moneda")
public class BilleteraMonedaController {

    private final BilleteraMonedaService service;

    // Constructor con inyección de dependencia
    public BilleteraMonedaController(BilleteraMonedaService service) {
        this.service = service;
    }

    // Endpoint para crear una nueva Billetera_Moneda
    @PostMapping("/crearBilleteraMoneda")
    public BilleteraMoneda crear(@RequestBody BilleteraMoneda billeteraMoneda) {
        return service.crearBilleteraMoneda(billeteraMoneda);
    }

    // Endpoint para obtener una Billetera_Moneda específica por IDs
    @GetMapping("/obtener")
    public Optional<BilleteraMoneda> obtener(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId
    ) {
        return service.obtenerBilleteraMoneda(billeteraId, monedaId);
    }

    // Endpoint para listar todas las Billeteras_Monedas
    @GetMapping("/listar")
    public List<BilleteraMoneda> listar() {
        return service.listarBilleterasMonedas();
    }

    @PostMapping("/depositar")
    public BilleteraMoneda depositar(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId,
            @RequestParam double monto) {
        return service.depositar(billeteraId, monedaId, monto);
    }

    @PostMapping("/retirar")
    public BilleteraMoneda retirar(
            @RequestParam Long billeteraId,
            @RequestParam Long monedaId,
            @RequestParam double monto) {
        return service.retirar(billeteraId, monedaId, monto);
    }
}
