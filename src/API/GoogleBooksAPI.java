package API;
import Excepciones.LibroNoEncontradoException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.net.URI;



public class GoogleBooksAPI {

    private static final String API_KEY = "AIzaSyB1kN5ExlkBre860cEBUfrSJYxBHMuRemc";
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private final HttpClient client;

    public GoogleBooksAPI () {
        this.client = HttpClient.newHttpClient();
    }

    public JSONObject buscarLibro (String query) throws IllegalArgumentException,InterruptedException, IOException {
        if (query == null || query.trim().isEmpty()) {
            throw new IllegalArgumentException("La busqueda no puede estar vacia");
        }
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8); // Pasa el query al formato correcto para la busqueda
        String url = BASE_URL + "?q=" + encodedQuery + "&key=" + API_KEY + "&maxResults=10";

        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return new JSONObject(response.body());
    }


    public JSONObject buscarPorISBN (String isbn) throws IllegalArgumentException,InterruptedException, IOException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("El ISBN no puede estar vacio");
        }
        return buscarLibro("isbn:" + isbn);
    }


    public JSONObject buscarPorAutor (String autor) throws IllegalArgumentException,InterruptedException, IOException {
        if (autor == null || autor.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        /* usamos "inauthor" ya que es un operador de busqueda especifico de la API de Google Books.
        "inauthor" le dice a la API que busque especificamente en el campo de autores.
         No podemos usar "author" o "authors" ya que los autores estan guardados como un JSONArray.*/
        return buscarLibro("inauthor:" + autor);
    }


    public JSONObject buscarPorTitulo (String titulo) throws IllegalArgumentException,InterruptedException, IOException {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }
        return buscarLibro("intitle:" + titulo);
    }
}
