package Handlers;

import Excepciones.FormatoInvalidoException;
import Excepciones.NoCoincideException;
import Libros.EstadoLibro;

import java.util.Scanner;


public class Helper {
    // Falta verificar que el nombre comience con una letra
    public static boolean verificarNombre(String nombre) throws FormatoInvalidoException {
        if (verificarLongitud(3,16,nombre.length())) {
            throw new FormatoInvalidoException("El nombre debe tener entre 3 o 16 letras");
        }
        return true;
    }
    public static boolean verificarLongitud(int minima, int maxima, int longitud) {
        boolean rta = false;
        if (longitud > maxima || longitud < minima) {
            rta  = true;
        }
        return rta; ///El metodo retorna True si la longitud del string no cumple con los parametros establecidos
    }
    public static boolean verificarEmail(String email) throws FormatoInvalidoException {
        if (email == null || email.trim().isEmpty())
            throw new FormatoInvalidoException("El correo esta vacio");

        String patron = "^[a-zA-Z0-9][a-zA-Z0-9._%+-]*[a-zA-Z0-9]@gmail\\.com$";

        if (email.startsWith("."))
            throw new FormatoInvalidoException("El correo no puede empezar con punto.");


        if (email.contains(".."))
            throw new FormatoInvalidoException("El correo no puede tener puntos consecutivos");

        if (!email.matches(patron))
            throw new FormatoInvalidoException("Solo podes usar letras, numeros y puntos");

        if (verificarLongitud(16,64,email.length()))
            throw new FormatoInvalidoException("El correo debe tener entre 16 y 64 caracteres.");

        if (!email.endsWith("@gmail.com"))
            throw new FormatoInvalidoException("Solo aceptamos correos registrados en gmail.");

        return true;
    }
    public static boolean verificarContrasenia (String contrasenia) throws FormatoInvalidoException{
        if(verificarLongitud(6,30,contrasenia.length()))
        {
            throw new FormatoInvalidoException("La contrasenia debe tener entre 6 y 30 caracteres de largo");
        }
        return true;
    }

    public static boolean verificarMismaContrasenia (String contrasenia1, String contrasenia2) throws NoCoincideException {
        if(!contrasenia1.equals(contrasenia2)){
            throw new NoCoincideException("Las contrasenias no coinciden");
        }
        return true;
    }
    public static boolean verificarSN(char caracter) throws FormatoInvalidoException {
        if(caracter != 's' && caracter != 'n') {
            throw new FormatoInvalidoException("Debe ingresar 's' o 'n'.");
        }
        return true;
    }
    public static EstadoLibro pedirEstado (Scanner scanner) {
        System.out.println("Ingrese 1 si quiere leer el libro.");
        System.out.println("Ingrese 2 si lo esta leyendo actualmente.");
        System.out.println("Ingrese 3 si ya lo termino de leer. \n");
        int opcion = 0;
        boolean flag = false;
        EstadoLibro estado = null;
        do {
            flag = true;
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1 -> estado = EstadoLibro.POR_LEER;
                case 2 -> estado = EstadoLibro.LEYENDO;
                case 3 -> estado = EstadoLibro.LEIDO;
                default -> flag = false;
            }
        } while (!flag);
        return estado;
    }

}

