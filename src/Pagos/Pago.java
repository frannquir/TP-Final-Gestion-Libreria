package Pagos;

import Excepciones.DineroInsuficienteException;
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
    public static boolean realizarPagoTarjeta(){
        String numeroTarjeta = "";
        String codigoSeguridad = "";
        int mes = 0;
        int anio = 0;
        String flag = "c";
        Random random = new Random();

        System.out.println("Ingrese 'n' en cualquier momento para cancelar el registro de pago.");

        while (!flag.equals("n")) {
            System.out.print("Ingrese su numero de tarjeta (16 digitos sin espacios): ");
            numeroTarjeta = scanner.nextLine();
            flag = numeroTarjeta;

            if (!flag.equals("n")) {
                try {
                    if (verificarTarjeta(numeroTarjeta)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Numero de tarjeta no valido: " + e.getMessage());
                }
            }
        }

        while (!flag.equals("n")) {
            System.out.print("Ingrese el codigo de seguridad (3 digitos): ");
            codigoSeguridad = scanner.nextLine();
            flag = codigoSeguridad;

            if (!flag.equals("n")) {
                try {
                    if (verificarCodigoSeguridad(codigoSeguridad)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Codigo de seguridad no valido: " + e.getMessage());
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
                    System.out.println("Mes no valido: " + e.getMessage());
                } catch (NumberFormatException e) { ///El metodo parseInt puede arrojar esta excepcion
                    System.out.println("Debe ingresar un numero valido para el mes.");
                }
            }
        }

        while (!flag.equals("n")) {
            System.out.print("Ingrese el anio de vencimiento (2024-2040): ");
            String anioInput = scanner.nextLine(); ///Leo el anio como string, y luego si no es n, lo convierto en int
            flag = anioInput;

            if (!flag.equals("n")) {
                try {
                    anio = Integer.parseInt(anioInput);  // Solo convierte si no se ingresó "n"
                    if (verificarAnio(anio)) {
                        break;
                    }
                } catch (FormatoInvalidoException e) {
                    System.out.println("Año no valido: " + e.getMessage());
                } catch (NumberFormatException e) {//El metodo parseInt puede arrojar esta excepcion
                    System.out.println("Debe ingresar un numero valido para el año.");
                }
            }
        }

        if (!flag.equals("n")) { ///Si no se salio del pago en ningun momento, se retorna true or false aleatoriamente
            return random.nextInt(10) < 9; // 10% de que rechace el pago
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
            throw new FormatoInvalidoException("El codigo de seguridad debe tener 3 digitos numericos.");
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

    private static boolean verificarDinero (int dinero) throws DineroInsuficienteException {
        if (dinero < 1000) {
            throw new DineroInsuficienteException();
        }
        return true;

    }

    /* Metodo sin terminar. Falta que toque 'n' en cualquier momento para salir.*/
    public static boolean realizarPagoEfectivo() {
        int dinero = 0;
        int vuelto = 0;
        boolean usuarioConDineroSuficiente = false;
        String flag = "c";

        System.out.println("El costo del pasaje a premium es de 1000 pesos.");
        System.out.println("Ingrese 'n' en cualquier momento para cancelar el registro de pago.");
        
        while (!flag.equals("n")){

            System.out.println("Ingrese el monto de dinero con el que va a pagar.");
            String dineroInput = scanner.nextLine();
            flag = dineroInput;

            if (!flag.equals("n")){
                try {
                    dinero = Integer.parseInt(dineroInput); // Lo pasa a un int si el usuario no salio
                if (verificarDinero(dinero)){
                    usuarioConDineroSuficiente = true; // llega hasta esta instancia solo si el usuario no salio y el dinero ingresado es valido
                    vuelto = dinero - 1000;
                    if(vuelto != 0.0){
                    System.out.println("Muchas gracias! .\n*Se le han devuelto " + vuelto + " pesos.*");
                    } else {
                        System.out.println("Muchas gracias por la compra!");
                    }
                    break;
                }
                } catch (DineroInsuficienteException e){
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e){
                    System.out.println("Porfavor, ingrese un monto numerico");
                }
            }
            }
        if (flag.equals("n")){
            System.out.println("Pago cancelado"); // Si el usuario toco n sale el print
        }
        return usuarioConDineroSuficiente;
    }

}
