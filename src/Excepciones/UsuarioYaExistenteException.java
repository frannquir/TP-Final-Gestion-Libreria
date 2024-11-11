package Excepciones;

public class UsuarioYaExistenteException extends Exception{
    public UsuarioYaExistenteException(String mensaje) {
       super(mensaje);
    }
    public UsuarioYaExistenteException(){
        super("Este usuario ya esta registrado");
    }
}

