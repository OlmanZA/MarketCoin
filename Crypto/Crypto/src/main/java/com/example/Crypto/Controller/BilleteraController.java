package com.example.Crypto.Controller;

import com.example.Crypto.Entities.Billetera;
import com.example.Crypto.Service.BilleteraService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("Crypto")
public class BilleteraController {

    private static final Logger logger = LoggerFactory.getLogger(BilleteraController.class);

    @Autowired
    private BilleteraService billeteraService;

    @PostMapping("/CrearBilletera/{cedulaUsuario}")
    public ResponseEntity<Map<String, Object>> crearBilletera(
            @PathVariable Long cedulaUsuario,
            @RequestBody Billetera billetera) {

        Map<String, Object> response = new HashMap<>();

        try {
            logger.info("Creando billetera para usuario con cédula: {}", cedulaUsuario);

            Billetera billeteraCreada = billeteraService.crearBilletera(cedulaUsuario, billetera);

            if (billeteraCreada != null) {
                response.put("success", true);
                response.put("message", "Billetera creada exitosamente");
                response.put("data", billeteraCreada);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } else {
                response.put("success", false);
                response.put("message", "No se pudo crear la billetera");
                response.put("data", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
            }

        } catch (Exception e) {
            logger.error("Error al crear billetera: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("message", "Error al crear la billetera: " + e.getMessage());
            response.put("data", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/BilleterasUsuario/{cedulaUsuario}")
    public ResponseEntity<?> obtenerBilleterasPorUsuario(@PathVariable Long cedulaUsuario) {
        try {
            logger.info("Obteniendo billeteras para usuario con cédula: {}", cedulaUsuario);
            List<Billetera> billeteras = billeteraService.obtenerBilleterasPorUsuario(cedulaUsuario);

            if (billeteras.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("No se encontraron billeteras para este usuario");
            }

            return ResponseEntity.ok(billeteras);
        } catch (Exception e) {
            logger.error("Error al obtener billeteras: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al obtener billeteras: " + e.getMessage());
        }
    }
    @GetMapping("/ListarBilleteras/{cedulaUsuario}")
    public ResponseEntity<?> listarBilleterasPorUsuario(@PathVariable Long cedulaUsuario) {
        return obtenerBilleterasPorUsuario(cedulaUsuario);
    }
}