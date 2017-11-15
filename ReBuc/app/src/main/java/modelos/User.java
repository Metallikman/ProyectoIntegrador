package modelos;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * @since 1.0
 * @version 1.0
 *
 * Permite el control de sesiones del usuario
 */

public class User {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String email;
    private String rol;
    private String idUser;
    private String nombreCompleto;
    private String idDependencia;


    public String getNombreCompleto() {
        nombreCompleto=sharedPreferences.getString("nombre","");
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        sharedPreferences.edit().putString("nombre",nombreCompleto).commit();
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdDependencia() {
        idDependencia=sharedPreferences.getString("idDependencia","");
        return idDependencia;
    }

    public void setIdDependencia(String idDependencia) {
        sharedPreferences.edit().putString("idDependencia",idDependencia).commit();
        this.idDependencia = idDependencia;
    }

    public String getIdUser() {
        idUser=sharedPreferences.getString("idUser","");
        return idUser;
    }

    public void setIdUser(String idUser) {
        sharedPreferences.edit().putString("idUser",idUser).commit();
        this.idUser = idUser;
    }

    public String getRol() {
        rol=sharedPreferences.getString("rol","");
        return rol;
    }

    public void setRol(String rol) {
        sharedPreferences.edit().putString("rol",rol).commit();
        this.rol = rol;
    }


    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email).commit();
    }

    public void remove(){
        sharedPreferences.edit().clear().commit();
    }


    public User(Context context){
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",Context.MODE_PRIVATE);
    }
}
