package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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

import adapters.ReportesAdapter;
import modelos.Tickets;
import modelos.User;
import utilidades.StringWithTags;

public class DetallesUsuarioActivity extends AppCompatActivity {

    private EditText txtDUApellido,txtDUNombre,txtDUCorreo;
    private Spinner spinnerRol, spinnerDependecias;
    private boolean isadmin=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_usuario);
        txtDUNombre=(EditText)findViewById(R.id.txtDUNombre);
        txtDUApellido=(EditText)findViewById(R.id.txtDUApellidos);
        txtDUCorreo=(EditText)findViewById(R.id.txtDUCorreo);

        txtDUNombre.setText(getIntent().getStringExtra("nombre"));
        txtDUApellido.setText(getIntent().getStringExtra("apellido"));
        txtDUCorreo.setText(getIntent().getStringExtra("email"));
        spinnerRol=(Spinner)findViewById(R.id.spnDUDRol);
        spinnerDependecias=(Spinner)findViewById(R.id.spnDUDependencia);
        isadmin=getIntent().getBooleanExtra("isAdmin",false);
        if (isadmin==false)
            spinnerDependecias.setVisibility(View.INVISIBLE);
        else
            getDependencias();

        fillRoles();

    }

    /**
     * Permite la modificacion de un usuario
     * <p>
     * Recupera los datos ingresados en los EditText y
     * modifica en la base de datos los datos del usuario.
     *
     */
    public void modificarUsuario(View v){
        String URL_POST=getResources().getString(R.string.host)+"updateUser.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){
                            Toast.makeText(getApplication(),rec.getString("success"),Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getApplication(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String > params=new HashMap<String,String>();
                params.put("nombre", txtDUNombre.getText().toString());
                params.put("apellido", txtDUApellido.getText().toString());
                params.put("email", txtDUCorreo.getText().toString());
                params.put("idUsuario", getIntent().getStringExtra("idUsuario"));
                StringWithTags s = (StringWithTags) spinnerRol.getItemAtPosition(spinnerRol.getSelectedItemPosition());
                String rol = s.tag.toString();
                if (isadmin){
                    StringWithTags s2 = (StringWithTags) spinnerRol.getItemAtPosition(spinnerDependecias.getSelectedItemPosition());
                    String dependencia = s2.tag.toString();
                    params.put("dependencia",dependencia);
                }


                params.put("rol",rol);


                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
    private void fillRoles(){
        List<StringWithTags> roles = new ArrayList<StringWithTags>();
        roles.add(new StringWithTags("Bibliotecario","2"));
        roles.add(new StringWithTags("Unversitario","3"));
        if(isadmin){
            roles.add(new StringWithTags("Responsable","4"));
        }
        ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (DetallesUsuarioActivity.this, android.R.layout.simple_spinner_item, roles);
        spinnerRol.setAdapter(adap);
    }

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

                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (DetallesUsuarioActivity.this, android.R.layout.simple_spinner_item, dependencias);

                    spinnerDependecias.setAdapter(adap);
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

}
