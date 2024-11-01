package Libros;

public enum EstadoLibro {
    LEIDO ("Leido"),
    LEYENDO ("Leyendo"),
    POR_LEER ("Por leer");

    private String estadoLibro;

    EstadoLibro(String estadoLibro) {
        this.estadoLibro = estadoLibro;
    }

    public String getEstadoLibro() {
        return estadoLibro;
    }
}
