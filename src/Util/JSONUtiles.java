package Util;
import Libro.Libro;
import Excepciones.LibroInvalidoException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONUtiles {

    // Metodo para pasar Libro.Libro en JSON a Libro.Libro en Objeto Java
    public Libro parseJsonBook(JSONObject jsonLibro) {
        try {
            String autoresString = jsonLibro.getString("authors");
            ArrayList<String> autores = separarAutores(autoresString);
            return new Libro(
                    jsonLibro.getString("title"),
                    jsonLibro.getInt("isbn"),
                    jsonLibro.getInt("pages"),
                    jsonLibro.getInt("year"),
                    autores
            );
        } catch (JSONException e) {
            throw new RuntimeException("Error al parsear el libro", e); // Utilizo e y no e.getMessage() para mantener el trazo de errores.
        }
    }

    // Metodo para separar los autores, ya que la API los devuelve como un string.
    public ArrayList<String> separarAutores(String autores) {
        if (autores == null || autores.trim().isEmpty()) {
            return new ArrayList<>();
        }
        String[] arrayAutores = autores.split(",\\s*");
        return new ArrayList<>(Arrays.asList(arrayAutores));
    }

    // Metodo para unir autores, deberiamos preguntar si es necesario guardarlo como JSONArray.
    public String unirAutores(ArrayList<String> autores) {
        if (autores == null || autores.isEmpty()) {
            return "";
        }
        return String.join(", ", autores);
    }

    // Metodo para pasar objeto java Libro.Libro a JSONObject
    public JSONObject libroAJson(Libro libro) {
        try {
            JSONObject jsonLibro = new JSONObject();
            jsonLibro.put("title", libro.getTitulo());
            jsonLibro.put("isbn", libro.getIsbn());
            jsonLibro.put("pages", libro.getNumPaginas());
            jsonLibro.put("year", libro.getAnioPublicacion());
            jsonLibro.put("authors", unirAutores(libro.getAutores()));
            return jsonLibro;
        } catch (JSONException e) {
            throw new RuntimeException("Error al convertir libro a JSON", e); // Utilizo e y no e.getMessage() para mantener el trazo de errores.
        }
    }
// Metodo para pasar lista de libros en JSON a ArrayList de libros
    public ArrayList<Libro> parseJsonLibros(String jsonResponse) throws LibroInvalidoException {
        ArrayList<Libro> libros = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            // Falta verificar si esta key es correcta, no se conoce el JSON por completo.
            JSONArray jsonLibros = jsonObject.getJSONArray("libros");

            for (int i = 0; i < jsonLibros.length(); i++) {
                JSONObject jsonLibro = jsonLibros.getJSONObject(i);
                Libro libro = parseJsonBook(jsonLibro);

                if (libro == null) {
                    throw new LibroInvalidoException("Libro.Libro invalido en posicion " + i);
                }
                libros.add(libro);
            }
            return libros;
        } catch (JSONException e) {
            throw new RuntimeException("Error al parsear la lista de libros", e);
        }
    }
// Metodo para pasar ArrayList de libros a un JSONObject con
    public JSONObject listaLibrosAJson (ArrayList<Libro> libros) throws LibroInvalidoException{
        JSONObject respuestaJson = new JSONObject();
        JSONArray jsonLibros = new JSONArray();
        try {
            for (Libro libro : libros) {
                JSONObject jsonLibro = libroAJson(libro);
                if (jsonLibro == null) {
                    throw new LibroInvalidoException("El libro es invalido");
                }
                jsonLibros.put(jsonLibro);
            }
            respuestaJson.put("libros", jsonLibros);
            return respuestaJson;
        } catch (JSONException e) {
            throw new RuntimeException("Error al convertir la lista de libros a JSON", e);
        }
    }
}
