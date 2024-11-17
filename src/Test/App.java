package Test;

import API.GoogleBooksAPI;
import Excepciones.UsuarioNoRegistradoException;
import Excepciones.UsuarioYaExistenteException;
import Handlers.SesionActiva;
import Pagos.Pago;
import Usuarios.GestionUsuarios;
import Libros.Libro;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Handlers.SesionActiva.getUsuarioActual;

public class App {
    public static void menu() throws IOException, UsuarioNoRegistradoException, UsuarioYaExistenteException {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        Scanner teclado = new Scanner(System.in);
        int opcionMenu = 9;
        do {
            System.out.println("¡Binvenido a GoodRead!\n");
            System.out.println("1. Registrarme");
            System.out.println("2. Iniciar sesion");
            System.out.println("3. Recuperar cuenta");
            System.out.println("0. Salir");
            System.out.println("Elija una opcion:");
            opcionMenu = teclado.nextInt();
            switch (opcionMenu) {
                case 1:
                    System.out.println("Empezando el registro.\n");
                    gestionUsuarios.registro();
                    System.out.println("Registro finalizado.\n");
                    break;
                case 2:
                    System.out.printf("Iniciando sesion\n");
                    gestionUsuarios.inicioDeSesion();
                    if (SesionActiva.getUsuarioActual() != null) {
                        int opcionSesion = 9;
                        do {
                            System.out.println("1. Buscar libro");
                            System.out.println("2. Mi biblioteca");
                            System.out.println("3. Mi perfil");
                            System.out.println("0. Salir");
                            System.out.println("Elija una opcion:");
                            opcionSesion=teclado.nextInt();
                            switch (opcionSesion){
                                case 1:
                                    buscarLibro(); /// A terminar de testear
                                    break;
                                case 2:
                                    //metodo para ver la biblioteca
                                    break;
                                case 3:
                                    int opcionPerfil = 9;
                                    System.out.println("Logueado como: " + SesionActiva.getUsuarioActual().getNombreUsuario() + "\n");
                                    do {
                                        System.out.println("1. Ver mi informacion.");
                                        System.out.println("2. Mejorar plan.");
                                        System.out.println("3. Cerrar sesion.");
                                        System.out.println("4. Dar cuenta de baja.");
                                        System.out.println("0. Salir.");
                                        System.out.println("Elija una opcion:");
                                        opcionPerfil = teclado.nextInt();

                                        switch (opcionPerfil){
                                            case 1:

                                                break;
                                            case 2:
                                                int opcionPlan = 9;
                                                if(SesionActiva.esUsuarioPremium()){
                                                System.out.println("Tu cuenta ya es premium!\n");
                                                opcionPlan = 0;
                                                } else {
                                                do {
                                                    System.out.println("1. Pagar con tarjeta");
                                                    System.out.println("2. Pagar en efectivo.");
                                                    System.out.println("0. Salir.");
                                                    System.out.println("Elija una opcion:");
                                                    opcionPlan = teclado.nextInt();

                                                    switch (opcionPlan) {
                                                        case 1:
                                                            if (Pago.realizarPagoTarjeta()) {
                                                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                                                            }
                                                            opcionPlan = 0;
                                                            break;
                                                        case 2:
                                                            if (Pago.realizarPagoEfectivo()) {
                                                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                                                            }
                                                            break;
                                                        default:
                                                            System.out.println("Opcion invalida");
                                                            break;
                                                    }
                                                } while (opcionPlan != 0);
                                            }
                                                break;
                                            case 3:
                                                SesionActiva.cerrarSesion();
                                                System.out.println("Sesion cerrada.");
                                                opcionSesion = 0;
                                                opcionPerfil = 0;
                                                break;
                                            case 4:
                                                gestionUsuarios.darDeBajaUsuario(getUsuarioActual()); /// Parece funcionar
                                                SesionActiva.cerrarSesion();
                                                opcionSesion = 0;
                                                opcionPerfil = 0;
                                                break;
                                            default:
                                                System.out.println("Opcion invalida.");
                                                break;
                                        }
                                    } while (opcionPerfil!=0);

                                    break;
                                default:
                                    System.out.println("Opcion invalida.");
                                    break;

                            }
                        }while (opcionSesion!=0);
                    }
                    break;
                case 3:
                    //System.out.printf("Rcuperar cuenta");
                    gestionUsuarios.recuperarCuenta();
                    break;
                case 0:
                    System.out.printf("Saliendo...");
                    break;
                default:
                    System.out.printf("Opcion invalida.");
                    break;
            }
        } while (opcionMenu != 0);

    }

    public static void buscarLibro(){
        GoogleBooksAPI test = new GoogleBooksAPI();
        int opcion=9;
        Scanner teclado = new Scanner(System.in);
        System.out.println("1. Buscar por titulo");
        System.out.println("2. Buscar por autor");
        System.out.println("0. Salir");
        System.out.println("Elija una opcion");
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
