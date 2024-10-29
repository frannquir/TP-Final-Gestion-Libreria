package Usuarios;

import Interfaces.ITipoUsuario;

public class UsuarioFree extends Usuario implements ITipoUsuario {
    public UsuarioFree(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
    }

    public UsuarioFree() {
        super();
    }

    @Override
    public boolean esPremium() {
        return false;
    }
}
