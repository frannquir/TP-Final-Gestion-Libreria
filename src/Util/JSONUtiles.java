package Util;

import Libros.Libro;
import Usuarios.UsuarioFree;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import Usuarios.Usuario;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONTokener;

public class JSONUtiles {
    // Pasar de JSONObject a Java Libro Object
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
    public static ArrayList<Libro> parseJsonListaLibros(JSONObject jsonResponse) throws JSONException {
        ArrayList<Libro> listaLibros = new ArrayList<>();
        JSONArray listaJSON = jsonResponse.getJSONArray("items");
        // Recorremos la listaJSON que tiene varios libros
        for (int i = 0; i < listaJSON.length(); i++) {
            // Obtenemos un libro de la lista
            JSONObject jsonActual = listaJSON.getJSONObject(i);
            Libro libro = parseJsonLibro(jsonActual);
            // Aniadimos el libro a la lista
            listaLibros.add(libro);
        }
        return listaLibros;
    }

    public static JSONObject usuarioAJson(Usuario usuario) throws JSONException {
        JSONObject jsonUsuario = new JSONObject();
        jsonUsuario.put("email", usuario.getEmail());
        jsonUsuario.put("nombreUsuario", usuario.getNombreUsuario());
        jsonUsuario.put("identificador", usuario.getIdentificador());
        jsonUsuario.put("contrasenia", usuario.getContrasenia());
        return jsonUsuario;
    }

    public static Usuario jsonAUsuario(JSONObject jsonUsuario) throws JSONException {
        return new UsuarioFree(
                jsonUsuario.getString("email"),
                jsonUsuario.getString("nombreUsuario"),
                jsonUsuario.getString("identificador"),
                jsonUsuario.getString("contrasenia")
        );
    }

    public static JSONArray listaUsuariosAJson(List<Usuario> usuarios) throws JSONException {
        JSONArray jsonArray = new JSONArray();
        for (Usuario usuario : usuarios) {
            jsonArray.put(usuarioAJson(usuario));
        }
        return jsonArray;
    }

    public static List<Usuario> jsonAListaUsuarios(JSONArray jsonArray) throws JSONException {
        List<Usuario> usuarios = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            usuarios.add(jsonAUsuario(jsonArray.getJSONObject(i)));
        }
        return usuarios;
    }

    public static void guardarJSON(JSONObject jsonObject, String archivo) throws IOException {
        FileWriter save = null;
        try {
            save = new FileWriter(archivo);
            save.write(jsonObject.toString(2));
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo JSON" + e.getMessage());
        } finally {
            save.flush();
            save.close();
        }
    }

    public static JSONObject leerJSON(String archivo) throws Exception {
        FileReader reader = null;
        try {
            reader = new FileReader(archivo);
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONObject(tokener);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Sucedio un error inesperado");
        } finally {
            reader.close();
        }
    }

    public static List<Usuario> leerListaUsuarios(String archivo) throws Exception {
        FileReader reader = null;
        try {
            reader = new FileReader(archivo);
            JSONTokener tokener = new JSONTokener(reader);
            JSONArray jsonArray = new JSONArray(tokener);
            List<Usuario> usuarios = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                usuarios.add(jsonAUsuario(jsonArray.getJSONObject(i)));
            }
            return usuarios;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (JSONException e) {
            throw new JSONException("No se pudo acceder al JSON");
        } catch (Exception e) {
            throw new Exception("Sucedio un error inesperado");
        } finally {
            reader.close();
        }
    }

    public static void guardarListaUsuarios(List<Usuario> usuarios, String archivo) throws Exception {
        FileWriter save = null;
        try {
            save = new FileWriter(archivo);
            JSONArray jsonUsuarios = new JSONArray();
            for (Usuario usuario : usuarios) {
                jsonUsuarios.put(usuarioAJson(usuario));
            }
            save.write(jsonUsuarios.toString(2));
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        } catch (Exception e) {
            throw new Exception("Sucedio un error inesperado");
        } finally {
            save.flush();
            save.close();
        }
    }
}

