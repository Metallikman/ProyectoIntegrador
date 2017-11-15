package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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

import java.util.HashMap;
import java.util.Map;

import adapters.ReportesAdapter;
import modelos.Tickets;
import modelos.User;

public class DetallesUsuarioActivity extends AppCompatActivity {

    private EditText txtDUApellido,txtDUNombre,txtDUCorreo;
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

    }

    /**
     * Permite la modificacion de un usuario
     * <p>
     * Recupera los datos ingresados en los EditText y
     * modifica en la base de datos los datos del usuario.
     *
     */
    public void modificarUsuario(View v){
        String URL_POST=getResources().getString(R.string.host)+"/pi/api/updateUser.php";
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
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
