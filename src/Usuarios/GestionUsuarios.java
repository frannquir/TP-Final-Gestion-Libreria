package Usuarios;

import Excepciones.*;
import Handlers.FileHandler;
import Handlers.Helper;
import Handlers.JSONUtiles;
import Handlers.SesionActiva;
import org.json.JSONObject;

import java.io.IOException;
import java.util.*;

import static Handlers.SesionActiva.getUsuarioActual;

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

    /**
     * Registro se encarga de llamar a crear usuario, y si el usuario se crea correctamente, guarda su informacion
     * en el archivo
     */
    public void registro() {
        Usuario usuario = crearUsuario();
        if (!(usuario == null)) {
            guardarRegistro(usuario);
            System.out.println("Usuario creado con exito!");
        }
    }

    /**
     * En este metodo se le solicita al usuario todos los atributos necesarios para crear una cuenta.
     * Estos atributos son chequeados a traves de otros metodos que se encargan de verificar que los valores tengan
     * el formato deseado, lo que asegura que todos los usuarios de la app sigan un mismo formato, ya que si no lo hacen
     * no pueden ser creados en primer lugar
     * @return Usuario si el registro se completo satisfactoriamente. Si se cancelo en algun momento, null
     */
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
                if (Helper.verificarEmail(email) && !verificarUsuarioExistente(email)) {
                    break;  // Sale del bucle si el email es valido
                }
            } catch (FormatoInvalidoException | UsuarioYaExistenteException e) {
                System.out.println("Email no válido: "+e.getMessage());
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
                System.out.println("Nombre no válido: "+e.getMessage());
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
                System.out.println("Contrasenia no válida: "+e.getMessage());
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
        if(!flag.equals("n")){ //Si el registro fue exitoso, se crea un nuevo usuario con todos los datos validados y se retorna
            Usuario usuario = new Usuario(email, nombre, contrasenia);
            return usuario;
        }
        ///Si en algun momento se pulso n para salir, el registro se cancela y se retorna null
        System.out.println("Registro cancelado");
        return null;
    }

    public void guardarRegistro(Usuario usuario) {

        usuariosEnElSistema.put(usuario.getEmail(), usuario);
        ArrayList<Usuario> usuarios = getUsuariosList();
        try {
            FileHandler.guardarListaUsuarios(usuarios);
        } catch (IOException e) {
            System.out.println(e.getMessage());;
        }
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

    public boolean verificarUsuarioInactivo(String email) throws UsuarioNoDadoDeBajaException { ///Si el usuario es inactivo, retorno true. Si el usuario es activo, lanzo excepcion
        if (usuariosEnElSistema.get(email).isActivo()) {
            throw new UsuarioNoDadoDeBajaException();
        }
        return true;
    }

    public boolean verificarUsuarioActivo(String email) throws UsuarioDadoDeBajaException { //Si el usuario es activo, retorno true. Si es inactivo, tiro la excepcion
        if (!usuariosEnElSistema.get(email).isActivo()) {
            throw new UsuarioDadoDeBajaException();
        }
        return true;
    }

    /**
     * El metodo de iniciar sesion se encarga de solicitar al usuario todos los datos para ingresar a su cuenta
     * Si el usuario ingresa un email asociado a una cuenta previamente ingresada, se le solicitara que ahora
     * ingrese la contrasenia, y una vez que ingrese ambos valores de forma correcta, se cargara ese usuario en
     * la clase SesionActiva, la cual contiene toda la informacion del usuario que se encuentra logeado.
     * El mismo puede cancelar el inicio de sesion en cualquier momento ingresando n, y no se cargara ninguna cuenta en SesionActiva
     */
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

            if (!flag.equals("n")) {
                try {
                    if (!verificarUsuarioRegistrado(email) && verificarUsuarioActivo(email)) { ///Si el usuario existe y es una cuenta activa, salgo del bucle
                        break;  // Sale del bucle si existe una cuenta asociada al email
                    }
                } catch (UsuarioNoRegistradoException | UsuarioDadoDeBajaException e) {
                    System.out.println(e.getMessage());
                }
            }
        }

        if (!flag.equals("n")) {
            contraseniaUsuario = usuariosEnElSistema.get(email).getContrasenia(); ///Una vez que encontre un email valido, copio la contrasenia de ese usuario en una contrasenia auxiliar, y verifico que la contrasenia ingresada sea igual a esta.
        }

        while (!flag.equals("n")) {
            System.out.println("Ingrese su contrasenia: ");
            contrasenia = scanner.nextLine();
            flag = contrasenia;

            if (!flag.equals("n")) {
                try {
                    if (Helper.verificarMismaContrasenia(contrasenia, contraseniaUsuario)) {
                        break;  // Sale del bucle si las contrasenias son iguales
                    }
                } catch (NoCoincideException e) {
                    System.out.println("Contrasenia Incorrecta");
                }
            }
        }
        if (!flag.equals("n")) {
            SesionActiva.iniciarSesion(usuariosEnElSistema.get(email)); //Una vez que el usuario se logeo exitosamente, guardo el usuario logeado en mi clase de sesionActiva;
        }
    }

    /**
     * El metodo recuperar cuenta permite a un usuario dar de alta su cuenta, si esta
     * fue previamente dada de baja. Para eso, deberá ingresar un correo asociado a una cuenta
     * que esté dada de baja, y una vez que lo haga, se le pedira que ingrese la contrasenia para verificar
     * que le pertenece. Si ingresa la contrasenia correctamente, se actualiza el estado de la cuenta
     * y se guardan los cambios en el archivo
     */
    public void recuperarCuenta() { /// Parece funcionar correctamente

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el email de la cuenta a recuperar");
        String email = scanner.nextLine();
        String contrasenia;
        try {
            if (!verificarUsuarioRegistrado(email)) { ///Si el usuario existe
                if (verificarUsuarioInactivo(email)) { /// Si el usuario no es activo
                    System.out.println("Ingrese la contrasenia");
                    contrasenia = scanner.nextLine();
                    if (Helper.verificarMismaContrasenia(contrasenia, usuariosEnElSistema.get(email).getContrasenia())) {
                        usuariosEnElSistema.get(email).setActivo(true);
                        ArrayList<Usuario> usuarios = getUsuariosList();
                        try {
                            FileHandler.guardarListaUsuarios(usuarios);
                        } catch (IOException e) {
                            System.out.printf(e.getMessage());;
                        }
                        System.out.printf("La cuenta se reestablecio correctamente");
                    }
                }
            }
        } catch (UsuarioNoDadoDeBajaException | NoCoincideException | UsuarioNoRegistradoException e) {
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

    /**
     * Mejorar plan actualiza el atributo esPremium de un usuario especifico
     * @param email el email del usuario que va a cambiar de plan
     * @throws UsuarioYaExistenteException si el usuario ya es premium
     * @throws UsuarioNoRegistradoException si no existe el usuario buscado (no deberia lanzarse nunca, ya que para cambiar de plan el usuario debe estar logeado)
     */
    public void mejorarPlan(String email) throws UsuarioYaExistenteException, UsuarioNoRegistradoException, IOException {
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
        List<Usuario> listaUsuariosActualizada = new ArrayList<>(usuariosEnElSistema.values()); /// Lo vuelve una List porque es lo que recibe el metodo guardarListaUsuario por parametro.
        FileHandler.guardarListaUsuarios(listaUsuariosActualizada);
        if (usuario == null) {
            throw new UsuarioNoRegistradoException("El usuario no fue encontrado.");
        }
    }

    /**
     * Da de baja a un usuario.
     * Pone el atributo activo del usuario en false en el HashMap y lo guarda en el json al finaliizar la ejecucion.
     * @param usuario recibe el usuario que está en SesionActiva.
     */
    public void darDeBajaUsuario (Usuario usuario) throws IOException {
        usuariosEnElSistema.get(usuario.getEmail()).setActivo(false);
        List<Usuario> listaUsuariosActualizada = new ArrayList<>(usuariosEnElSistema.values()); /// Lo vuelve una List porque es lo que recibe el metodo guardarListaUsuario por parametro.
        FileHandler.guardarListaUsuarios(listaUsuariosActualizada);
        System.out.println("La cuenta asociada al gmail:" + getUsuarioActual().getEmail() + " fue dada de baja.\n");
    }
}


