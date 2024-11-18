package Handlers;

import Usuarios.Usuario;
import Usuarios.UsuarioPremium;

public class SesionActiva {
    ///La clase sesion activa nos permite guardar en una variable estatica el usuario que inicio sesion.
    ///Es especialmente util para que cualquier parte del programa pueda acceder a la informacion del usuario que se encuentra activo
    private static Usuario usuarioActivo;

    public static void iniciarSesion(Usuario usuario) {
        usuarioActivo = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActivo;
    }

    public static void cerrarSesion() {
        usuarioActivo = null;
    }

    public static boolean esUsuarioPremium() {
        return usuarioActivo instanceof UsuarioPremium;
    }

}
