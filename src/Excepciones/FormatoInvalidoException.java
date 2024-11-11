package Excepciones;

public class FormatoInvalidoException extends Exception{
    public FormatoInvalidoException(){
        super("Formato invalido");
    }
    public FormatoInvalidoException(String mensaje){
        super(mensaje);
    }
}
