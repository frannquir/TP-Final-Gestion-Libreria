package Excepciones;

public class ReseniaNoExistenteException extends RuntimeException{
    private String mensaje;
    public ReseniaNoExistenteException(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje () {
        return mensaje;
    }
}
