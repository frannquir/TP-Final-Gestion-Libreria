package Handlers;

import Excepciones.FormatoInvalidoException;
import Excepciones.NoCoincideException;
import Libros.EstadoLibro;

import java.util.Scanner;


public class Helper {
    public static boolean verificarNombre(String nombre) throws FormatoInvalidoException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new FormatoInvalidoException("El nombre no puede estar vacio");
        }
        // Patron que debe cumplir el nombre para ser registrado.
        if (!nombre.matches("^[a-zA-Z][a-zA-Z0-9]*$")) {
            throw new FormatoInvalidoException("El nombre debe comenzar con una letra");
        }
        if (verificarLongitud(3, 16, nombre.length())) {
            throw new FormatoInvalidoException("El nombre debe tener entre 3 o 16 letras");
        }
        return true;
    }

    public static boolean verificarLongitud(int minima, int maxima, int longitud) {
        boolean rta = false;
        if (longitud > maxima || longitud < minima) {
            rta = true;
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

        if (verificarLongitud(16, 64, email.length()))
            throw new FormatoInvalidoException("El correo debe tener entre 16 y 64 caracteres.");

        if (!email.endsWith("@gmail.com"))
            throw new FormatoInvalidoException("Solo aceptamos correos registrados en gmail.");

        return true;
    }

    public static boolean verificarContrasenia(String contrasenia) throws FormatoInvalidoException {
        if (verificarLongitud(6, 30, contrasenia.length())) {
            throw new FormatoInvalidoException("La contrasenia debe tener entre 6 y 30 caracteres de largo");
        }
        return true;
    }

    public static boolean verificarMismaContrasenia(String contrasenia1, String contrasenia2) throws NoCoincideException {
        if (!contrasenia1.equals(contrasenia2)) {
            throw new NoCoincideException("Las contrasenias no coinciden");
        }
        return true;
    }

    public static boolean verificarSN(char caracter) throws FormatoInvalidoException {
        if (caracter != 's' && caracter != 'n') {
            throw new FormatoInvalidoException("Debe ingresar 's' o 'n'.");
        }
        return true;
    }

    /**
     * Valida y convierte una opcion a EstadoLibro
     * @param opcion numero que representa el estado (1-3)
     * @return EstadoLibro correspondiente
     * @throws FormatoInvalidoException si la opcion es inválida
     */
    public static EstadoLibro validarEstado(int opcion) throws FormatoInvalidoException {
        // el return switch nos permite retornar lo que este dentro del switch. las flechas hacen que cada caso sea mutuamente exclusivo, sin necesidad de break.
        return switch (opcion) {
            case 1 -> EstadoLibro.POR_LEER;
            case 2 -> EstadoLibro.LEYENDO;
            case 3 -> EstadoLibro.LEIDO;
            default -> throw new FormatoInvalidoException("Opción inválida. Debe ser 1, 2 o 3");
        };
    }

    /**
     * Valida el rating de un libro
     * @param rating Rating a validar
     * @throws FormatoInvalidoException si el rating no esta entre 0 y 5
     */
    public static boolean verificarRating(int rating) throws FormatoInvalidoException {
        if (rating < 0 || rating > 5) {
            throw new FormatoInvalidoException("El rating debe estar entre 0 y 5");
        }
        return true;
    }

    /**
     * Valida que el comentario no exceda el limite de caracteres
     * @param comentario Comentario a validar
     * @throws FormatoInvalidoException si el comentario excede el limite
     */
    public static boolean verificarComentario(String comentario) throws FormatoInvalidoException {
        if (comentario == null) {
            throw new FormatoInvalidoException("El comentario no puede ser null");
        }
        if (verificarLongitud(0, 250, comentario.length())) {
            throw new FormatoInvalidoException("El comentario no puede exceder los 250 caracteres");
        }
        return true;
    }

}

