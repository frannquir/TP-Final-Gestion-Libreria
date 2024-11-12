package Libros;


import Interfaces.Identificable;

import java.util.Objects;

public class Resenia implements Identificable {
    private EstadoLibro estadoLibro;
    private int rating;
    private String comentario;
    private String isbn;

    public Resenia(EstadoLibro estadoLibro, int rating, String comentario, String isbn) {
        this.estadoLibro = estadoLibro;
        this.rating = rating;
        this.comentario = comentario;
        this.isbn = isbn;
    }

    // Constructor de resenias para que el usuario pueda agregar un libro a su biblioteca y agregar una resenia despues.
    public Resenia(String isbn, EstadoLibro estadoLibro) {
        /* El estado por defecto de un libro es por leer, esto es algo que decidimos ya que nos
         parecio que si el usuario no le agrega rating ni comentario, significa que no lo leyo.*/
        this(estadoLibro, 0, "", isbn);
    }

    public EstadoLibro getEstadoLibro() {
        return estadoLibro;
    }

    public void setEstadoLibro(EstadoLibro estadoLibro) {
        this.estadoLibro = estadoLibro;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resenia resenia = (Resenia) o;
        return Objects.equals(isbn, resenia.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "Resenia{" +
                "estadoLibro=" + estadoLibro +
                ", rating=" + rating +
                ", comentario='" + comentario + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }

}
