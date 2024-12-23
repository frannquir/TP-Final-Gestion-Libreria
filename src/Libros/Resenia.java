package Libros;


import Interfaces.IMostrable;
import Interfaces.IToJson;
import Interfaces.Identificable;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class Resenia implements Identificable, IToJson{
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

    public Resenia() {
        this(null, 0, "", "");
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

    @Override
    public JSONObject toJSON() throws JSONException {
        var jo = new JSONObject();
        jo.put("estado", estadoLibro.getEstadoLibro());
        jo.put("rating", rating);
        jo.put("comentario", comentario);
        jo.put("isbn", isbn);
        return jo;
    }

    @Override
    public void fromJSON(JSONObject jo) throws JSONException {
        String estadoStr = jo.getString("estado");
        setEstadoLibro(EstadoLibro.fromString(estadoStr));
        setRating(jo.getInt("rating"));
        setComentario(jo.getString("comentario"));
        setIsbn(jo.getString("isbn"));
    }
    public String mostrar() {
        var msj = new StringBuilder();
        msj.append("--- Reseña del Libro ---\n")
                .append("ISBN: ").append(getIsbn()).append("\n")
                .append("Estado: ").append(getEstadoLibro().getEstadoLibro()).append("\n")
                .append("Calificacion: ").append(getRating()).append(" ★\n")
                .append("Comentario: ").append(getComentario());
        return msj.toString();
    }
}
