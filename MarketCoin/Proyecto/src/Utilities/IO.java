package Utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IO {

    //Método para leer un String con validación básica
    public static String lea(String mensaje) {

        //Codigo para leer
        String dato = "";
        InputStreamReader objflujo = new InputStreamReader(System.in);
        BufferedReader objleer = new BufferedReader(objflujo);
        try {
            imp(mensaje);
            dato = objleer.readLine();
        } catch (IOException er) {
            imp("Hubo un error en la lectura");

        }

        return dato;
    }

    //Método para imprimir
    public static void imp(String mensaje) {
        System.out.println(mensaje);
    }

//Método para leer un entero con manejo de errores

    public static int leaInt(String mensaje) {
        int numero = 0;
        boolean datoValido = false;
        InputStreamReader objflujo = new InputStreamReader(System.in);
        BufferedReader objleer = new BufferedReader(objflujo);

        while (!datoValido) {
            try {
                imp(mensaje);
                String dato = objleer.readLine();

                // Verificar si está vacío
                if (dato == null || dato.trim().isEmpty()) {
                    imp("Error: No puede estar vacío. Intente nuevamente.");
                    continue;
                }

                // Convertir a int
                numero = Integer.parseInt(dato.trim());
                datoValido = true;

            } catch (NumberFormatException e) {
                imp("Error: Debe ingresar un número entero válido. Intente nuevamente.");
            } catch (IOException er) {
                imp("Hubo un error en la lectura");
            }
        }
        return numero;
    }
}
