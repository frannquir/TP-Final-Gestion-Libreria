package Usuarios;

import Excepciones.FormatoInvalidoException;
import Excepciones.NoCoincideException;
import Excepciones.UsuarioNoRegistradoException;
import Excepciones.UsuarioYaExistenteException;
import Util.Helper;
import Util.SesionActiva;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GestionUsuarios {
    private HashMap<String, Usuario> usuariosEnElSistema; //La clave de cada usuario sera su gmail.

    public GestionUsuarios() {
        usuariosEnElSistema = new HashMap<>();
        usuariosEnElSistema.put("manuelpalacios@gmail.com",new UsuarioFree());
        ///usuariosEnElSistema = bajarUsuarios(); necesitamos inicializar el mapa con todos los usuarios del archivo json
    }

    private ArrayList<Usuario> bajarUsuarios() {
        ArrayList<Usuario> auxiliar = new ArrayList<>();

        return auxiliar;
    }

    public UsuarioFree crearUsuario() {
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
                if (Helper.verificarEmail(email) && !verificarUsuarioExistente(email)) { ///Idealmente, la verificacion de que el mail no se repita iria en el helper
                    break;  // Sale del bucle si el email es válido
                }
            } catch (FormatoInvalidoException | UsuarioYaExistenteException e) {
                System.out.println(e.getMessage());
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
        UsuarioFree usuario = new UsuarioFree(email, nombre, contrasenia);
        return usuario;
    }

    public void guardarRegistro(UsuarioFree usuarioFree) {

        usuariosEnElSistema.put(usuarioFree.getEmail(), usuarioFree);
        //Falta hacer una funcion que ahora pase de hashmap a arraylist, para luego pasar ese arraylist a JSONArray y finalmente sobreescribir el archivo de usuaruos con la lista que contiene el nuevo usuario

    }

    public boolean verificarUsuarioExistente(String email) throws UsuarioYaExistenteException {
        if (usuariosEnElSistema.containsKey(email)) {
            throw new UsuarioYaExistenteException("Ya existe un usuario con este correo");
        }
        return false;
    }

    public boolean verificarUsuarioRegistrado (String email) throws UsuarioNoRegistradoException{
        if (!usuariosEnElSistema.containsKey(email)){
            throw new UsuarioNoRegistradoException("No se encuentra ninguna cuenta asociada al correo ingresado");
        }
        return false;
    }

    public void inicioDeSesion (){
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
                if (!verificarUsuarioRegistrado(email)) { ///Idealmente, la verificacion de que el mail exista iria en el helper
                    break;  // Sale del bucle si existe una cuenta asociada al email
                }
            } catch (UsuarioNoRegistradoException e) {
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

}


