package Excepciones;

public class ReseniaExistenteException extends Exception{
    public ReseniaExistenteException(String mensaje) {
        super(mensaje);
    }
    public ReseniaExistenteException() {
        super("Ya existe la resenia");
    }
}