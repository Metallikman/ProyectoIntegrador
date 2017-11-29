package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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

import modelos.User;

public class LevantarTicketActivity extends AppCompatActivity {

    private EditText txtSolicitud;
    private EditText txtDetallesTicket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levantar_ticket);
        txtSolicitud = (EditText)findViewById(R.id.txtSolicitud);
        txtDetallesTicket=(EditText)findViewById(R.id.txtDetallesTicket);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_universitario, menu);
        return true;
    }

    /**
     * Permite generar un ticket
     * <p>
     * El universitario realiza la creacion del ticket
     *
     */
    public void levantarTicket(View v){

        String URL_POST=getResources().getString(R.string.host)+"levantarTicket.php";
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

            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError{
                User user=new User(LevantarTicketActivity.this);

                Map<String,String > params=new HashMap<String,String>();
                String solicitud=txtSolicitud.getText().toString();
                String idUsuario= user.getIdUser();
                String detalles= txtDetallesTicket.getText().toString();

                params.put("solicitud",solicitud);
                params.put("idUsuario",idUsuario);
                params.put("detalles",detalles);

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);

    }
}
