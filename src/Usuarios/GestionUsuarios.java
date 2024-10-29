package Usuarios;

import Util.Helper;
import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GestionUsuarios {
    private HashMap<String, Usuario> usuariosEnElSistema; //La clave de cada usuario sera su gmail.

    public GestionUsuarios() {
        usuariosEnElSistema = new HashMap<>();
        ///usuariosEnElSistema = bajarUsuarios();
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

        do {
            System.out.println("Ingrese su gmail: ");    ///email repetido agregar verificar    (HashMap con clave gmail
            email = scanner.nextLine();
            flag = email;

        } while (!Helper.verificarEmail(email) && !flag.equals("n"));

        if (!flag.equals("n"))
            do {
                System.out.println("Ingrese el nombre de usuario: ");
                nombre = scanner.nextLine();
                flag = nombre;
            } while (!Helper.verificarNombre(nombre) && !flag.equals("n"));

        if (!flag.equals("n"))
            do {
                System.out.println("Ingrese su contrasenia: ");
                contrasenia = scanner.nextLine();
                flag = contrasenia;
            } while (!Helper.verificarContrasenia(contrasenia) && !flag.equals("n"));
                //Hacer un try catch por cada do while
        if (!flag.equals("n"))
            do {
                System.out.println("Ingrese nuevamente su contrasenia: ");
                contraseniaDeNuevo = scanner.nextLine();
                flag = contraseniaDeNuevo;
            } while (!Helper.verificarMismaContrasenia(contrasenia, contraseniaDeNuevo) && !flag.equals("n"));

        UsuarioFree usuario = new UsuarioFree(email, nombre, contrasenia);
        scanner.close();
        return usuario;
    }

   /* public void guardarUsuario(Usuario usuario) { ///Metodo que guarda en el archivo el usuario creado

        Usuario auxiliar = new UsuarioFree(email, nombre, contrasenia);
        usuariosEnElSistema.put(auxiliar.getEmail(), auxiliar);

        JSONArray jsonArray = new JSONArray();
        jsonArray = arrayUsuarioToJSONArray(usuariosEnElSistema);

    }*/
}


