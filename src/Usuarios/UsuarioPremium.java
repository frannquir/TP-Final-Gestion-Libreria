package Usuarios;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioPremium extends Usuario{
    public UsuarioPremium(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
    }

    public UsuarioPremium(String email, String nombreUsuario, String contrasenia, boolean activo, String identificador) {
        super(email, nombreUsuario, contrasenia, activo, identificador);
    }

    public UsuarioPremium() {
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return super.toJSON();
    }
    public void fromJSON(JSONObject jo) throws JSONException {
        super.fromJSON(jo);
    }
    public String mostrar() {
        return super.mostrar() + '\n' +
                "★ Esta cuenta es Premium ★";
    }
}
