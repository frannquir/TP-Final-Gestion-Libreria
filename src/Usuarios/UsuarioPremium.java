package Usuarios;

import Interfaces.ITipoUsuario;

public final class UsuarioPremium extends Usuario implements ITipoUsuario {

    public UsuarioPremium(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
    }

    @Override
    public String toString() {
        return "UsuarioPremium{" +
                "categorias=" +
                '}';
    }


    @Override
    public boolean esPremium() {
        return true;
    }
}
