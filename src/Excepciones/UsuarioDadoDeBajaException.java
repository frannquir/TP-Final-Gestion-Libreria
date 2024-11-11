package Excepciones;

public class UsuarioDadoDeBajaException extends Exception{
    public UsuarioDadoDeBajaException(){
        super("El usuario se encuentra dado de baja");
    }
    public UsuarioDadoDeBajaException(String mensaje){
        super(mensaje);
    }
}
