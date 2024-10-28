package Excepciones;

public class NoCoincideException extends RuntimeException{
    public NoCoincideException(){
        super("No coinciden los datos");
    }
    public NoCoincideException(String mensaje){
        super(mensaje);
    }
}
