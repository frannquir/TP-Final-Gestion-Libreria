package Test;

import Usuarios.GestionUsuarios;
import Usuarios.Usuario;
import Handlers.*;
import API.GoogleBooksAPI;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        GoogleBooksAPI test = new GoogleBooksAPI();
        try {
            /*// Buscar libro segun titulo
            ArrayList<Libro> busquedaTitulo = new ArrayList<>();
            busquedaTitulo = JSONUtiles.parseJsonListaLibros(test.buscarPorTitulo("Harry Potter"));
            System.out.println(busquedaTitulo);
            // Buscar un libro por su ISBN
            ArrayList<Libro> busquedaISBN = new ArrayList<>();
            busquedaISBN = JSONUtiles.parseJsonListaLibros(test.buscarPorISBN("9781781101346"));
            System.out.println(busquedaISBN);
            // Buscar libros de un autor
            ArrayList<Libro> busquedaAutor = new ArrayList<>();
            busquedaAutor = JSONUtiles.parseJsonListaLibros(test.buscarPorAutor("J.K. Rowling"));
            System.out.println(busquedaAutor);

*/
            Usuario prueba = new Usuario();
            GestionUsuarios gestionUsuarios = new GestionUsuarios();
            prueba = gestionUsuarios.crearUsuario();
            System.out.println(prueba);
            gestionUsuarios.guardarRegistro(prueba);

            gestionUsuarios.inicioDeSesion();
            System.out.println(SesionActiva.getUsuarioActual());
            prueba.toJSON();
            gestionUsuarios.guardarRegistro(prueba);


        } catch (Exception e) { //trabajar mejor las Exceptions
            e.printStackTrace();
        }
    }
}