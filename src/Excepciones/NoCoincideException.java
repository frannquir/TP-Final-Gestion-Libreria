package Excepciones;

public class NoCoincideException extends Exception{
    public NoCoincideException(){
        super("No coinciden los datos");
    }
    public NoCoincideException(String mensaje){
        super(mensaje);
    }
}
