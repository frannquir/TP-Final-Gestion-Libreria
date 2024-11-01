package Bibliotecas;

import Excepciones.*;
import Libros.Resenia;

import java.util.HashMap;

public class BibliotecaManager {
    // Guarda las resenias de cada usuario
    HashMap<String, BibliotecaGenerica<Resenia>> bibliotecas;
    // El String es el userId y el Boolean es true si el usuario es premium.
    HashMap<String, Boolean> planUsuarios;

    public BibliotecaManager() {
        bibliotecas = new HashMap<>();
        planUsuarios = new HashMap<>();
    }

    public void agregarLibro(String userId, Resenia resenia) {
        if (!bibliotecas.containsKey(userId)) {
            bibliotecas.put(userId, new BibliotecaGenerica<>());
        }
        bibliotecas.get(userId).agregarResenia(resenia);
    }

    public void eliminarLibro(String userId, Resenia resenia) throws BibliotecaNoEncontradaException {
        if(!bibliotecas.containsKey(userId)) {
            throw new BibliotecaNoEncontradaException("No tenes libros");
        }
        bibliotecas.get(userId).eliminarResenia(resenia);
    }

    public void agregarUsuario (String userId) {
        if(!planUsuarios.containsKey(userId)) {
            throw new UsuarioYaExistenteException("El usuario ya esta registrado");
        }
        planUsuarios.put(userId, false); // sin terminar
    }
}
