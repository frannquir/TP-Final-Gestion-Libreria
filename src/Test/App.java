package Test;

import API.GoogleBooksAPI;
import Bibliotecas.ColeccionGenerica;
import Excepciones.NoCoincideException;
import Excepciones.UsuarioNoRegistradoException;
import Excepciones.UsuarioYaExistenteException;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import Pagos.Pago;
import Usuarios.GestionUsuarios;
import Libros.Libro;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Handlers.SesionActiva.getUsuarioActual;

public class App {

    private static final Scanner teclado = new Scanner(System.in);

    public static void menu() throws IOException, UsuarioNoRegistradoException, UsuarioYaExistenteException, NoCoincideException {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        int opcionMenu = 9;
        do {
            opcionMenu = menuPrincipal();
            switch (opcionMenu) {
                case 1:
                    System.out.println("Empezando el registro.\n");
                    gestionUsuarios.registro();
                    System.out.println("Registro finalizado.\n");
                    break;
                case 2:
                    System.out.println("Iniciando sesion\n");
                    gestionUsuarios.inicioDeSesion();
                    if (SesionActiva.getUsuarioActual() != null) {
                        int opcionSesion = 9;
                        do {
                            System.out.println("1. Buscar libro");
                            System.out.println("2. Mi biblioteca");
                            System.out.println("3. Mi perfil");
                            System.out.println("0. Salir");
                            System.out.println("Elija una opcion:");
                            opcionSesion = teclado.nextInt();
                            switch (opcionSesion) {
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

                                        switch (opcionPerfil) {
                                            case 0:
                                                System.out.println("Saliendo...");
                                                break;
                                            case 1:
                                                int opcionMostrarPerfil = 9;
                                                gestionUsuarios.mostrarPerfil(SesionActiva.getUsuarioActual());
                                                System.out.println("1. Cambiar nombre de usuario");
                                                System.out.println("2. Cambiar contrasenia");
                                                System.out.println("0. Volver");
                                                opcionMostrarPerfil = teclado.nextInt();
                                                teclado.nextLine();
                                                switch (opcionMostrarPerfil){
                                                    case 0:
                                                        System.out.println("Volviendo...");
                                                        break;
                                                    case 1:
                                                        gestionUsuarios.cambiarNombreUsuario(SesionActiva.getUsuarioActual().getEmail());
                                                        break;
                                                    case 2:
                                                        gestionUsuarios.cambiarContrasenia(SesionActiva.getUsuarioActual().getEmail());
                                                        break;
                                                    default:
                                                        System.out.println("Opcion invalida");
                                                        break;
                                                }
                                                break;
                                            case 2:
                                                int opcionPlan = 9;
                                                if (SesionActiva.esUsuarioPremium()) {
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
                                                            case 0:
                                                                System.out.println("Saliendo...");
                                                                break;
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
                                                                opcionPlan = 0;
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
                                    } while (opcionPerfil != 0);

                                    break;
                                default:
                                    System.out.println("Opcion invalida.");
                                    break;

                            }
                        } while (opcionSesion != 0);
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

    public static int menuPrincipal() {
        System.out.println("\n¡Binvenido a GoodRead!\n");
        System.out.println("1. Registrarme");
        System.out.println("2. Iniciar sesion");
        System.out.println("3. Recuperar cuenta");
        System.out.println("0. Salir");
        System.out.println("Elija una opcion:");
        int opcionMenu = teclado.nextInt();
        teclado.nextLine();
        return opcionMenu;
    }

    public static void buscarLibro() {
        GoogleBooksAPI test = new GoogleBooksAPI();
        int opcion = 9;
        Scanner teclado = new Scanner(System.in);
        System.out.println("1. Buscar por titulo");
        System.out.println("2. Buscar por autor");
        System.out.println("0. Salir");
        System.out.println("Elija una opcion");
        opcion = teclado.nextInt();
        switch (opcion) {
            case 1:
                // Buscar libro segun titulo
                System.out.println("Ingrese el titulo: ");
                String titulo = teclado.nextLine();
                ColeccionGenerica<Libro> busquedaTitulo = new ColeccionGenerica<>();
                try {
                    busquedaTitulo = JSONUtiles.parseJsonListaLibros(test.buscarPorTitulo(titulo));
                } catch (JSONException | IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(busquedaTitulo);
            case 2:
                // Buscar libros de un autor
                System.out.println("Ingrese el autor: ");
                String autor = teclado.nextLine();
                ColeccionGenerica<Libro> busquedaAutor = new ColeccionGenerica<>();
                try {
                    busquedaAutor = JSONUtiles.parseJsonListaLibros(test.buscarPorAutor(autor));
                } catch (JSONException | IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(busquedaAutor);
                break;
            default:
                System.out.println("Opcion incorrecta.");
                break;
        }
    }
}
