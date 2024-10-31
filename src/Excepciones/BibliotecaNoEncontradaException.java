package Excepciones;

public class BibliotecaNoEncontradaException extends RuntimeException {
    public BibliotecaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
   public BibliotecaNoEncontradaException(){
        super("No se encontro la biblioteca");
   }
}
