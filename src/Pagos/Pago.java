package Pagos;

import Excepciones.FormatoInvalidoException;

import java.util.Random;
import java.util.Scanner;

public class Pago {

    private static final Scanner scanner = new Scanner(System.in);


    /**Este metodo solicita al usuario que ingrese su tarjeta y su CVV junto a su mes y anio de vencimiento
    *Luego de verificar que todos los datos son correctos, se realiza el pago, y se retorna true si
    *este es exitoso para que pueda ser usado en otro metodo que se encargue del pasaje de plan
    *El pago puede "no ser exitoso" con un 10% de probabilidades o si se cancela el pago en cualquier momento tocando n
     */
    public static boolean realizarPago(){
        String numeroTarjeta = "";
        String codigoSeguridad = "";
        int mes = 0;
        int anio = 0;
        String flag = "c";
        Random random = new Random();

        System.out.println("Ingrese 'n' en cualquier momento para cancelar el registro de pago.");

        while (!flag.equals("n")) {
            System.out.print("Ingrese su número de tarjeta (16 dígitos): ");
            numeroTarjeta = scanner.nextLine();
            flag = numeroTarjeta;

            if (!flag.equals("n")) {
                try {
                    if (verificarTarjeta(numeroTarjeta)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Número de tarjeta no válido: " + e.getMessage());
                }
            }
        }

        while (!flag.equals("n")) {
            System.out.print("Ingrese el código de seguridad (3 dígitos): ");
            codigoSeguridad = scanner.nextLine();
            flag = codigoSeguridad;

            if (!flag.equals("n")) {
                try {
                    if (verificarCodigoSeguridad(codigoSeguridad)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Código de seguridad no válido: " + e.getMessage());
                }
            }
        }

        while (!flag.equals("n")) {
            System.out.print("Ingrese el mes de vencimiento (1-12): ");
            String mesInput = scanner.nextLine(); ///Para la lectura, lo leo como String y luego si no es n, lo convierto en int
            flag = mesInput;

            if (!flag.equals("n")) {
                try {
                    mes = Integer.parseInt(mesInput);  // Solo convierte si no se ingresó "n"
                    if (verificarMes(mes)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Mes no válido: " + e.getMessage());
                } catch (NumberFormatException e) { ///El metodo parseInt puede arrojar esta excepcion
                    System.out.println("Debe ingresar un número válido para el mes.");
                }
            }
        }

        while (!flag.equals("n")) {
            System.out.print("Ingrese el año de vencimiento (2024-2040): ");
            String anioInput = scanner.nextLine(); ///Leo el anio como string, y luego si no es n, lo convierto en int
            flag = anioInput;

            if (!flag.equals("n")) {
                try {
                    anio = Integer.parseInt(anioInput);  // Solo convierte si no se ingresó "n"
                    if (verificarAnio(anio)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Año no válido: " + e.getMessage());
                } catch (NumberFormatException e) {//El metodo parseInt puede arrojar esta excepcion
                    System.out.println("Debe ingresar un número válido para el año.");
                }
            }
        }

        if (!flag.equals("n")) { ///Si no se salio del pago en ningun momento, se retorna true or false aleatoriamente
            return random.nextInt(10) < 9;
        }

        ///Si en algun momento se toco n, el pago fue cancelado y se retorna false ya que este fallo
        System.out.println("Pago cancelado.");
        return false;
    }

    private static boolean verificarTarjeta (String tarjeta) throws FormatoInvalidoException{

        if (!tarjeta.matches("\\d{16}")) { ///Usamos el metodo Matches para verificar que el String cumpla con un patron especifico
            // ,en este caso \\d para indicar que sea un numero del 0 al 9, y 16 para indicar que la longitud es de 16 digitos
            throw new FormatoInvalidoException("El número de tarjeta debe tener 16 digitos numericos.");
        }
        return true;
    }
    private static boolean verificarCodigoSeguridad(String codigoSeguridad)throws FormatoInvalidoException{
        if (!codigoSeguridad.matches("\\d{3}")) {
            throw new FormatoInvalidoException("El código de seguridad debe tener 3 dígitos numericos.");
        }
        return true;
    }
    private static boolean verificarMes (int mes)throws FormatoInvalidoException{
        if (mes < 1 || mes > 12) {
            throw new FormatoInvalidoException("El mes debe estar entre 1 y 12.");
        }
        return true;
    }

    private static boolean verificarAnio(int anio)throws FormatoInvalidoException{
        if (anio < 2024 || anio > 2040) {
            throw new FormatoInvalidoException("El anio debe estar entre 2024 y 2040.");
        }
        return true;
    }

}
