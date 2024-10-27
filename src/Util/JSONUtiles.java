package Util;

import Libro.Libro;
import Excepciones.LibroInvalidoException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

public class JSONUtiles {
    // Pasar de JSONObject a Java Libro Object
    public static Libro parseJsonLibro(JSONObject jsonLibro) throws JSONException{
        // Nos manejamos con volumeInfo que es un object que adentro tiene la informacion del libro.
        JSONObject volumeInfo = jsonLibro.getJSONObject("volumeInfo");
        // Obtener titulo
        String titulo = volumeInfo.getString("title");
        // Obtener autores
        ArrayList<String> autores = new ArrayList<>();
        if (volumeInfo.has("authors")) {
            JSONArray arrayAutores = volumeInfo.getJSONArray("authors");
            for (int i = 0; i < arrayAutores.length(); i++) {
                autores.add(arrayAutores.getString(i));
            }
        }
        // Algunos libros no tienen pageCount, por lo tanto, tiraria una JSONException, usando optInt, evita la excepcion y le asigna 0.
        int numPaginas = volumeInfo.optInt("pageCount", 0);
        // Obtenemos anio de publicacion, primero obtenemos la fecha y despues nos quedamos con el anio.
        String fechaPublicacion = volumeInfo.optString("publishedDate", "");
        // Simple operador ternario, si esta vacio, retorna 0 y sino, se queda con las primeras cuatro cifras (anio).
        int anioPublicacion = fechaPublicacion.isEmpty() ? 0 : Integer.parseInt(fechaPublicacion.substring(0, 4));
        /* La API de Google Books guarda diferentes tipos de isbn, buscamos especificamente
        el isbn_13, si buscamos todos los tipos, podriamos tener errores de repeticion en nuestro
        programa. En volumeInfo, tenemos industryIdentifiers, que guarda el isbn_type y el isbn en String.
        Si no existe lo guardo en 0. */
        String isbn = null;
        if (volumeInfo.has("industryIdentifiers")) {
            JSONArray identificadores = volumeInfo.getJSONArray("industryIdentifiers");
            for (int i = 0; i < identificadores.length(); i++) {
                JSONObject identificador = identificadores.getJSONObject(i);
                if (identificador.optString("type", "").equals("ISBN_13")) {
                    isbn = identificador.optString("identifier", "0");
                    break;
                }
            }
        }

        return new Libro(titulo, isbn, numPaginas, anioPublicacion, autores);
    }
    // Obtenemos un JSON con muchos libros y lo pasamos a un ArrayList de Libros, usando el metodo parseJsonLibro
    public static ArrayList<Libro> parseJsonListaLibros (JSONObject jsonResponse) throws JSONException {
        ArrayList<Libro> listaLibros = new ArrayList<>();
        JSONArray listaJSON = jsonResponse.getJSONArray("items");
        // Recorremos la listaJSON que tiene varios libros
        for(int i = 0; i < listaJSON.length() ; i++) {
                // Obtenemos un libro de la lista
                JSONObject jsonActual = listaJSON.getJSONObject(i);
                Libro libro = parseJsonLibro(jsonActual);
                // Aniadimos el libro a la lista
                listaLibros.add(libro);
        }
        return listaLibros;
    }
}