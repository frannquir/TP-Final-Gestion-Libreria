package Excepciones;

public class BibliotecaNoEncontradaException extends Exception {
    public BibliotecaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
   public BibliotecaNoEncontradaException(){
        super("No se encontro la biblioteca");
   }
}
