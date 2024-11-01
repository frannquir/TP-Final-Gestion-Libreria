package Usuarios;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioFree extends Usuario {
    public UsuarioFree(String email, String nombreUsuario, String contrasenia) {
        super(email, nombreUsuario, contrasenia);
    }

    public UsuarioFree(String email, String nombreUsuario, String contrasenia, String identificador) {
        super(email, nombreUsuario, contrasenia, identificador);
    }

    public UsuarioFree() {
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
    public void fromJSON (JSONObject jsonObject) throws JSONException
    {
        super.fromJSON(jsonObject);
    }

}
