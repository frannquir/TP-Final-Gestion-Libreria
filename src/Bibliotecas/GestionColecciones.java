package Bibliotecas;

import Excepciones.LibroNoEncontradoException;
import Excepciones.ReseniaExistenteException;
import Libros.Libro;
import Libros.Resenia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/* Esta clase gestiona la biblioteca GENERAL, es decir, aquellos libros agregados por usuarios
y las colecciones de resenias de cada usuario. */
public class GestionColecciones {
    // Biblioteca general
    private ColeccionGenerica<Libro> biblioteca;
    /* Biblioteca de resenias de cada usuario.
    La clave (String) almacena los correos, y el valor (ColeccionGenerica) almacena las resenias. */
    private Map<String, ColeccionGenerica<Resenia>> resenias;

    public GestionColecciones() {
        biblioteca = new ColeccionGenerica<>();
        resenias = new HashMap<>();
    }

    /**
     * Este metodo agrega una resenia a una coleccion de un usuario, y agrega el libro a la biblioteca
     * para evitar pedidos redundantes a la API.
     *
     * @param email  Correo electronico del usuario, clave usada para ordenarlos.
     * @param isbn   ISBN del libro, usado para realizar la conexion de la resenia con el libro.
     * @param libros Coleccion temporal de libros, que proviene del metodo parseJsonListaLibro.
     */
    public void agregarResenia(String email, String isbn, ColeccionGenerica<Libro> libros) {
        if (!resenias.containsKey(email)) {
            System.out.println("Creando una biblioteca..");
            resenias.put(email, new ColeccionGenerica<>());
        }
        // Pregunta si el el libro ya esta agregado a su coleccion de resenias.
        if (resenias.get(email).buscar(isbn) != null) {
            /* Al ser capturada esta excepcion, el programa deberia darle la opcion al usuario de
            modificar la resenia, ya que no puede agregar dos veces el mismo libro.*/
            throw new ReseniaExistenteException("Debe modificar la resenia.");
        }
        resenias.get(email).agregar(new Resenia(isbn));
        // Se agrega el libro a la biblioteca general en caso de no existir.
        if (biblioteca.buscar(isbn) == null)
            biblioteca.agregar(libros.buscar(isbn));
    }

    /**
     * Este metodo deberia eliminar la resenia de la coleccion del usuario.
     * No deberia eliminar el libro de la biblioteca general, ya que este nos evita un pedido a la API,
     * en caso de que el usuario se haya arrepentido.
     *
     * @param email Correo electronico del usuario.
     * @param isbn  Identificador del libro a eliminar.
     */
    public void eliminarResenia(String email, String isbn) {
        // Si el libro no esta en la biblioteca general, es imposible que se encuentre en la biblioteca del usuario.
        if (biblioteca.buscar(isbn) == null)
            throw new LibroNoEncontradoException("El ISBN del libro no fue encontrado.");

        // Es lo mismo que el condicional anterior, pero concretamente en la biblioteca del usuario.
        if (resenias.get(email).buscar(isbn) == null)
            throw new LibroNoEncontradoException("No tenes este libro en tu biblioteca.");

        // Efectivamente se elimina la resenia.
        resenias.get(email).eliminar(isbn);
    }

    /* Este metodo modificar los atributos de una resenia. SIEMPRE que se agregue un libro a la biblioteca
    PERSONAL te va a ofrecer modificar la resenia, y le decimos modificar ya que esta ya fue creada con el
    ISBN, pero el usuario va a pensar que tiene agregado el libro, y la resenia no existe.
     */
    public void modificarResenia(String email, String isbn) {
        if (biblioteca.buscar(isbn) == null)
            throw new LibroNoEncontradoException("El ISBN del libro no fue encontrado.");
        if(resenias.get(email).buscar(isbn) == null)
            throw new LibroNoEncontradoException("No tenes este libro en tu biblioteca.");

    }
}


