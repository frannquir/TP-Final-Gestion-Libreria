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
    // Para deserializar un ENUM, es necesario este metodo. Convierte un String a un estado.
    public static EstadoLibro fromString(String estado) {
        for (EstadoLibro e : EstadoLibro.values()) {
            if (e.getEstadoLibro().equalsIgnoreCase(estado)) {
                return e;
            }
        }
        throw new IllegalArgumentException("No se encontro el estado: " + estado);
    }
}
