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
}