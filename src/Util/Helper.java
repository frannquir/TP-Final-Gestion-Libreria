package Util;

import Excepciones.FormatoInvalidoException;
import Excepciones.NoCoincideException;


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

        if (!email.endsWith("@gmail.com"))
            throw new FormatoInvalidoException("Solo aceptamos correos registrados en gmail.");

        if (verificarLongitud(16,64,email.length()))
            throw new FormatoInvalidoException("El correo debe tener entre 16 y 64 letras.");

        if (email.startsWith("."))
            throw new FormatoInvalidoException("El correo no puede empezar con punto.");

        if (email.contains(".."))
            throw new FormatoInvalidoException("El correo no puede tener puntos consecutivos");

        if (!email.matches(patron))
            throw new FormatoInvalidoException("Solo podes usar letras, numeros y puntos");

        return true;
    }
    public static boolean verificarContrasenia (String contrasenia) throws FormatoInvalidoException{
        if(verificarLongitud(6,30,contrasenia.length()))
        {
            throw new FormatoInvalidoException("La contrasenia debe tener entre 6 y 30 caracteres de largo");
        }
        return true;
    }

    public static boolean verificarMismaContrasenia (String contrasenia1, String contrasenia2){
        if(!contrasenia1.equals(contrasenia2)){
            throw new NoCoincideException("Las contrasenias no coinciden");
        }
        return true;
    }
}

