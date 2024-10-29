package Bibliotecas;

import Excepciones.*;
import Libros.Libro;
import Libros.Resenia;

import java.util.HashMap;
import java.util.List;

public class BibliotecaGenerica <T extends Resenia> { ///Clase en proceso.... ¿o no? 🤔
    private List<T> biblioteca;


    public void agregarResenia (T t) {
        if(biblioteca.contains(t)) {
            throw new ReseniaExistenteException("Ya existe esta resenia, si quiere, puede modificarla.");
        }
        biblioteca.add(t);
    }
    public boolean eliminarResenia (T t) {
        return biblioteca.remove(t);
    }

}
