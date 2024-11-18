package Handlers;

import Bibliotecas.ColeccionGenerica;
import Libros.Libro;
import Libros.Resenia;
import Usuarios.UsuarioPremium;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import Usuarios.Usuario;

import java.util.*;

public class JSONUtiles {
    // Metodo para pasar de JSONObject a Java Libro Object
    public static Libro parseJsonLibro(JSONObject jsonLibro) throws JSONException {
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
    public static ColeccionGenerica<Libro> parseJsonListaLibros(JSONObject jsonResponse) throws JSONException {
        var listaLibros = new ColeccionGenerica<Libro>();
        if (!jsonResponse.has("items")) {
            return listaLibros; // Retorna una coleccion vacia si no hay resultados
        }
        JSONArray listaJSON = jsonResponse.getJSONArray("items");
        // Recorremos la listaJSON que tiene varios libros
        for (int i = 0; i < listaJSON.length(); i++) {
            // Obtenemos un libro de la lista
            JSONObject jsonActual = listaJSON.getJSONObject(i);
            Libro libro = parseJsonLibro(jsonActual);
            // Aniadimos el libro a la lista
            listaLibros.agregar(libro);
        }
        return listaLibros;
    }

    // Convierte una lista de usuarios en un JSONObject con dos arrays (free y premium)
    public static JSONObject listaUsuariosAJson(List<Usuario> usuarios) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray usuariosFree = new JSONArray();
        JSONArray usuariosPremium = new JSONArray();

        for (Usuario usuario : usuarios) {
            if (usuario instanceof UsuarioPremium) {
                usuariosPremium.put(usuario.toJSON());
            } else {
                usuariosFree.put(usuario.toJSON());
            }
        }

        jsonObject.put("usuariosFree", usuariosFree);
        jsonObject.put("usuariosPremium", usuariosPremium);
        return jsonObject;
    }

    // Convierte un JSONObject con dos arrays en una unica lista de usuarios
    public static List<Usuario> jsonAListaUsuarios(JSONObject jsonObject) throws JSONException {
        List<Usuario> usuarios = new ArrayList<>();

        // Convertir usuarios free
        JSONArray usuariosFree = jsonObject.getJSONArray("usuariosFree");
        for (int i = 0; i < usuariosFree.length(); i++) {
            Usuario usuario = new Usuario();
            usuario.fromJSON(usuariosFree.getJSONObject(i));
            usuarios.add(usuario);
        }

        // Convertir usuarios premium
        JSONArray usuariosPremium = jsonObject.getJSONArray("usuariosPremium");
        for (int i = 0; i < usuariosPremium.length(); i++) {
            var usuario = new UsuarioPremium();
            usuario.fromJSON(usuariosPremium.getJSONObject(i));
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public static JSONObject listaLibrosAJson(ColeccionGenerica<Libro> libros) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        JSONArray librosArray = new JSONArray();

        for (Libro libro : libros) {
            librosArray.put(libro.toJSON());
        }

        jsonObject.put("libros", librosArray);
        return jsonObject;
    }

    public static ColeccionGenerica<Libro> jsonAListaLibros(JSONObject jsonObject) throws JSONException {
        ColeccionGenerica<Libro> libros = new ColeccionGenerica<>();
        JSONArray librosArray = jsonObject.getJSONArray("libros");

        for (int i = 0; i < librosArray.length(); i++) {
            Libro libro = new Libro();
            libro.fromJSON(librosArray.getJSONObject(i));
            libros.agregar(libro);
        }

        return libros;
    }

    // Convierte nuestro mapa de resenias a un JSONObject, en el cual cada array es la coleccion de resenias de un usuario.
    public static JSONObject mapaReseniasAJSON(HashMap<String, ColeccionGenerica<Resenia>> mapa) throws JSONException {
        var mapaJSON = new JSONObject();
        for (Map.Entry<String, ColeccionGenerica<Resenia>> entry : mapa.entrySet()) {
            var coleccionJSON = new JSONArray();
            var resenias = entry.getValue();
            for (Resenia resenia : resenias) {
                coleccionJSON.put(resenia.toJSON());
            }
            mapaJSON.put(entry.getKey(), coleccionJSON);
        }
        return mapaJSON;
    }

    // Convierte un JSONObject de resenias a un HashMap.
    public static HashMap<String, ColeccionGenerica<Resenia>> jsonAMapaResenias(JSONObject mapaJSON) throws JSONException {
        var mapa = new HashMap<String, ColeccionGenerica<Resenia>>();
        // la mejor manera que encontre para recorrer un jsonobject.
        Iterator<String> keys = mapaJSON.keys();
        while (keys.hasNext()) {
            String email = keys.next();
            JSONArray coleccionJSON = mapaJSON.getJSONArray(email);
            var resenias = new ColeccionGenerica<Resenia>();

            for (int i = 0; i < coleccionJSON.length(); i++) {
                JSONObject reseniaJSON = coleccionJSON.getJSONObject(i);
                var resenia = new Resenia();
                resenia.fromJSON(reseniaJSON);
                resenias.agregar(resenia);
            }

            mapa.put(email, resenias);
        }

        return mapa;
    }

    // Convierte una coleccion generica de libros a un JSON
    public static JSONObject bibliotecaAJSON(ColeccionGenerica<Libro> biblioteca) throws JSONException {
        var jo = new JSONObject();
        var coleccionJSON = new JSONArray();
        for (Libro libro : biblioteca) {
            coleccionJSON.put(libro.toJSON());
        }
        jo.put("libros", coleccionJSON);
        return jo;
    }

    // Convierte una biblioteca JSON a una coleccion generica de libros.
    public static ColeccionGenerica<Libro> jsonABiblioteca(JSONObject bibliotecaJSON) throws JSONException{
        var biblioteca = new ColeccionGenerica<Libro>();
        var coleccionJSON = new JSONArray(bibliotecaJSON.getJSONArray("libros"));
        for (int i = 0; i < coleccionJSON.length(); i++) {
            var libroJSON = coleccionJSON.getJSONObject(i);
            var libro = new Libro();
            libro.fromJSON(libroJSON);
            biblioteca.agregar(libro);
        }
        return biblioteca;
    }
}