package Excepciones;

public class LibroNoEncontradoException extends RuntimeException {
    public LibroNoEncontradoException(String message) {
        super(message);
    }
}
