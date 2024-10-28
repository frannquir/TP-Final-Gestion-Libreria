package Usuarios;

import java.util.ArrayList;

public final class UsuarioPremium extends Usuario {
    private ArrayList<String> categorias;

    public UsuarioPremium(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
        this.categorias = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "UsuarioPremium{" +
                "categorias=" + categorias +
                '}';
    }
}
