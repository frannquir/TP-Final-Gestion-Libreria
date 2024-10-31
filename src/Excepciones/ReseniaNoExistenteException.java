package Excepciones;

public class ReseniaNoExistenteException extends RuntimeException{
    public ReseniaNoExistenteException(String mensaje) {
        super(mensaje);
    }
    public ReseniaNoExistenteException(){
        super("No existe la resenia");
    }
}
