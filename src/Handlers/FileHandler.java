package Handlers;

import Libros.Libro;
import Usuarios.Usuario;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileHandler {
    public static void guardarJSON(JSONObject jsonObject, String archivo) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(archivo);
            writer.write(jsonObject.toString(2));
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo JSON: " + e.getMessage());
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public static JSONObject leerJSON(String archivo) throws Exception {
        FileReader reader = null;
        try {
            reader = new FileReader(archivo);
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONObject(tokener);
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage());
        } catch (JSONException e) {
            throw new JSONException("Error en el formato JSON: " + e.getMessage());
        } finally {
            reader.close();
        }
    }

    public static List<Usuario> leerListaUsuarios(String archivo) throws Exception {
        JSONObject jsonObject = leerJSON(archivo);
        return JSONUtiles.jsonAListaUsuarios(jsonObject);
    }

    public static void guardarListaUsuarios(List<Usuario> usuarios, String archivo) throws Exception {
        JSONObject jsonObject = JSONUtiles.listaUsuariosAJson(usuarios);
        guardarJSON(jsonObject, archivo);
    }

    public static void guardarListaLibros(List<Libro> libros, String archivo) throws Exception {
        JSONObject jsonObject = JSONUtiles.listaLibrosAJson(libros);
        guardarJSON(jsonObject, archivo);
    }

    public static List<Libro> leerListaLibros(String archivo) throws Exception {
        JSONObject jsonObject = leerJSON(archivo);
        return JSONUtiles.jsonAListaLibros(jsonObject);
    }
}