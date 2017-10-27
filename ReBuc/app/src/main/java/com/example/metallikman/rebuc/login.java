package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    EditText txtCorreo, txtContraseña;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtCorreo = (EditText)findViewById(R.id.txtCorreo);
        txtContraseña = (EditText)findViewById(R.id.txtContraseña);

    }

    public void lanzarRegistro(View v){
        Intent intent = new Intent(Login.this, Registro.class);
        startActivity(intent);
    }

    public void iniciarLogin(View v){
        String URL_POST="http://dogebox.ddns.net/pi/api/Login.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Intent intent;
                User user=new User(Login.this);
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("us_rol")){
                            int rol = rec.getInt("us_rol");
                            if(rol==1){
                                /*intent = new Intent(Login.this, UniversitarioActivity.class);
                                user.setEmail(rec.getString("us_correo"));
                                user.setRol(rec.getString("us_rol"));
                                user.setIdUser(rec.getString("us_id"));
                                startActivity(intent);
                                finish();*/
                            }else if(rol==2){
                                 intent = new Intent(Login.this, BibliotecarioActivity.class);
                                user.setEmail(rec.getString("us_correo"));
                                user.setRol(rec.getString("us_rol"));
                                user.setIdUser(rec.getString("us_id"));
                                startActivity(intent);
                                finish();
                            }else if(rol==3) {
                                intent = new Intent(Login.this, UniversitarioActivity.class);
                                user.setEmail(rec.getString("us_correo"));
                                user.setRol(rec.getString("us_rol"));
                                user.setIdUser(rec.getString("us_id"));
                                startActivity(intent);
                                finish();
                            }
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
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String > params=new HashMap<String,String>();
                String email=txtCorreo.getText().toString();
                String pass=txtContraseña.getText().toString();


                params.put("email",email);
                params.put("pass",pass);

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
