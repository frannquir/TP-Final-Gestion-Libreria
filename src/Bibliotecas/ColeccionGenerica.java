package Bibliotecas;

import Interfaces.Identificable;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;
import java.util.Set;

// Coleccion generica para guardar libros y resenias
public class ColeccionGenerica<T extends Identificable> implements Iterable<T> {
    private Set<T> coleccion;

    public ColeccionGenerica() {
        coleccion = new LinkedHashSet<>();
    }
    /* El metodo de agregar no lanza excepcion, ya que cuando dos usuarios guardan un libro
    no deberia avisarle que este no fue agregado a la biblioteca de libros */

    public void agregar(T t) {
        coleccion.add(t);
    }

    /* Al ser diferentes las excepciones posibles para eliminar un Libro o una Resenia, la responsabilidad
        es del metodo eliminarResenia */
    public void eliminar(String isbn) {
        T t = buscar(isbn);
        coleccion.remove(t);
    }

    public T buscar(String isbn) {
        T found = null;
        for (T t : coleccion) {
            // verifico que el ISBN del elemento no sea null antes de hacer equals
            if (t.getIsbn() != null && t.getIsbn().equals(isbn)) {
                found = t;
                break;  // salgo del bucle una vez encontrado
            }
        }
        return found;
    }

    public String listar() {
        var mensaje = new StringBuilder();
        for (T t : coleccion) {
            mensaje.append(t.mostrar()).append('\n');
        }
        return mensaje.toString();
    }

    public boolean estaVacia() {
        return coleccion.isEmpty();
    }

    public int tamanio() {
        return coleccion.size();
    }

    @Override
    public Iterator<T> iterator() {
        return coleccion.iterator();
    }
}
