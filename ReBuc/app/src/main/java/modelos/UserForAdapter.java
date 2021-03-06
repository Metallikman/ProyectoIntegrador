package modelos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * @since 1.0
 * @version 1.0
 *
 * Utilizada para el adapter de usuarios para luego mostarlos
 * en un ListView y mostarlos al responsable o administrador segun sea el caso.
 */

public class UserForAdapter {


    private String email;
    private String idRol;
    private String rol;
    private String nombre;
    private String apellido;
    private String idDependencia;
    private String dependencia;
    private String idStatus;
    private int idUsuario;

    public UserForAdapter(String email, String idRol,String rol, String nombre,String apellido, String idDependencia, String dependencia, String idStatus, int idUsuario) {
        this.email = email;
        this.idRol = idRol;
        this.rol=rol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.idDependencia = idDependencia;
        this.dependencia = dependencia;
        this.idStatus = idStatus;
        this.idUsuario = idUsuario;

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getStatus() {
        return idStatus;
    }

    public String getDependencia() {
        return dependencia;
    }

    public String getNombreCompleto() {

        return nombre + " " +apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getIdDependencia() {
        return idDependencia;
    }

    public String getIdRol() {
        return idRol;
    }

    public String getRol(){
        return rol;
    }

    public String getEmail() {
        return email;
    }
}
