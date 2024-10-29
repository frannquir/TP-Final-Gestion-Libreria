package Excepciones;

public class BibliotecaNoEncontradaException extends RuntimeException {
    private String mensaje;
    public BibliotecaNoEncontradaException(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje () {
        return mensaje;
    }
}
