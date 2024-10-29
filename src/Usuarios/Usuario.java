package Usuarios;

import java.util.Objects;
import java.util.UUID;

public abstract class Usuario {
    private String email;
    private String nombreUsuario;
    private String contrasenia;
    private boolean activo;
    private String identificador;

    public Usuario(String email, String nombreUsuario, String contrasenia) {
        this.email = email;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.activo = true;
        this.identificador = UUID.randomUUID().toString();
    }

    public Usuario() {
        this("", "", "");
        this.activo = false;
        this.identificador = null;
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
        return "Usuarios{" +
                "email='" + email + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", activo=" + activo +
                '}';
    }

}
