package Excepciones;

public class UsuarioYaExistenteException extends RuntimeException{
    private String mensaje;
    public UsuarioYaExistenteException(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje () {
        return mensaje;
    }
}

