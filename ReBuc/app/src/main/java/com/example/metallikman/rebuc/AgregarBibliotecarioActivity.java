package com.example.metallikman.rebuc;

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

import adapters.ReportesAdapter;
import modelos.Tickets;


public class AgregarBibliotecarioActivity extends AppCompatActivity {

    private EditText txtABNombre,txtABApellido,txtABCorreo,txtABPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_bibliotecario);

        txtABNombre=(EditText)findViewById(R.id.txtABNombre);
        txtABApellido=(EditText)findViewById(R.id.txtABApellido);
        txtABCorreo=(EditText)findViewById(R.id.txtABCorreo);
        txtABPass=(EditText)findViewById(R.id.txtABPass);
    }

    /**
     * AgregaBibliotecario como responsable
     * <p>
     * Permite la creacion de bibliotecarios tomados de los EditText de la vista.
     *
     */
    public void agregarBibliotecario(View v){
        String URL_POST=getResources().getString(R.string.host)+"/pi/api/addBibliotecario.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);

                        if(rec.has("success")){
                            Toast.makeText(AgregarBibliotecarioActivity.this, rec.getString("success"), Toast.LENGTH_SHORT).show();
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
                params.put("nombre", txtABNombre.getText().toString());
                params.put("apellido", txtABApellido.getText().toString());
                params.put("correo", txtABCorreo.getText().toString());
                params.put("pass", txtABPass.getText().toString());
                params.put("dependencia","1");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
