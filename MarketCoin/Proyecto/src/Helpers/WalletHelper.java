package Helpers;

import Dao.Implements.WalletDaoImp;
import Entities.CoinWallet;
import Entities.Wallet;
import Entities.User;
import Utilities.IO;

import java.util.List;
import java.util.Optional;
import java.util.Random;

public class WalletHelper {
    private static WalletDaoImp walletDao = new WalletDaoImp();
    static Random random = new Random();

    public static String generateWalletNumber(long cedulaUsuario) {
        int numeroAleatorio = random.nextInt(9000) + 1000; // Número de 4 cifras (1000-9999)
        return "WAL-" + cedulaUsuario + "-" + numeroAleatorio;
    }

    public static void crearBilletera(User usuario) {
        IO.imp("\n--- CREAR BILLETERA ---");

        try {
            IO.imp("Ingrese un nombre para la billetera: ");
            String nombreBilletera = IO.lea("").trim();

            if (nombreBilletera.isEmpty()) {
                nombreBilletera = "Mi Billetera Crypto";
            }

            walletDao.createWallet(usuario.getCedula(), nombreBilletera);
            IO.imp("✅ Billetera creada exitosamente!");

        } catch (Exception e) {
            IO.imp("❌ Error al crear billetera: " + e.getMessage());
        }
    }


    // DEPOSITAR
    public static void depositar(User usuario) {
        IO.imp("\n--- DEPOSITAR CRYPTO ---");

        try {
            // Verificar si el usuario tiene billeteras
            List<Wallet> billeteras = walletDao.findAllByUser(usuario.getCedula());

            if (billeteras.isEmpty()) {
                IO.imp("❌ No tienes billeteras creadas.");
                IO.imp("💡 Primero crea una billetera desde el menú principal.");
                return;
            }

            // Mostrar billeteras disponibles
            IO.imp("💰 TUS BILLETERAS DISPONIBLES:");
            IO.imp("=================================");

            for (int i = 0; i < billeteras.size(); i++) {
                Wallet wallet = billeteras.get(i);
                IO.imp((i + 1) + ". " + wallet.getNombreBilletera() +
                        " - Número: " + wallet.getNumeroBilletera());
            }

            IO.imp("=================================");

            // Seleccionar billetera
            int opcionBilletera;
            while (true) {
                IO.imp("Selecciona el número de la billetera para depositar: ");
                try {
                    opcionBilletera = Integer.parseInt(IO.lea(""));
                    if (opcionBilletera >= 1 && opcionBilletera <= billeteras.size()) {
                        break;
                    } else {
                        IO.imp("❌ Opción inválida. Debe ser entre 1 y " + billeteras.size());
                    }
                } catch (NumberFormatException e) {
                    IO.imp("❌ Por favor, ingresa un número válido.");
                }
            }

            Wallet billeteraSeleccionada = billeteras.get(opcionBilletera - 1);
            IO.imp("✅ Billetera seleccionada: " + billeteraSeleccionada.getNombreBilletera());

            // Ingresar símbolo de la moneda
            String simbolo;
            while (true) {
                IO.imp("Ingresa el símbolo de la moneda (ej: BTC, ETH, USDT, BNB, SOL, ADA): ");
                simbolo = IO.lea("").trim().toUpperCase();

                if (simbolo.isEmpty()) {
                    IO.imp("❌ El símbolo no puede estar vacío.");
                    continue;
                }

                if (walletDao.existeMoneda(simbolo)) {
                    break;
                } else {
                    IO.imp("❌ La moneda '" + simbolo + "' no existe en el sistema.");
                    IO.imp("💡 Monedas disponibles: BTC, ETH, ADA, SOL, DOT, BNB, USDT");
                }
            }

            // Ingresar cantidad
            double cantidad;
            while (true) {
                IO.imp("Ingresa la cantidad a depositar: ");
                try {
                    cantidad = Double.parseDouble(IO.lea(""));
                    if (cantidad <= 0) {
                        IO.imp("❌ La cantidad debe ser mayor a 0.");
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    IO.imp("❌ Por favor, ingresa un número válido.");
                }
            }

            // Confirmar depósito
            IO.imp("\n📋 RESUMEN DEL DEPÓSITO:");
            IO.imp("   • Billetera: " + billeteraSeleccionada.getNombreBilletera());
            IO.imp("   • Moneda: " + simbolo);
            IO.imp("   • Cantidad: " + cantidad);
            IO.imp("   • Número de billetera: " + billeteraSeleccionada.getNumeroBilletera());

            IO.imp("¿Confirmar depósito? (s/n): ");
            String confirmacion = IO.lea("").trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                // Realizar depósito
                boolean exito = walletDao.depositar(
                        billeteraSeleccionada.getNumeroBilletera(),
                        simbolo,
                        cantidad
                );

                if (exito) {
                    IO.imp("✅ ¡Depósito exitoso!");
                    IO.imp("💰 " + cantidad + " " + simbolo + " depositados en " +
                            billeteraSeleccionada.getNombreBilletera());
                } else {
                    IO.imp("❌ Error al realizar el depósito.");
                }
            } else {
                IO.imp("❌ Depósito cancelado.");
            }

        } catch (Exception e) {
            IO.imp("❌ Error inesperado durante el depósito: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // RETIRAR
    public static void retirar(User usuario) {
        IO.imp("\n--- RETIRAR CRYPTO ---");

        try {
            // Verificar si el usuario tiene billeteras
            List<Wallet> billeteras = walletDao.findAllByUser(usuario.getCedula());

            if (billeteras.isEmpty()) {
                IO.imp("❌ No tienes billeteras creadas.");
                IO.imp("💡 Primero crea una billetera desde el menú principal.");
                return;
            }

            // Mostrar billeteras disponibles
            IO.imp("💰 TUS BILLETERAS DISPONIBLES:");
            IO.imp("=================================");

            for (int i = 0; i < billeteras.size(); i++) {
                Wallet wallet = billeteras.get(i);
                IO.imp((i + 1) + ". " + wallet.getNombreBilletera() +
                        " - Número: " + wallet.getNumeroBilletera());
            }

            IO.imp("=================================");

            // Seleccionar billetera
            int opcionBilletera;
            while (true) {
                IO.imp("Selecciona el número de la billetera para retirar: ");
                try {
                    opcionBilletera = Integer.parseInt(IO.lea(""));
                    if (opcionBilletera >= 1 && opcionBilletera <= billeteras.size()) {
                        break;
                    } else {
                        IO.imp("❌ Opción inválida. Debe ser entre 1 y " + billeteras.size());
                    }
                } catch (NumberFormatException e) {
                    IO.imp("❌ Por favor, ingresa un número válido.");
                }
            }

            Wallet billeteraSeleccionada = billeteras.get(opcionBilletera - 1);
            IO.imp("✅ Billetera seleccionada: " + billeteraSeleccionada.getNombreBilletera());

            // Mostrar monedas disponibles en esta billetera
            List<CoinWallet> monedas = walletDao.getCoinsByWallet(billeteraSeleccionada.getNumeroBilletera());

            if (monedas.isEmpty()) {
                IO.imp("❌ Esta billetera está vacía. No hay monedas para retirar.");
                return;
            }

            IO.imp("\n💰 MONEDAS DISPONIBLES EN ESTA BILLETERA:");
            IO.imp("=================================");

            for (int i = 0; i < monedas.size(); i++) {
                CoinWallet moneda = monedas.get(i);
                IO.imp((i + 1) + ". " + moneda.getCantidad() + " " + moneda.getSimbolo() +
                        " (" + moneda.getNombre() + ")");
            }

            IO.imp("=================================");

            // Seleccionar moneda
            int opcionMoneda;
            while (true) {
                IO.imp("Selecciona el número de la moneda a retirar: ");
                try {
                    opcionMoneda = Integer.parseInt(IO.lea(""));
                    if (opcionMoneda >= 1 && opcionMoneda <= monedas.size()) {
                        break;
                    } else {
                        IO.imp("❌ Opción inválida. Debe ser entre 1 y " + monedas.size());
                    }
                } catch (NumberFormatException e) {
                    IO.imp("❌ Por favor, ingresa un número válido.");
                }
            }

            CoinWallet monedaSeleccionada = monedas.get(opcionMoneda - 1);
            String simbolo = monedaSeleccionada.getSimbolo();
            double saldoActual = monedaSeleccionada.getCantidad().doubleValue();

            IO.imp("✅ Moneda seleccionada: " + simbolo);
            IO.imp("💰 Saldo disponible: " + saldoActual + " " + simbolo);

            // Ingresar cantidad a retirar
            double cantidad;
            while (true) {
                IO.imp("Ingresa la cantidad a retirar: ");
                try {
                    cantidad = Double.parseDouble(IO.lea(""));

                    if (cantidad <= 0) {
                        IO.imp("❌ La cantidad debe ser mayor a 0.");
                    } else if (cantidad > saldoActual) {
                        IO.imp("❌ Cantidad excede el saldo disponible.");
                        IO.imp("💡 Saldo disponible: " + saldoActual + " " + simbolo);
                    } else {
                        break;
                    }
                } catch (NumberFormatException e) {
                    IO.imp("❌ Por favor, ingresa un número válido.");
                }
            }

            // Confirmar retiro
            IO.imp("\n📋 RESUMEN DEL RETIRO:");
            IO.imp("   • Billetera: " + billeteraSeleccionada.getNombreBilletera());
            IO.imp("   • Moneda: " + simbolo);
            IO.imp("   • Cantidad a retirar: " + cantidad + " " + simbolo);
            IO.imp("   • Saldo restante: " + (saldoActual - cantidad) + " " + simbolo);
            IO.imp("   • Número de billetera: " + billeteraSeleccionada.getNumeroBilletera());

            IO.imp("¿Confirmar retiro? (s/n): ");
            String confirmacion = IO.lea("").trim().toLowerCase();

            if (confirmacion.equals("s") || confirmacion.equals("si")) {
                // Realizar retiro
                boolean exito = walletDao.retirar(
                        billeteraSeleccionada.getNumeroBilletera(),
                        simbolo,
                        cantidad
                );

                if (exito) {
                    IO.imp("✅ ¡Retiro exitoso!");
                    IO.imp("💰 " + cantidad + " " + simbolo + " retirados de " +
                            billeteraSeleccionada.getNombreBilletera());
                    IO.imp("💳 Nuevo saldo: " + (saldoActual - cantidad) + " " + simbolo);
                } else {
                    IO.imp("❌ Error al realizar el retiro.");
                }
            } else {
                IO.imp("❌ Retiro cancelado.");
            }

        } catch (Exception e) {
            IO.imp("❌ Error inesperado durante el retiro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // VER BILLETERA
    public static void verBilletera(User usuario) {
        IO.imp("\n--- MIS BILLETERAS ---");

        try {
            List<Wallet> billeteras = walletDao.findAllByUser(usuario.getCedula());

            if (billeteras.isEmpty()) {
                IO.imp("❌ No tienes billeteras creadas.");
                return;
            }

            IO.imp("📊 Tienes " + billeteras.size() + " billetera(s):");
            IO.imp("==========================================");

            for (Wallet billetera : billeteras) {
                IO.imp("💼 BILLETERA: " + billetera.getNombreBilletera());
                IO.imp("   • Número: " + billetera.getNumeroBilletera());

                List<CoinWallet> monedas = walletDao.getCoinsByWallet(billetera.getNumeroBilletera());

                if (monedas.isEmpty()) {
                    IO.imp("   • Estado: Vacía");
                } else {
                    IO.imp("   • Monedas disponibles:");
                    double totalMonedas = 0;
                    for (CoinWallet moneda : monedas) {
                        double cantidad = moneda.getCantidad().doubleValue();
                        IO.imp("     ◦ " + cantidad + " " + moneda.getSimbolo() +
                                " (" + moneda.getNombre() + ")");
                        totalMonedas += cantidad;
                    }
                    IO.imp("   • Total de monedas: " + totalMonedas);
                }
                IO.imp("------------------------------------------");
            }

        } catch (Exception e) {
            IO.imp("❌ Error al obtener billeteras: " + e.getMessage());
        }
    }

    public static void transformarADinero(User usuario) {

        // --- CONSTANTES DE CONVERSIÓN Y PRECIOS ---
        final double TASA_USD_COP = 4000.00;
        final double PRECIO_BTC_USD = 65000.00;
        final double PRECIO_ETH_USD = 3500.00;
        final double PRECIO_USDT_USD = 1.00;
        final double PRECIO_BNB_USD = 550.00;
        final double PRECIO_SOL_USD = 150.00;
        final double PRECIO_ADA_USD = 0.40;


        java.util.function.Function<String, Double> getPrecioSimuladoUSD = (simbolo) -> {
            return switch (simbolo.toUpperCase()) {
                case "BTC" -> PRECIO_BTC_USD;
                case "ETH" -> PRECIO_ETH_USD;
                case "USDT" -> PRECIO_USDT_USD;
                case "BNB" -> PRECIO_BNB_USD;
                case "SOL" -> PRECIO_SOL_USD;
                case "ADA" -> PRECIO_ADA_USD;
                default -> 0.0;
            };
        };


        WalletDaoImp walletDao = new WalletDaoImp();

        //Obtener la billetera y saldos
        Optional<Wallet> billeteraOpt = walletDao.findByUserCedula(usuario.getCedula());
        if (billeteraOpt.isEmpty()) {
            IO.imp("❌ No se encontró una billetera. Por favor, cree una.");
            return;
        }

        Wallet billetera = billeteraOpt.get();
        List<CoinWallet> saldos = walletDao.getCoinsByWallet(billetera.getNumeroBilletera());

        // Filtrar solo monedas con saldo > 0
        List<CoinWallet> saldosValidos = saldos.stream()
                .filter(cw -> cw.getCantidad().doubleValue() > 0.0)
                .filter(cw -> getPrecioSimuladoUSD.apply(cw.getSimbolo()) > 0.0)
                .toList();

        if (saldosValidos.isEmpty()) {
            IO.imp("❌ La billetera está vacía o no contiene monedas con precio conocido para transformar.");
            return;
        }



        IO.imp("\n=== SELECCIÓN DE MONEDA PARA CONVERSIÓN A COP ===");

        Map<Integer, CoinWallet> opcionesMonedas = new HashMap<>();
        int index = 1;

        // Mostrar monedas disponibles para selección individual
        for (CoinWallet cw : saldosValidos) {
            IO.imp(String.format("%d. %s (%s) - Saldo: %.8f",
                    index, cw.getNombre(), cw.getSimbolo(), cw.getCantidad().doubleValue()));
            opcionesMonedas.put(index, cw);
            index++;
        }

        // Opción para ver el total
        final int OPCION_TODAS = index;
        IO.imp(String.format("%d. Convertir TODAS las monedas", OPCION_TODAS));
        IO.imp("----------------------------------------------");

        int opcion = IO.leaInt("Elija el número de la moneda o elija 'TODAS': ");
        CoinWallet monedaSeleccionada = opcionesMonedas.get(opcion);



        if (opcion == OPCION_TODAS) {

            // calculo total

            double valorTotalCOP = 0.0;

            IO.imp("\n=== VALORACIÓN DE TODAS LAS MONEDAS EN COP ===");
            IO.imp(String.format("Tasa de Conversión: 1 USD = $%,.2f COP", TASA_USD_COP));
            IO.imp("-----------------------------------------------------");

            for (CoinWallet cw : saldosValidos) {
                double cantidad = cw.getCantidad().doubleValue();
                double precioUSD = getPrecioSimuladoUSD.apply(cw.getSimbolo());

                double valorCOP = (cantidad * precioUSD) * TASA_USD_COP;
                valorTotalCOP += valorCOP;

                IO.imp(String.format("%s (%s): $%,.0f COP",
                        cw.getNombre(), cw.getSimbolo(), valorCOP));
            }

            IO.imp("-----------------------------------------------------");
            IO.imp(String.format("💰 VALOR TOTAL ESTIMADO DEL PORTAFOLIO: $%,.0f COP", valorTotalCOP));

        } else if (monedaSeleccionada != null) {

            // calculo individual

            String simbolo = monedaSeleccionada.getSimbolo();
            double cantidad = monedaSeleccionada.getCantidad().doubleValue();
            double precioUSD = getPrecioSimuladoUSD.apply(simbolo);

            double valorUSD = cantidad * precioUSD;
            double valorCOP = valorUSD * TASA_USD_COP;

            IO.imp("\n=== CONVERSIÓN INDIVIDUAL A COP ===");
            IO.imp(String.format("Moneda: %s (%s)", monedaSeleccionada.getNombre(), simbolo));
            IO.imp(String.format("Saldo: %.8f", cantidad));
            IO.imp(String.format("Precio (USD): $%,.2f", precioUSD));
            IO.imp(String.format("Tasa (COP): $%,.2f", TASA_USD_COP));
            IO.imp("-------------------------------------");
            IO.imp(String.format("Valor Total en USD: $%,.2f USD", valorUSD));
            IO.imp(String.format("Valor Total en COP: $%,.0f COP", valorCOP));
            IO.imp("-------------------------------------");

        } else {
            IO.imp("⚠️ Opción no válida. Volviendo al meú.");
        }
    }
}
