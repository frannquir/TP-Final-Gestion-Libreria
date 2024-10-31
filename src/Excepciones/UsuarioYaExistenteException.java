package Excepciones;

public class UsuarioYaExistenteException extends RuntimeException{
    public UsuarioYaExistenteException(String mensaje) {
       super(mensaje);
    }
    public UsuarioYaExistenteException(){
        super("Este usuario ya esta registrado");
    }
}

