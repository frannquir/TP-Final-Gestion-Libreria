package Excepciones;

public class UsuarioNoRegistradoException extends Exception{
    public UsuarioNoRegistradoException (){
        super("No existe un usuario registrado con ese correo");
    }
    public UsuarioNoRegistradoException(String mensaje){
        super(mensaje);
    }
}
