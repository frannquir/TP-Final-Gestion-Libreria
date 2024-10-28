package Excepciones;

public class FormatoInvalidoException extends RuntimeException{
    public FormatoInvalidoException(){
        super("Formato invalido");
    }
    public FormatoInvalidoException(String mensaje){
        super(mensaje);
    }
}
