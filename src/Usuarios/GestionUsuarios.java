package Usuarios;

import Excepciones.*;
import Handlers.FileHandler;
import Handlers.Helper;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

public class GestionUsuarios {
    private HashMap<String, Usuario> usuariosEnElSistema; //La clave de cada usuario sera su gmail.

    public GestionUsuarios() {
        try {
            usuariosEnElSistema = new HashMap<>();
            List<Usuario> usuarios = FileHandler.leerListaUsuarios();
            setUsuariosEnElSistema(usuarios);
        } catch (IOException e) {
            System.out.println("Error al cargar usuarios: No hay usuarios en el json");
        }
    }


    private ArrayList<Usuario> bajarUsuarios() {
        ArrayList<Usuario> auxiliar = new ArrayList<>();

        return auxiliar;
    }

    public Usuario crearUsuario() {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String nombre = "";
        String contrasenia = "";
        String contraseniaDeNuevo = "";
        String flag = "c";

        System.out.println("Ingrese n en cualquier momento para cancelar el registro.");

        while (!flag.equals("n")) {
            System.out.println("Ingrese su gmail: ");
            email = scanner.nextLine();
            flag = email;

            try {
                if (usuariosEnElSistema.containsKey(email) && !usuariosEnElSistema.get(email).isActivo()) {
                    char opcion = 'c';
                    do {
                        System.out.printf("La cuenta asociada a este email esta dada de baja, ¿desea recuperarla? s/n");
                        opcion = scanner.nextLine().toLowerCase().charAt(0);
                    } while (!Helper.verificarSN(opcion));
                    if (opcion == 'n') {
                        flag = "n";
                        break;
                    }
                    do {
                        System.out.println("Ingrese su contrasenia, ingrese 'n' para salir.");
                        contrasenia = scanner.nextLine();
                        if (contrasenia == "n") {
                            flag = "n";
                            break;
                        }
                    } while (!(Helper.verificarMismaContrasenia(contrasenia, usuariosEnElSistema.get(email).getContrasenia())));
                    usuariosEnElSistema.get(email).setActivo(true);
                    System.out.println("El usuario se reestablecio correctamente.");
                    return usuariosEnElSistema.get(email);
                }

                try {
                    if (Helper.verificarEmail(email) && !verificarUsuarioExistente(email)) {
                        break;  // Sale del bucle si el email es valido
                    }
                } catch (FormatoInvalidoException | UsuarioYaExistenteException e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("Error al verificar el usuario: " + e.getMessage());
            }
        }

        while (!flag.equals("n")) {
            System.out.println("Ingrese su nombre de usuario: ");
            nombre = scanner.nextLine();
            flag = nombre;

            try {
                if (Helper.verificarNombre(nombre)) {
                    break;  // Sale del bucle si el nombre es válido
                }
            } catch (FormatoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        while (!flag.equals("n")) {
            System.out.println("Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();
            flag = contrasenia;

            try {
                if (Helper.verificarContrasenia(contrasenia)) {
                    break;  // Sale del bucle si el contrasenia es válido
                }
            } catch (FormatoInvalidoException e) {
                System.out.println(e.getMessage());
            }
        }

        while (!flag.equals("n")) {
            System.out.println("Ingrese nuevamente su contrasenia: ");
            contraseniaDeNuevo = scanner.nextLine();
            flag = contraseniaDeNuevo;

            try {
                if (Helper.verificarMismaContrasenia(contrasenia, contraseniaDeNuevo)) {
                    break;  // Sale del bucle si las contrasenias son iguales
                }
            } catch (NoCoincideException e) {
                System.out.println(e.getMessage());
            }
        }
        Usuario usuario = new Usuario(email, nombre, contrasenia);
        return usuario;
    }

    public void guardarRegistro(Usuario usuario) throws Exception {

        usuariosEnElSistema.put(usuario.getEmail(), usuario);
        ArrayList<Usuario> usuarios = getUsuariosList();
        FileHandler.guardarListaUsuarios(usuarios);
    }

    public boolean verificarUsuarioExistente(String email) throws UsuarioYaExistenteException {
        if (usuariosEnElSistema.containsKey(email)) {
            throw new UsuarioYaExistenteException("Ya existe un usuario con este correo");
        }
        return false;
    }

    public boolean verificarUsuarioRegistrado(String email) throws UsuarioNoRegistradoException {
        if (!usuariosEnElSistema.containsKey(email)) {
            throw new UsuarioNoRegistradoException("No se encuentra ninguna cuenta asociada al correo ingresado");
        }
        return false;
    }
    public boolean verificarUsuarioActivo(String email) throws UsuarioNoDadoDeBajaException {
        if (!usuariosEnElSistema.get(email).isActivo()) {
            throw new UsuarioNoDadoDeBajaException();
        }
        return false;
    }

    public void inicioDeSesion() {
        Scanner scanner = new Scanner(System.in);
        String email = "";
        String contrasenia = "";
        String contraseniaUsuario = "";
        String flag = "c";
        System.out.println("Ingrese n en cualquier momento para cancelar el inicio de sesion.");

        while (!flag.equals("n")) {
            System.out.println("Ingrese su gmail: ");
            email = scanner.nextLine();
            flag = email;

            try {
                if (!verificarUsuarioRegistrado(email) && usuariosEnElSistema.get(email).isActivo()) { ///Idealmente, la verificacion de que el mail exista iria en el helper
                    break;  // Sale del bucle si existe una cuenta asociada al email
                } else if (!usuariosEnElSistema.get(email).isActivo()) {
                    throw new UsuarioDadoDeBajaException("La cuenta asociada a este email se encuentra dada de baja");
                }
            } catch (UsuarioNoRegistradoException | UsuarioDadoDeBajaException e) {
                System.out.println(e.getMessage());
            }
        }

        contraseniaUsuario = usuariosEnElSistema.get(email).getContrasenia(); ///Una vez que encontre un email valido, copio la contrasenia de ese usuario en una contrasenia auxiliar, y verifico que la contrasenia ingresada sea igual a esta.

        while (!flag.equals("n")) {
            System.out.println("Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();
            flag = contrasenia;

            try {
                if (Helper.verificarMismaContrasenia(contrasenia, contraseniaUsuario)) {
                    break;  // Sale del bucle si las contrasenias son iguales
                }
            } catch (NoCoincideException e) {
                System.out.println("Contrasenia Incorrecta");
            }
        }

        SesionActiva.iniciarSesion(usuariosEnElSistema.get(email)); //Una vez que el usuario se logeo exitosamente, guardo el usuario logeado en mi clase de sesionActiva;
    }
public void recuperarCuenta (Scanner scanner){

    System.out.println("Ingrese el email de la cuenta a recuperar");
    String email = scanner.nextLine();
    String contrasenia;
    try {
        if (!verificarUsuarioRegistrado(email) && !verificarUsuarioActivo(email)){
            System.out.println("Ingrese la contrasenia");
            contrasenia = scanner.nextLine();
            if (Helper.verificarMismaContrasenia(contrasenia,usuariosEnElSistema.get(email).getContrasenia()))
            {
                usuariosEnElSistema.get(email).setActivo(true);
            }
        }
    } catch (UsuarioNoRegistradoException | UsuarioNoDadoDeBajaException | NoCoincideException e) {
        System.out.println(e.getMessage());
    }
}

    public void setUsuariosEnElSistema(List<Usuario> usuarios) {
        for (Usuario usuario : usuarios) {
            usuariosEnElSistema.put(usuario.getEmail(), usuario);
        }
    }

    public ArrayList<Usuario> getUsuariosList() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        // No hace falta verificar repeticion ya que ya fue hecho en el agregarUsuario.
        for (Map.Entry<String, Usuario> entry : usuariosEnElSistema.entrySet()) {
            usuarios.add(entry.getValue());
        }
        return usuarios;
    }

    public void mejorarPlan(String email) throws UsuarioYaExistenteException, UsuarioNoRegistradoException {
        Usuario usuario = null;
        for (Map.Entry<String, Usuario> entry : usuariosEnElSistema.entrySet()) {
            if (entry.getValue().getEmail().equals(email)) {
                if (entry.getValue().isPremium()) {
                    throw new UsuarioYaExistenteException("El usuario ya es premium.");
                }
                usuario = entry.getValue();
                usuario.setPremium(true);
            }
        }
        if (usuario == null) {
            throw new UsuarioNoRegistradoException("El usuario no fue encontrado.");
        }
    }
}


