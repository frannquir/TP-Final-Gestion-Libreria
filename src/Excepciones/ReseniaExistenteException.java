package Excepciones;

public class ReseniaExistenteException extends RuntimeException{
    private String mensaje;
    public ReseniaExistenteException(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje () {
        return mensaje;
    }
}