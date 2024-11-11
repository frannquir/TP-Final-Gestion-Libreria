package Excepciones;

public class UsuarioNoDadoDeBajaException extends Exception{
    public UsuarioNoDadoDeBajaException(){
        super("El usuario se encuentra activo");
    }
    public UsuarioNoDadoDeBajaException(String mensaje){
        super(mensaje);
    }
}
