package Libros;

import Interfaces.IToJson;
import Interfaces.Identificable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Libro implements IToJson, Identificable {
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

    @Override
    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("titulo", titulo);
        json.put("isbn", isbn);
        json.put("numPaginas", numPaginas);
        json.put("anioPublicacion", anioPublicacion);

        JSONArray jsonAutores = new JSONArray();
        for (String autor : autores) {
            jsonAutores.put(autor);
        }
        json.put("autores", jsonAutores);

        return json;
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        setTitulo(jsonObject.getString("titulo"));
        setIsbn(jsonObject.getString("isbn"));
        setNumPaginas(jsonObject.getInt("numPaginas"));
        setAnioPublicacion(jsonObject.getInt("anioPublicacion"));

        JSONArray jsonAutores = jsonObject.getJSONArray("autores");
        ArrayList<String> autores = new ArrayList<>();
        for (int i = 0; i < jsonAutores.length(); i++) {
            autores.add(jsonAutores.getString(i));
        }
        setAutores(autores);
    }
}
