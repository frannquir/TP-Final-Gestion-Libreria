package Handlers;

import Bibliotecas.ColeccionGenerica;
import Libros.Libro;
import Libros.Resenia;
import Usuarios.Usuario;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {
    public static void guardarJSON(JSONObject jsonObject, String archivo) throws IOException {
        FileWriter writer = null;
        try {
            writer = new FileWriter(archivo);
            writer.write(jsonObject.toString(2));
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo JSON: " + e.getMessage());
        } finally {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        }
    }

    public static JSONObject leerJSON(String archivo) throws IOException {
        FileReader reader = null;
        try {
            reader = new FileReader(archivo);
            JSONTokener tokener = new JSONTokener(reader);
            return new JSONObject(tokener);
        } catch (IOException e) {
            throw new IOException("Error al leer el archivo: " + e.getMessage());
        } catch (JSONException e) {
            throw new IOException("Error en el formato JSON: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new IOException("Error al cerrar el archivo: " + e.getMessage());
                }
            }
        }
    }

    public static List<Usuario> leerListaUsuarios() throws IOException {
        try {
            JSONObject jsonObject = leerJSON("usuarios.json");
            return JSONUtiles.jsonAListaUsuarios(jsonObject);
        } catch (IOException e) {
            throw new IOException("Error al leer la lista de usuarios: " + e.getMessage());
        }
    }

    public static void guardarListaUsuarios(List<Usuario> usuarios) throws IOException {
        try {
            JSONObject jsonObject = JSONUtiles.listaUsuariosAJson(usuarios);
            guardarJSON(jsonObject, "usuarios.json");
        } catch (IOException e) {
            throw new IOException("Error al guardar la lista de usuarios: " + e.getMessage());
        }
    }

    public static void guardarListaLibros(ColeccionGenerica<Libro> libros) throws IOException {
        try {
            JSONObject jsonObject = JSONUtiles.listaLibrosAJson(libros);
            guardarJSON(jsonObject, "libros.json");
        } catch (IOException e) {
            throw new IOException("Error al guardar la lista de libros: " + e.getMessage());
        }
    }

    public static ColeccionGenerica<Libro> leerListaLibros() throws IOException {
        try {
            JSONObject jsonObject = leerJSON("libros.json");
            return JSONUtiles.jsonAListaLibros(jsonObject);
        } catch (IOException e) {
            throw new IOException("No se encontraron libros");
        }
    }
    public static void guardarMapaResenias(HashMap<String, ColeccionGenerica<Resenia>> resenias) throws IOException {
        try {
            JSONObject jsonObject = JSONUtiles.mapaReseniasAJSON(resenias);
            guardarJSON(jsonObject, "resenias.json");
        } catch (IOException e) {
            throw new IOException("Error al guardar el mapa de resenias: " + e.getMessage());
        }
    }

    public static HashMap<String, ColeccionGenerica<Resenia>> leerMapaResenias() throws IOException {
        try {
            JSONObject jsonObject = leerJSON("resenias.json");
            return JSONUtiles.jsonAMapaResenias(jsonObject);
        } catch (IOException e) {
            throw new IOException("No se encontraron resenias");
        }
    }
}
