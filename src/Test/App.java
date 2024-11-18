package Test;

import API.GoogleBooksAPI;
import Bibliotecas.ColeccionGenerica;
import Bibliotecas.GestionColecciones;
import Excepciones.*;
import Handlers.Helper;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import Libros.EstadoLibro;
import Libros.Resenia;
import Pagos.Pago;
import Usuarios.GestionUsuarios;
import Libros.Libro;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
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

    public static void menu() {
        GestionUsuarios gestionUsuarios = new GestionUsuarios();
        GestionColecciones gestionColecciones = new GestionColecciones();
        int opcionMenu = 9;
        do {
            opcionMenu = menuPrincipal();
            procesarOpcionMenu(opcionMenu, gestionUsuarios, gestionColecciones);
        } while (opcionMenu != 0);
    }

    private static int menuPrincipal() {
        String input;
        int opcionMenu = 9;
        System.out.println("\n¡Binvenido a GoodRead!\n");
        System.out.println("1. Registrarme");
        System.out.println("2. Iniciar sesion");
        System.out.println("3. Recuperar cuenta");
        System.out.println("0. Salir");
        System.out.println("Elija una opcion:");
        input = teclado.nextLine();
        try {
            opcionMenu = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
        }
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
            } while (opcionSesion != 0 && SesionActiva.getUsuarioActual() != null);
        }
    }

    private static int menuSesion() {
        String input;
        int opcionSesion = 9;
        System.out.println("1. Buscar libro");
        System.out.println("2. Mi biblioteca");
        System.out.println("3. Mi perfil");
        System.out.println("0. Salir");
        System.out.println("Elija una opción:");
        input = teclado.nextLine();
        try {
            opcionSesion = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
        }
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
                SesionActiva.cerrarSesion();
            default:
                System.out.println("Opcion invalida.");
                break;
        }
    }

    private static void mostrarBiblioteca(GestionColecciones gestionColecciones) {
        try {
            System.out.println(gestionColecciones.mostrarResenias(SesionActiva.getUsuarioActual().getEmail()));

            int opcion;
            do {
                opcion = mostrarMenuBiblioteca();
                switch (opcion) {
                    case 1:
                        modificarResenia(gestionColecciones);
                        break;
                    case 2:
                        eliminarResenia(gestionColecciones);
                        break;
                    case 3:
                        verInformacionLibro(gestionColecciones);
                        break;
                    case 0:
                        System.out.println("Volviendo al menu principal...");
                        break;
                    default:
                        System.out.println("Opcion invalida");
                        break;
                }
            } while (opcion != 0);
        } catch (BibliotecaNoEncontradaException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int mostrarMenuBiblioteca() {
        System.out.println("\n1. Modificar resenia");
        System.out.println("2. Eliminar resenia");
        System.out.println("3. Ver informacion de un libro");
        System.out.println("0. Volver");
        System.out.println("Elija una opcion:");

        String input = teclado.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
            return -1;
        }
    }

    private static void modificarResenia(GestionColecciones gestionColecciones) {
        System.out.println("Ingrese el ISBN del libro (n para cancelar):");
        String isbn = teclado.nextLine();

        if (isbn.equals("n")) return;

        try {
            System.out.println("¿Qué desea modificar?");
            System.out.println("1. Estado");
            System.out.println("2. Rating");
            System.out.println("3. Comentario");
            String input = teclado.nextLine();

            int opcion = Integer.parseInt(input);
            Resenia resenia = gestionColecciones.buscarResenia(SesionActiva.getUsuarioActual().getEmail(), isbn);
            EstadoLibro nuevoEstado = resenia.getEstadoLibro();
            int nuevoRating = resenia.getRating();
            String nuevoComentario = resenia.getComentario();

            switch (opcion) {
                case 1:
                    System.out.println("1. Por leer");
                    System.out.println("2. Leyendo");
                    System.out.println("3. Leido");
                    System.out.println("Ingrese el nuevo estado:");
                    int estadoInput = Integer.parseInt(teclado.nextLine());
                    nuevoEstado = Helper.validarEstado(estadoInput);
                    break;
                case 2:
                    System.out.println("Ingrese el nuevo rating (0-5):");
                    nuevoRating = Integer.parseInt(teclado.nextLine());
                    Helper.verificarRating(nuevoRating);
                    break;
                case 3:
                    System.out.println("Ingrese el nuevo comentario:");
                    nuevoComentario = teclado.nextLine();
                    Helper.verificarComentario(nuevoComentario);
                    break;
                default:
                    System.out.println("Opcion invalida");
                    return;
            }

            gestionColecciones.modificarResenia(
                    SesionActiva.getUsuarioActual().getEmail(),
                    isbn,
                    nuevoEstado,
                    nuevoRating,
                    nuevoComentario
            );
            System.out.println("Resenia modificada exitosamente");

        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
        } catch (FormatoInvalidoException | BibliotecaNoEncontradaException | LibroNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void eliminarResenia(GestionColecciones gestionColecciones) {
        System.out.println("Ingrese el ISBN del libro a eliminar (n para cancelar):");
        String isbn = teclado.nextLine();

        if (isbn.equals("n")) return;

        try {
            gestionColecciones.eliminarResenia(SesionActiva.getUsuarioActual().getEmail(), isbn);
            System.out.println("Resenia eliminada exitosamente");
        } catch (BibliotecaNoEncontradaException | LibroNoEncontradoException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void verInformacionLibro(GestionColecciones gestionColecciones) {
        System.out.println("Ingrese el ISBN del libro que desea ver (n para cancelar):");
        String isbn = teclado.nextLine();

        if (isbn.equals("n")) return;

        // Verificar si el usuario tiene una resenia del libro
        if (gestionColecciones.tieneResenia(SesionActiva.getUsuarioActual().getEmail(), isbn)) {
            Libro libro = gestionColecciones.getBiblioteca().buscar(isbn);
            if (libro != null) {
                System.out.println(libro.mostrar());
            } else {
                System.out.println("Error: El libro existe en tu biblioteca pero no se encuentra en la biblioteca general");
            }
        } else {
            System.out.println("No tienes este libro en tu biblioteca");
        }
    }
    private static void gestionarPerfil(GestionUsuarios gestionUsuarios) throws IOException {
        System.out.println("Logueado como " + SesionActiva.getUsuarioActual().getNombreUsuario() + "\n");
        int opcionPerfil;
        do {
            opcionPerfil = menuPerfil();
            procesarOpcionPerfil(opcionPerfil, gestionUsuarios);
        } while (opcionPerfil != 0 && SesionActiva.getUsuarioActual() != null);
    }

    private static int menuPerfil() {
        String input;
        int opcionPerfil = 9;
        System.out.println("1. Ver mi información.");
        System.out.println("2. Mejorar plan.");
        System.out.println("3. Cerrar sesión.");
        System.out.println("4. Dar cuenta de baja.");
        System.out.println("0. Salir.");
        System.out.println("Elija una opción:");
        input = teclado.nextLine();
        try {
            opcionPerfil = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
        }
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
                break;
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
        String input;
        int opcionMostrarPerfil = 9;
        input = teclado.nextLine();
        try {
            opcionMostrarPerfil = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
        }
        switch (opcionMostrarPerfil) {
            case 1:
                try {
                    gestionUsuarios.cambiarNombreUsuario(SesionActiva.getUsuarioActual().getEmail());
                } catch (UsuarioNoRegistradoException e) {
                    System.out.println(e.getMessage());
                    ;
                } catch (IOException e) {
                    System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                    ;
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
            int opcionPlan = 9;
            String input;
            do {
                System.out.println("1. Pagar con tarjeta");
                System.out.println("2. Pagar en efectivo.");
                System.out.println("0. Salir.");
                System.out.println("Elija una opción:");
                input = teclado.nextLine();
                try {
                    opcionPlan = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Ingrese un numero entero");
                }
                switch (opcionPlan) {
                    case 1:
                        if (Pago.realizarPagoTarjeta()) {
                            try {
                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                            } catch (UsuarioYaExistenteException | UsuarioNoRegistradoException e) {
                                System.out.println(e.getMessage());
                            } catch (IOException e) {
                                System.out.println("Ocurrio un error inesperado: " + e.getMessage());
                            }
                        } else {
                            System.out.println("La tarjeta fue rechazada. Saldo insuficiente.");
                        }
                        opcionPlan = 0;
                        break;
                    case 2:
                        if (Pago.realizarPagoEfectivo()) {
                            try {
                                gestionUsuarios.mejorarPlan(SesionActiva.getUsuarioActual().getEmail());
                            } catch (UsuarioYaExistenteException | UsuarioNoRegistradoException e) {
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
        int opcion = mostrarMenuBusqueda();

        switch (opcion) {
            case 1:
                buscarPorTitulo(test, gestionColecciones);
                break;
            case 2:
                buscarPorAutor(test, gestionColecciones);
                break;
            case 0:
                System.out.println("Volviendo al menu principal...");
                break;
            default:
                System.out.println("Opcion incorrecta");
                break;
        }
    }

    private static int mostrarMenuBusqueda() {
        System.out.println("1. Buscar por titulo");
        System.out.println("2. Buscar por autor");
        System.out.println("0. Salir");
        System.out.println("Elija una opcion");
        String input = teclado.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero");
            return -1;
        }
    }

    private static void buscarPorTitulo(GoogleBooksAPI test, GestionColecciones gestionColecciones) {
        System.out.println("Ingrese el titulo: ");
        String titulo = teclado.nextLine();
        procesarBusqueda(test, gestionColecciones, titulo, true);
    }

    private static void buscarPorAutor(GoogleBooksAPI test, GestionColecciones gestionColecciones) {
        System.out.println("Ingrese el autor: ");
        String autor = teclado.nextLine();
        procesarBusqueda(test, gestionColecciones, autor, false);
    }

    private static void procesarBusqueda(GoogleBooksAPI test, GestionColecciones gestionColecciones,
                                         String busqueda, boolean esPorTitulo) {
        var resultadoBusqueda = new ColeccionGenerica<Libro>();

        try {
            resultadoBusqueda = JSONUtiles.parseJsonListaLibros(
                    /* operador ternario: si esPorTitulo es true, ejecuta buscarPorTitulo
                     si esPorTitulo es false, ejecuta buscarPorAutor */
                    esPorTitulo ? test.buscarPorTitulo(busqueda) : test.buscarPorAutor(busqueda)
            );

            if (resultadoBusqueda.tamanio() == 0) {
                System.out.println("No se encontraron libros con ese " +
                        // lo mismo, para generalizar la busqueda, uso el mismo sout para ambos.
                        (esPorTitulo ? "titulo" : "autor"));
                return;
            }

            System.out.println("Resultado de la busqueda:\n" + resultadoBusqueda.listar());
            procesarSeleccionLibro(gestionColecciones, resultadoBusqueda);

        } catch (JSONException | IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void procesarSeleccionLibro(GestionColecciones gestionColecciones,
                                               ColeccionGenerica<Libro> resultadoBusqueda) {
        System.out.println("Ingrese el isbn del libro que desea agregar (ingrese n para salir): ");
        String isbn = teclado.nextLine();

        if (!isbn.equals("n")) {
            try {
                // verificar que el libro existe en la busqueda
                Libro libroSeleccionado = resultadoBusqueda.buscar(isbn);
                if (libroSeleccionado == null) {
                    System.out.println("El ISBN ingresado no corresponde a ningun libro de la busqueda");
                    return;
                }

                EstadoLibro estadoLibro = solicitarEstadoLibro();
                if (estadoLibro != null) {
                    gestionColecciones.agregarResenia(
                            SesionActiva.getUsuarioActual().getEmail(),
                            isbn,
                            estadoLibro,
                            resultadoBusqueda
                    );
                    System.out.println("Libro agregado exitosamente");
                }
            } catch (LimiteReseniasException | ReseniaExistenteException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static EstadoLibro solicitarEstadoLibro() {
        while (true) {
            System.out.println("1. Por leer");
            System.out.println("2. Leyendo");
            System.out.println("3. Leido");
            System.out.println("Ingrese el estado que desea darle al libro: ");

            String inputEstado = teclado.nextLine();
            if (inputEstado.equals("n")) {
                return null;
            }

            try {
                int estado = Integer.parseInt(inputEstado);
                return Helper.validarEstado(estado);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero entero");
            } catch (FormatoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }
    }

}
