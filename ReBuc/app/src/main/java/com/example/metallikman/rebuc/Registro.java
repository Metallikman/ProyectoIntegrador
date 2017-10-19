package com.example.metallikman.rebuc;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

public class Registro extends AppCompatActivity {

    private Context mContext;
    private EditText txtRegistrCorreo, txtRegistroContraseña, txtRegistroNombre, txtRegistroApellido;
    private Spinner spnDependencias;
    private ArrayAdapter<String> adapter;
    List<String> initialList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txtRegistrCorreo = (EditText)findViewById(R.id.txtRegistroCorreo);
        txtRegistroContraseña = (EditText)findViewById(R.id.txtRegistroContraseña);
        txtRegistroNombre = (EditText)findViewById(R.id.txtRegistroNombre);
        txtRegistroApellido = (EditText)findViewById(R.id.txtRegistroApellidos);
        spnDependencias = (Spinner)findViewById(R.id.spnDependencias);
    }
}
