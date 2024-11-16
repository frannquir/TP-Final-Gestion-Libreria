package Test;

import Excepciones.UsuarioNoRegistradoException;
import Excepciones.UsuarioYaExistenteException;
import Pagos.Pago;
import Usuarios.GestionUsuarios;
import Usuarios.Usuario;
import Handlers.*;
import API.GoogleBooksAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Test.App.menu;

public class Main {
    public static void main(String[] args) throws UsuarioNoRegistradoException, UsuarioYaExistenteException, IOException {
        menu();
      //  GoogleBooksAPI test = new GoogleBooksAPI();
        //try {
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
//           Usuario prueba = new Usuario();
          //  GestionUsuarios gestionUsuarios = new GestionUsuarios();
//            prueba = gestionUsuarios.crearUsuario();
//            System.out.println(prueba);
//            gestionUsuarios.guardarRegistro(prueba);

            //gestionUsuarios.registro();

           /* System.out.println("\nInicio de sesion \n");
            gestionUsuarios.inicioDeSesion();
            System.out.println(SesionActiva.getUsuarioActual());
            prueba.toJSON();
            gestionUsuarios.guardarRegistro(prueba); */

            ///Prueba recuperar cuenta
            /*gestionUsuarios.recuperarCuenta(new Scanner(System.in));
            System.out.printf(gestionUsuarios.getUsuariosList().toString());*/
        ///App.menu();
           // System.out.println(Pago.realizarPago());


        //} catch (Exception e) { //trabajar mejor las Exceptions
       //     e.printStackTrace();
        //}
    }
}