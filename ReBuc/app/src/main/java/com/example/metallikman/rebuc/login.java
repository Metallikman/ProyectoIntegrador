package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class login extends AppCompatActivity {

    EditText txtCorreo, txtContraseña;
    Button btnIniciar, btnRegistro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtContraseña = (EditText)findViewById(R.id.txtContraseña);
        btnIniciar = (Button)findViewById(R.id.btnIniciar);
        btnRegistro = (Button)findViewById(R.id.btnRegistrarse);
    }

    public void lanzarRegistro(){
        Intent intent = new Intent(login.this, Registro.class);
        finish();
        startActivity(intent);
    }
}
