package com.example.metallikman.rebuc;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ubaldo Torres Juárez on 26/10/2017.
 */

public class User {

    private Context context;
    private SharedPreferences sharedPreferences;
    private String email;
    private String rol;
    private String idUser;
    private String nombreCompleto;

    public String getNombreCompleto() {
        nombreCompleto=sharedPreferences.getString("nombre","");
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        sharedPreferences.edit().putString("nombre",nombreCompleto).commit();
        this.nombreCompleto = nombreCompleto;
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
