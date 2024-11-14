package Test;

import API.GoogleBooksAPI;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import Usuarios.GestionUsuarios;
import Libros.Libro;
import java.util.ArrayList;
import java.util.Scanner;

public class App {
    public static void menu() {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        Scanner teclado = new Scanner(System.in);
        int opcionMenu = 9;
        do {
            System.out.println("¡Binvenido a GoodRead!\n");
            System.out.println("1. Registrarme");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Recuperar cuenta");
            System.out.println("0. Salir");
            System.out.println("Elija una opion:");
            opcionMenu = teclado.nextInt();
            switch (opcionMenu) {
                case 1:
                    //System.out.println("Registro");
                    gestionUsuarios.registro();
                    opcionMenu = 2;
                case 3:
                    //System.out.printf("Rcuperar cuenta");
                    gestionUsuarios.recuperarCuenta(new Scanner(System.in));
                    opcionMenu = 2;
                case 2:
                    //System.out.printf("Iniciar sesion");
                    gestionUsuarios.inicioDeSesion();
                    if (SesionActiva.getUsuarioActual() != null) {
                        int opcion = 9;
                        do {
                            System.out.printf("1. Ver biblioteca");
                            System.out.printf("2. Buscar libro");
                            System.out.printf("3. Ver perfil");
                            System.out.printf("0. Salir");
                            System.out.println("Elija una opion:");
                            opcion=teclado.nextInt();
                            switch (opcion){
                                case 1:
                                    //metodo para ver la biblioteca
                                case 2:

                            }
                        }while (opcion!=0);
                    }
                    break;
                case 0:
                    System.out.printf("Saliendo...");
                    break;
                default:
                    System.out.printf("Elija una opcion correspondiente:");
            }
        } while (opcionMenu != 0);

    }

    public static void buscarLibro(){
        GoogleBooksAPI test = new GoogleBooksAPI();
        int opcion=9;
        Scanner teclado = new Scanner(System.in);
        System.out.printf("1. Buscar por titulo");
        System.out.printf("2. Buscar por autor");
        System.out.printf("0. Salir");
        System.out.printf("Elija una opcion");
        opcion=teclado.nextInt();
        switch (opcion){
            case 1:
                // Buscar libro segun titulo
                System.out.printf("Ingrese el titulo: ");
                String titulo = teclado.nextLine();
                ArrayList<Libro> busquedaTitulo = new ArrayList<>();
                try {
                    ///busquedaTitulo = JSONUtiles.parseJsonListaLibros(test.buscarPorTitulo(titulo));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(busquedaTitulo);
            case 2:
                // Buscar libros de un autor
                System.out.printf("Ingrese el autor: ");
                String autor = teclado.nextLine();
                ArrayList<Libro> busquedaAutor = new ArrayList<>();
                try {
                    ///busquedaAutor = JSONUtiles.parseJsonListaLibros(test.buscarPorAutor(autor));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println(busquedaAutor);
            case 3:

        }
        /*



         */
    }

}
