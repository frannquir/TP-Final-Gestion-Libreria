package Excepciones;

public class DineroInsuficienteException extends Exception{
    public DineroInsuficienteException(String mensaje) {
        super (mensaje);
    }

    public DineroInsuficienteException (){
        super ("Dinero insuficiente.");
    }
}
