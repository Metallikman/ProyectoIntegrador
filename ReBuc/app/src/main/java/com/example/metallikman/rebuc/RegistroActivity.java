package com.example.metallikman.rebuc;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utilidades.StringWithTags;


/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 *
 * Registro activity.                           (1)
 * <p>
 * Formulario para poder ingresar los datos del nuevo universitario.
 * <p>
 */
public class RegistroActivity extends AppCompatActivity {

    private Context mContext;
    private EditText txtRegistroCorreo, txtRegistroContraseña, txtRegistroNombre, txtRegistroApellido;
    private Spinner spnDependencias;
    private ArrayAdapter<String> adapter;

    List<String> initialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getDependencias();
        txtRegistroCorreo = (EditText)findViewById(R.id.txtRegistroCorreo);
        txtRegistroContraseña = (EditText)findViewById(R.id.txtRegistroContraseña);
        txtRegistroNombre = (EditText)findViewById(R.id.txtRegistroNombre);
        txtRegistroApellido = (EditText)findViewById(R.id.txtRegistroApellidos);
        spnDependencias = (Spinner)findViewById(R.id.spnDependencias);
    }

    /**
     * Obtiene las dependencias.                           (1)
     * <p>
     * Obtiene las dependencias para introducirlas
     * en un spinner y seleccionar la opcion deseada.
     * <p>
     */
    private void getDependencias() {
        String URL_POST=getResources().getString(R.string.host)+"getDependencias.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    List<StringWithTags> dependencias = new ArrayList<StringWithTags>();
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        //dependencias.add("" + rec.getString("dependencia"));
                        dependencias.add(new StringWithTags(rec.getString("dependencia"), rec.getString("de_id")));

                    }

                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (RegistroActivity.this, android.R.layout.simple_spinner_item, dependencias);

                    spnDependencias.setAdapter(adap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    /**
     * Envia los parametros para el registro del usuario(1)
     * <p>
     * Realiza la llamada POST a la API para poder hacer
     * el registro de un nuevo univesitario.
     * @param  v Es para el elemento en el layout que tiene asigando el evento onclick
     * <p>
     */
    public void registrarUsuario(View v){
        String URL_POST=getResources().getString(R.string.host)+"register.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){
                            Toast.makeText(getApplication(),rec.getString("success"),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistroActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                Map<String,String > params=new HashMap<String,String>();
                String email=txtRegistroCorreo.getText().toString();
                String pass=txtRegistroContraseña.getText().toString();
                String fname=txtRegistroNombre.getText().toString();
                String lname=txtRegistroApellido.getText().toString();
                StringWithTags s = (StringWithTags) spnDependencias.getItemAtPosition(spnDependencias.getSelectedItemPosition());
                String dependencia = s.tag.toString();

                params.put("email",email);
                params.put("pass",pass);
                params.put("fname",fname);
                params.put("lname",lname);
                params.put("dependencia",dependencia);

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

}
