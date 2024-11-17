package Test;

import API.GoogleBooksAPI;
import Bibliotecas.ColeccionGenerica;
import Bibliotecas.GestionColecciones;
import Excepciones.*;
import Handlers.Helper;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import Libros.EstadoLibro;
import Pagos.Pago;
import Usuarios.GestionUsuarios;
import Libros.Libro;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static Handlers.SesionActiva.cerrarSesion;
import static Handlers.SesionActiva.getUsuarioActual;

public class App {

    private static final Scanner teclado = new Scanner(System.in);

//    public static void menu() throws IOException, UsuarioNoRegistradoException, UsuarioYaExistenteException, NoCoincideException {
//        GestionUsuarios gestionUsuarios = new GestionUsuarios();
//        GestionColecciones gestionColecciones = new GestionColecciones();
//        int opcionMenu = 9;
//        do {
//            opcionMenu = menuPrincipal();
//            switch (opcionMenu) {
//                case 1:
//                    System.out.println("Empezando el registro.\n");
//                    gestionUsuarios.registro();
//                    System.out.println("Registro finalizado.\n");
//                    break;
//                case 2:
//                    System.out.println("Iniciando sesion\n");
//                    gestionUsuarios.inicioDeSesion();
//                    if (SesionActiva.getUsuarioActual() != null) {
//                        int opcionSesion = 9;
//                        do {
//                            System.out.println("1. Buscar libro");
//                            System.out.println("2. Mi biblioteca");
//                            System.out.println("3. Mi perfil");
//                            System.out.println("0. Salir");
//                            System.out.println("Elija una opcion:");
//                            opcionSesion = teclado.nextInt();
//                            switch (opcionSesion) {
//                                case 1:
//                                    buscarLibro(gestionColecciones); /// A terminar de testear
//                                    break;
//                                case 2:
//                                    //metodo para ver la biblioteca
//                                    System.out.println(gestionColecciones.getBiblioteca().toString());;
//                                    break;
//                                case 3:
//                                    int opcionPerfil = 9;
//                                    System.out.println("Logueado como: " + SesionActiva.getUsuarioActual().getNombreUsuario() + "\n");
//                                    do {
//                                        System.out.println("1. Ver mi informacion.");
//                                        System.out.println("2. Mejorar plan.");
//                                        System.out.println("3. Cerrar sesion.");
//                                        System.out.println("4. Dar cuenta de baja.");
//                                        System.out.println("0. Salir.");
//                                        System.out.println("Elija una opcion:");
//                                        opcionPerfil = teclado.nextInt();
//
//                                        switch (opcionPerfil) {
//                                            case 0:
//                                                System.out.println("Saliendo...");
//                                                break;
//                                            case 1:
//                                                int opcionMostrarPerfil = 9;
//                                                gestionUsuarios.mostrarPerfil(SesionActiva.getUsuarioActual());
//                                                System.out.println("1. Cambiar nombre de usuario");
//                                                System.out.println("2. Cambiar contrasenia");
//                                                System.out.println("0. Volver");
//                                                opcionMostrarPerfil = teclado.nextInt();
//                                                teclado.nextLine();
//                                                switch (opcionMostrarPerfil) {
//                                                    case 0:
//                                                        System.out.println("Volviendo...");
//                                                        break;
//                                                    case 1:
//                                                        gestionUsuarios.cambiarNombreUsuario(SesionActiva.getUsuarioActual().getEmail());
//                                                        break;
//                                                    case 2:
//                                                        gestionUsuarios.cambiarContrasenia(SesionActiva.getUsuarioActual().getEmail());
//                                                        break;
//                                                    default:
//                                                        System.out.println("Opcion invalida");
//                                                        break;
//                                                }
//                                                break;
//                                            case 2:
//                                                int opcionPlan = 9;
//                                                if (SesionActiva.esUsuarioPremium()) {
//                                                    System.out.println("Tu cuenta ya es premium!\n");
//                                                    opcionPlan = 0;
//                                                } else {
//                                                    do {
//                                                        System.out.println("1. Pagar con tarjeta");
//                                                        System.out.println("2. Pagar en efectivo.");
//                                                        System.out.println("0. Salir.");
//                                                        System.out.println("Elija una opcion:");
//                                                        opcionPlan = teclado.nextInt();
//
//                                                        switch (opcionPlan) {
//                                                            case 0:
//                                                                System.out.println("Saliendo...");
//                                                                break;
//                                                            case 1:
//                                                                if (Pago.realizarPagoTarjeta()) {
//                                                                    gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
//                                                                }
//                                                                opcionPlan = 0;
//                                                                break;
//                                                            case 2:
//                                                                if (Pago.realizarPagoEfectivo()) {
//                                                                    gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
//                                                                }
//                                                                opcionPlan = 0;
//                                                                break;
//                                                            default:
//                                                                System.out.println("Opcion invalida");
//                                                                break;
//                                                        }
//                                                    } while (opcionPlan != 0);
//                                                }
//                                                break;
//                                            case 3:
//                                                SesionActiva.cerrarSesion();
//                                                System.out.println("Sesion cerrada.");
//                                                opcionSesion = 0;
//                                                opcionPerfil = 0;
//                                                break;
//                                            case 4:
//                                                gestionUsuarios.darDeBajaUsuario(getUsuarioActual()); /// Parece funcionar
//                                                SesionActiva.cerrarSesion();
//                                                opcionSesion = 0;
//                                                opcionPerfil = 0;
//                                                break;
//                                            default:
//                                                System.out.println("Opcion invalida.");
//                                                break;
//                                        }
//                                    } while (opcionPerfil != 0);
//
//                                    break;
//                                default:
//                                    System.out.println("Opcion invalida.");
//                                    break;
//
//                            }
//                        } while (opcionSesion != 0);
//                    }
//                    break;
//                case 3:
//                    //System.out.printf("Rcuperar cuenta");
//                    gestionUsuarios.recuperarCuenta();
//                    break;
//                case 0:
//                    System.out.printf("Saliendo...");
//                    break;
//                default:
//                    System.out.printf("Opcion invalida.");
//                    break;
//            }
//        } while (opcionMenu != 0);
//
//    }

    public static void menu() throws IOException, UsuarioNoRegistradoException, UsuarioYaExistenteException, NoCoincideException {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        GestionColecciones gestionColecciones = new GestionColecciones();
        int opcionMenu;
        do {
            opcionMenu = menuPrincipal();
            procesarOpcionMenu(opcionMenu, gestionUsuarios, gestionColecciones);
        } while (opcionMenu != 0);
    }

    private static int menuPrincipal() {
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

    private static void procesarOpcionMenu(int opcionMenu, GestionUsuarios gestionUsuarios, GestionColecciones gestionColecciones) {
        switch (opcionMenu) {
            case 1:
                iniciarRegistro(gestionUsuarios);
                break;
            case 2:
                iniciarSesion(gestionUsuarios, gestionColecciones);
                break;
            case 3:
                gestionUsuarios.recuperarCuenta();
                break;
            case 0:
                System.out.println("Saliendo...");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    private static void iniciarRegistro(GestionUsuarios gestionUsuarios) {
        System.out.println("Empezando el registro.\n");
        gestionUsuarios.registro();
        System.out.println("Registro finalizado.\n");
    }

    private static void iniciarSesion(GestionUsuarios gestionUsuarios, GestionColecciones gestionColecciones) {
        System.out.println("Iniciando sesion.\n");
        gestionUsuarios.inicioDeSesion();
        if (SesionActiva.getUsuarioActual() != null) {
            int opcionSesion;
            do {
                opcionSesion = menuSesion();
                procesarOpcionSesion(opcionSesion, gestionUsuarios, gestionColecciones);
            } while (opcionSesion != 0 && SesionActiva.getUsuarioActual()!=null);
        }
    }

    private static int menuSesion() {
        System.out.println("1. Buscar libro");
        System.out.println("2. Mi biblioteca");
        System.out.println("3. Mi perfil");
        System.out.println("0. Salir");
        System.out.println("Elija una opción:");
        int opcionSesion = teclado.nextInt();
        teclado.nextLine();
        return opcionSesion;
    }

    private static void procesarOpcionSesion(int opcionSesion, GestionUsuarios gestionUsuarios, GestionColecciones gestionColecciones) {
        switch (opcionSesion) {
            case 1:
                buscarLibro(gestionColecciones);
                break;
            case 2:
                mostrarBiblioteca(gestionColecciones);
                break;
            case 3:
                try {
                    gestionarPerfil(gestionUsuarios);
                } catch (IOException e) {
                    System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                }
                break;
            case 0:
                System.out.println("Saliendo...");
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }

    private static void mostrarBiblioteca(GestionColecciones gestionColecciones) {
        System.out.println(gestionColecciones.getBiblioteca().toString()); /// NO ES ASI
    }

    private static void gestionarPerfil(GestionUsuarios gestionUsuarios) throws IOException {
        System.out.println("Logueado como " + SesionActiva.getUsuarioActual().getNombreUsuario() + "\n");
        int opcionPerfil;
        do {
            opcionPerfil = menuPerfil();
            procesarOpcionPerfil(opcionPerfil, gestionUsuarios);
        } while (opcionPerfil != 0);
    }

    private static int menuPerfil() {
        System.out.println("1. Ver mi información.");
        System.out.println("2. Mejorar plan.");
        System.out.println("3. Cerrar sesión.");
        System.out.println("4. Dar cuenta de baja.");
        System.out.println("0. Salir.");
        System.out.println("Elija una opción:");
        int opcionPerfil = teclado.nextInt();
        teclado.nextLine();
        return opcionPerfil;
    }

    private static void procesarOpcionPerfil(int opcionPerfil, GestionUsuarios gestionUsuarios) {
        switch (opcionPerfil) {
            case 1:
                mostrarOpcionesPerfil(gestionUsuarios);
                break;
            case 2:
                mejorarPlan(gestionUsuarios);
                break;
            case 3:
                cerrarSesion();
                break;
            case 4:
                darDeBajaCuenta(gestionUsuarios);
                break;
            case 0:
                System.out.println("Saliendo...");
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }

    private static void mostrarOpcionesPerfil(GestionUsuarios gestionUsuarios) {
        gestionUsuarios.mostrarPerfil(SesionActiva.getUsuarioActual());
        System.out.println("1. Cambiar nombre de usuario");
        System.out.println("2. Cambiar contraseña");
        System.out.println("0. Volver");
        int opcionMostrarPerfil = teclado.nextInt();
        teclado.nextLine();
        switch (opcionMostrarPerfil) {
            case 1:
                try {
                    gestionUsuarios.cambiarNombreUsuario(SesionActiva.getUsuarioActual().getEmail());
                } catch (UsuarioNoRegistradoException e) {
                    System.out.println(e.getMessage());;
                } catch (IOException e) {
                    System.out.println("Ocurrio un error inesperado: " + e.getMessage());;
                }
                break;
            case 2:
                try {
                    gestionUsuarios.cambiarContrasenia(SesionActiva.getUsuarioActual().getEmail());
                } catch (UsuarioNoRegistradoException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                }
                break;
            case 0:
                System.out.println("Volviendo....");
                break;
            default:
                System.out.println("Opcion invalida");
                break;
        }
    }

    private static void mejorarPlan(GestionUsuarios gestionUsuarios) {
        if (SesionActiva.esUsuarioPremium()) {
            System.out.println("¡Tu cuenta ya es premium!");
        } else {
            int opcionPlan;
            do {
                System.out.println("1. Pagar con tarjeta");
                System.out.println("2. Pagar en efectivo.");
                System.out.println("0. Salir.");
                System.out.println("Elija una opción:");
                opcionPlan = teclado.nextInt();
                switch (opcionPlan) {
                    case 1:
                        if (Pago.realizarPagoTarjeta()) {
                            try {
                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                            } catch (UsuarioYaExistenteException e) {
                                System.out.println(e.getMessage());
                            } catch (UsuarioNoRegistradoException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                            }
                        }
                        opcionPlan = 0;
                        break;
                    case 2:
                        if (Pago.realizarPagoEfectivo()) {
                            try {
                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                            } catch (UsuarioYaExistenteException e) {
                                System.out.println(e.getMessage());
                            } catch (UsuarioNoRegistradoException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                            }
                        }
                        opcionPlan = 0;
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida.");
                        break;
                }
            } while (opcionPlan != 0);
        }
    }

    private static void darDeBajaCuenta(GestionUsuarios gestionUsuarios) {
        try {
            gestionUsuarios.darDeBajaUsuario(SesionActiva.getUsuarioActual());
        } catch (IOException e) {
            System.out.println("Ocurrio un error inesperado: " + e.getMessage());
        }
        SesionActiva.cerrarSesion();
    }

    public static void buscarLibro(GestionColecciones gestionColecciones) {
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
                teclado.nextLine();
                // Buscar libro segun titulo
                System.out.println("Ingrese el titulo: ");
                String titulo = teclado.nextLine();
                ColeccionGenerica<Libro> busquedaTitulo = new ColeccionGenerica<>();
                try {
                    busquedaTitulo = JSONUtiles.parseJsonListaLibros(test.buscarPorTitulo(titulo));
                } catch (JSONException | IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("Ocurrio un error inesperado.");
                }
                System.out.println("Resultado de la busqueda:\n " + busquedaTitulo.listar());
                System.out.println("Ingrese el isbn del libro que desea reseñar (ingrese n para salir): ");
                String opcionTitulo = teclado.nextLine();
                while (!opcionTitulo.equals("n")) {
                    System.out.println("1. Por leer.");
                    System.out.println("2. Leyendo.");
                    System.out.println("3. Leido.");
                    System.out.println("Ingrese el estado que desea darle al libro: ");
                    int estado = teclado.nextInt();
                    EstadoLibro estadoLibro = null;
                    try {
                        estadoLibro = Helper.validarEstado(estado);
                    } catch (FormatoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    try {
                        gestionColecciones.agregarResenia(SesionActiva.getUsuarioActual().getEmail(), opcionTitulo, estadoLibro, busquedaTitulo);
                        //break;
                    } catch (LimiteReseniasException e) {
                        System.out.println(e.getMessage());
                    } catch (ReseniaExistenteException e) {
                        System.out.println(e.getMessage());
                        ;
                    }
                }
                break;
            case 2:
                teclado.nextLine();
                // Buscar libros de un autor
                System.out.println("Ingrese el autor: ");
                String autor = teclado.nextLine();
                ColeccionGenerica<Libro> busquedaAutor = new ColeccionGenerica<>();
                try {
                    busquedaAutor = JSONUtiles.parseJsonListaLibros(test.buscarPorAutor(autor));
                } catch (JSONException | IOException | InterruptedException e) {
                    System.out.println(e.getMessage());
                }
                System.out.println(busquedaAutor.listar());
                break;
            default:
                System.out.println("Opcion incorrecta.");
                break;
        }
    }
}
