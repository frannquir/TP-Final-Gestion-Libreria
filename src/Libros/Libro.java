package Libros;

import java.util.ArrayList;
import java.util.Objects;
import Enum.EstadoLibro;
public class Libro {
    private String titulo;
    private String isbn;
    private Integer numPaginas;
    private Integer anioPublicacion;
    private ArrayList<String> autores;

    public Libro(String titulo, String isbn, Integer numPaginas, Integer anioPublicacion, ArrayList<String> autores) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.numPaginas = numPaginas;
        this.anioPublicacion = anioPublicacion;
        this.autores = autores;
    }
    public Libro() {
        this("", "", 0, 0, null);
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getNumPaginas() {
        return numPaginas;
    }

    public void setNumPaginas(Integer numPaginas) {
        this.numPaginas = numPaginas;
    }

    public Integer getAnioPublicacion() {
        return anioPublicacion;
    }

    public void setAnioPublicacion(Integer anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }

    public ArrayList<String> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<String> autores) {
        this.autores = autores;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "titulo='" + titulo + '\'' +
                ", isbn=" + isbn +
                ", numPaginas=" + numPaginas +
                ", anioPublicacion=" + anioPublicacion +
                ", autores=" + autores +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro libro)) return false;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }
}
