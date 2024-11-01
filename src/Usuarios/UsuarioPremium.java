package Usuarios;

import org.json.JSONException;
import org.json.JSONObject;

public final class UsuarioPremium extends Usuario {

    public UsuarioPremium(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
    }

    public UsuarioPremium(String email, String nombreUsuario, String contrasenia, String identificador) {
        super(email, nombreUsuario, contrasenia, identificador);
    }

    public UsuarioPremium() {
        this("", "", "", "");
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public JSONObject toJSON() throws JSONException {
        return super.toJSON();
    }

    @Override
    public void fromJSON(JSONObject jsonObject) throws JSONException {
        super.fromJSON(jsonObject);
    }

}
