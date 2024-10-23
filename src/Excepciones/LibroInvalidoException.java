package Excepciones;

public class LibroInvalidoException extends RuntimeException {
  public LibroInvalidoException(String message) {
    super(message);
  }
}
