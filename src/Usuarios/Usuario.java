package Usuarios;

import Interfaces.IMostrable;
import Interfaces.IToJson;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;
import java.util.UUID;

public class Usuario implements IToJson, IMostrable {
    private String email;
    private String nombreUsuario;
    private String contrasenia;
    private boolean activo;
    private String identificador;

    public Usuario(String email, String nombreUsuario, String contrasenia) {
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        activo = true;
        identificador = UUID.randomUUID().toString();
    }

    public Usuario(String email, String nombreUsuario, String contrasenia, boolean activo, String identificador) {
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.activo = activo;
        this.identificador = identificador;
    }

    public Usuario() {
        this("", "", "", false, "");

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + '{' +
                "email='" + email + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", activo=" + activo +
                ", identificador='" + identificador + '\'' +
                '}';
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("nombreUsuario", nombreUsuario);
        json.put("email", email);
        json.put("identificador", identificador);
        json.put("contrasenia", contrasenia);
        json.put("activo", activo);
        return json;
    }

    public void fromJSON(JSONObject jsonObject) throws JSONException {
        setNombreUsuario(jsonObject.getString("nombreUsuario"));
        setEmail(jsonObject.getString("email"));
        setIdentificador(jsonObject.getString("identificador"));
        setContrasenia(jsonObject.getString("contrasenia"));
        setActivo(jsonObject.getBoolean("activo"));
    }

    // En nuestro programa no mostramos el identificador. Nos parece mas privado.
    public String mostrar() {
        var msj = new StringBuilder();
        String contraseniaOculta = "*".repeat(getContrasenia().length());
        msj.append("--- Mi perfil ---\n")
                .append("Nombre de usuario: ").append(getNombreUsuario()).append("\n")
                .append("Gmail: ").append(getEmail()).append("\n")
                .append("Contrasenia: ").append(contraseniaOculta);
        return msj.toString();
    }
}
