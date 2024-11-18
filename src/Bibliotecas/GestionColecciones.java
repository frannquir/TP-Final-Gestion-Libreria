package Bibliotecas;

import Excepciones.BibliotecaNoEncontradaException;
import Excepciones.LibroNoEncontradoException;
import Excepciones.LimiteReseniasException;
import Excepciones.ReseniaExistenteException;
import Handlers.FileHandler;
import Handlers.Helper;
import Handlers.SesionActiva;
import Libros.EstadoLibro;
import Libros.Libro;
import Libros.Resenia;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Gestiona la biblioteca general y las colecciones de resenias de usuarios.
 * Mantiene un registro centralizado de libros y permite a cada usuario
 * tener su propia coleccion de reseñas.
 */
public class GestionColecciones {
    // Biblioteca general
    private ColeccionGenerica<Libro> biblioteca;
    /* Biblioteca de resenias de cada usuario.
    La clave (String) almacena los correos, y el valor (ColeccionGenerica) almacena las resenias. */
    private HashMap<String, ColeccionGenerica<Resenia>> resenias;

    public GestionColecciones() {
        biblioteca = new ColeccionGenerica<>();
        try {
            biblioteca = FileHandler.leerListaLibros();
        } catch (IOException e) {
            System.out.println("Error al cargar biblioteca: " + e.getMessage());
        }

        try {
            resenias = FileHandler.leerMapaResenias();
        } catch (IOException e) {
            System.out.println("Error al cargar resenias: " + e.getMessage());
            resenias = new HashMap<>();
        }
    }

    /**
     * Este metodo agrega una resenia a una coleccion de un usuario, y agrega el libro a la biblioteca
     * para evitar pedidos redundantes a la API.
     *
     * @param email  Correo electronico del usuario, clave usada para ordenarlos.
     * @param isbn   ISBN del libro, usado para realizar la conexion de la resenia con el libro.
     * @param estado Estado del libro, establecido obligatoriamente por el usuario.
     * @param libros Coleccion temporal de libros, que proviene del metodo parseJsonListaLibro.
     */
    public void agregarResenia(String email, String isbn, EstadoLibro estado, ColeccionGenerica<Libro> libros) throws LimiteReseniasException, ReseniaExistenteException {
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
        if (!SesionActiva.esUsuarioPremium() && resenias.get(email).tamanio() >= 10) {
            throw new LimiteReseniasException("Alcanzaste el limite de 10 libros. Actualiza a premium para agregar mas.");
        }
        resenias.get(email).agregar(new Resenia(isbn, estado));
        // Se agrega el libro a la biblioteca general en caso de no existir.
        if (biblioteca.buscar(isbn) == null)
            biblioteca.agregar(libros.buscar(isbn));
        try {
            FileHandler.guardarMapaResenias(resenias);
        } catch (IOException e) {
            System.out.println("Error al guardar las resenias: " + e.getMessage());
        }
        try {
            FileHandler.guardarListaLibros(biblioteca);
        } catch (IOException e) {
            System.out.println("Error al guardar los libros: " + e.getMessage());
        }
    }


    /**
     * Este metodo deberia eliminar la resenia de la coleccion del usuario.
     * No deberia eliminar el libro de la biblioteca general, ya que este nos evita un pedido a la API,
     * en caso de que el usuario se haya arrepentido.
     *
     * @param email Correo electronico del usuario.
     * @param isbn  Identificador del libro a eliminar.
     */
    public void eliminarResenia(String email, String isbn) throws BibliotecaNoEncontradaException, LibroNoEncontradoException {
        // Si el usuario no tiene una biblioteca, se lanza la excepcion
        if (!resenias.containsKey(email))
            throw new BibliotecaNoEncontradaException("Este usuario no tiene una biblioteca");

        // Si el libro no esta en la biblioteca general, es imposible que se encuentre en la biblioteca del usuario.
        if (biblioteca.buscar(isbn) == null)
            throw new LibroNoEncontradoException("El ISBN del libro no fue encontrado.");

        // Es lo mismo que el condicional anterior, pero concretamente en la biblioteca del usuario.
        if (resenias.get(email).buscar(isbn) == null)
            throw new LibroNoEncontradoException("No tenes este libro en tu biblioteca.");

        // Efectivamente se elimina la resenia.
        resenias.get(email).eliminar(isbn);
        try {
            FileHandler.guardarMapaResenias(resenias);
        } catch (IOException e) {
            System.out.println("Error al guardar las resenias: " + e.getMessage());
        }
    }

    /**
     * Modifica los atributos de una resenia existente.
     * Este metodo permite actualizar el estado, rating y comentario de una resenia
     * previamente creada en la biblioteca del usuario.
     *
     * @param email           Email del usuario
     * @param isbn            ISBN del libro a modificar
     * @param nuevoEstado     Nuevo estado de lectura del libro
     * @param nuevoRating     Nueva calificación del libro (0-5)
     * @param nuevoComentario Nuevo comentario sobre el libro
     * @throws BibliotecaNoEncontradaException si el usuario no tiene una biblioteca
     * @throws LibroNoEncontradoException      si el libro no existe en la biblioteca general o en la del usuario
     */
    public void modificarResenia(String email, String isbn, EstadoLibro nuevoEstado, int nuevoRating, String nuevoComentario) throws BibliotecaNoEncontradaException, LibroNoEncontradoException {
        if (!resenias.containsKey(email))
            throw new BibliotecaNoEncontradaException("Este usuario no tiene una biblioteca");
        if (biblioteca.buscar(isbn) == null)
            throw new LibroNoEncontradoException("El ISBN del libro no fue encontrado.");
        if (resenias.get(email).buscar(isbn) == null)
            throw new LibroNoEncontradoException("No tenes este libro en tu biblioteca.");
        Resenia resenia = resenias.get(email).buscar(isbn);
        resenia.setComentario(nuevoComentario);
        resenia.setRating(nuevoRating);
        resenia.setEstadoLibro(nuevoEstado);
        try {
            FileHandler.guardarMapaResenias(resenias);
        } catch (IOException e) {
            System.out.println("Error al guardar las resenias: " + e.getMessage());
        }
    }

    /**
     * Metodo para buscar una resenia.
     *
     * @param email Email del usuario
     * @param isbn  ISBN del libro
     * @return Resenia encontrada
     * @throws LibroNoEncontradoException si el libro no esta en la biblioteca general o si no esta en la del usuario.
     */
    public Resenia buscarResenia(String email, String isbn) throws LibroNoEncontradoException {
        if (biblioteca.buscar(isbn) == null)
            throw new LibroNoEncontradoException("El ISBN del libro no fue encontrado.");
        if (resenias.get(email).buscar(isbn) == null)
            throw new LibroNoEncontradoException("No tenes este libro en tu biblioteca.");
        return resenias.get(email).buscar(isbn);
    }

    /**
     * Conseguir todas las resenias de un usuario.
     *
     * @param email Email del usuario
     * @return String con el listado de resenias
     * @throws BibliotecaNoEncontradaException si el usuario no tiene una biblioteca.
     */
    public String mostrarResenias(String email) throws BibliotecaNoEncontradaException {
        if (!resenias.containsKey(email))
            throw new BibliotecaNoEncontradaException("Este usuario no tiene una biblioteca.");

        var mensaje = new StringBuilder();
        ColeccionGenerica<Resenia> reseniasUsuario = resenias.get(email);

        for (Resenia resenia : reseniasUsuario) {
            Libro libro = biblioteca.buscar(resenia.getIsbn());
            mensaje.append("=== ").append(libro.getTitulo()).append(" ===\n")
                    .append(resenia.mostrar())
                    .append("\n\n");
        }
        return mensaje.toString();
    }

    /**
     * Metodo para saber si el usuario ya tiene una resenia de un libro.
     *
     * @param email Email del usuario
     * @param isbn  ISBN del libro
     * @return boolean, si el usuario no tiene una biblioteca, devuelve false, si el usuario tiene una resenia de ese libro, devuelve true.
     */
    public boolean tieneResenia(String email, String isbn) {
        return resenias.containsKey(email) && resenias.get(email).buscar(isbn) != null;
    }

    /**
     * Verificar la existencia de un libro en la biblioteca general
     *
     * @param isbn ISBN del libro
     * @return boolean, true si tenemos el libro, false si no lo tenemos.
     */
    public boolean tieneLibro(String isbn) {
        return biblioteca.buscar(isbn) != null;
    }

    /**
     * Obtiene todos los libros con el estado indicado.
     *
     * @param email  Email del usuario
     * @param estado Estado indicado
     * @return ColeccionGenerica con los libros con el estado indicado.
     * @throws BibliotecaNoEncontradaException si el usuario no tiene biblioteca
     */
    public ColeccionGenerica<Libro> filtrarPorEstado(String email, EstadoLibro estado) throws BibliotecaNoEncontradaException {
        if (!resenias.containsKey(email))
            throw new BibliotecaNoEncontradaException("Este usuario no tiene una biblioteca.");
        var libros = new ColeccionGenerica<Libro>();
        ColeccionGenerica<Resenia> reseniasUsuario = resenias.get(email);
        // Recorre las resenias del usuario
        for (Resenia resenia : reseniasUsuario) {
            if (resenia.getEstadoLibro() == estado) {
                Libro libro = biblioteca.buscar(resenia.getIsbn());
                // Agrego a mi coleccion de libros.
                libros.agregar(libro);
            }
        }
        if (libros.estaVacia())
            throw new NoSuchElementException("No hay libros con el estado " + estado.getEstadoLibro() +
                    " para el usuario con email " + email);
        return libros;
    }

    /**
     * Obtiene todos los libros de la biblioteca de un usuario. No usamos este metodo, ya que en nuestro programa
     * un usuario no puede ver los libros de otro usuario. Aumenta la escalabilidad.
     *
     * @param email Email del usuario
     * @return ColeccionGenerica con los libros del usuario
     * @throws BibliotecaNoEncontradaException si el usuario no tiene biblioteca
     */
    public ColeccionGenerica<Libro> obtenerLibrosUsuario(String email)
            throws BibliotecaNoEncontradaException {
        if (!resenias.containsKey(email)) {
            throw new BibliotecaNoEncontradaException("Este usuario no tiene una biblioteca.");
        }

        var libros = new ColeccionGenerica<Libro>();
        for (Resenia resenia : resenias.get(email)) {
            libros.agregar(biblioteca.buscar(resenia.getIsbn()));
        }
        return libros;
    }

    /**
     * Retorna una copia de la biblioteca, evita trabajar sobre la coleccion directamente.
     */
    public ColeccionGenerica<Libro> getBiblioteca() {
        var copiaSegura = new ColeccionGenerica<Libro>();
        for (Libro libro : biblioteca) {
            copiaSegura.agregar(libro);
        }
        return copiaSegura;
    }

    /**
     * Establece una biblioteca de forma segura.
     */
    public void setBiblioteca(ColeccionGenerica<Libro> libros) {
        for (Libro libro : libros) {
            biblioteca.agregar(libro);
        }
    }

    /**
     * Retorna una copia segura del mapa de resenias
     */
    public Map<String, ColeccionGenerica<Resenia>> getResenias() {
        var copiaSegura = new HashMap<String, ColeccionGenerica<Resenia>>();
        for (Map.Entry<String, ColeccionGenerica<Resenia>> entry : resenias.entrySet()) {
            ColeccionGenerica<Resenia> copiaResenias = new ColeccionGenerica<>();
            for (Resenia resenia : entry.getValue()) {
                copiaResenias.agregar(resenia);
            }
            copiaSegura.put(entry.getKey(), copiaResenias);
        }
        return copiaSegura;
    }

    /**
     * Establece el mapa de resenias de forma segura
     */
    public void setResenias(Map<String, ColeccionGenerica<Resenia>> nuevasResenias) {
        this.resenias = new HashMap<>();
        for (Map.Entry<String, ColeccionGenerica<Resenia>> entry : nuevasResenias.entrySet()) {
            ColeccionGenerica<Resenia> copiaResenias = new ColeccionGenerica<>();
            for (Resenia resenia : entry.getValue()) {
                copiaResenias.agregar(resenia);
            }
            this.resenias.put(entry.getKey(), copiaResenias);
        }
    }
}


