package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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
import java.util.Map;

public class DetallesReportesUniversitario extends AppCompatActivity {

    private ArrayList arComentarios = new ArrayList<Comentarios>();
    private TextView txvRepAsunto, txvRepFechaAlta, txvRepFechaCierre,txvRepUsuario;
    private EditText txtComentario;
    private ListView lstComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reportes_universitario);
        lstComentarios=(ListView)findViewById(R.id.lstComentarios);
        getComentarios();

        txtComentario=(EditText)findViewById(R.id.txtComentario);
        txvRepAsunto=(TextView)findViewById(R.id.txvComUsuario);
        txvRepFechaAlta=(TextView)findViewById(R.id.txvComComentario);
        txvRepFechaCierre=(TextView)findViewById(R.id.txvRepFechaCierre);
        txvRepUsuario=(TextView)findViewById(R.id.txvRepUsuario);

        txvRepAsunto.setText(getIntent().getStringExtra("asunto"));
        txvRepFechaAlta.setText("Fecha de alta: "+getIntent().getStringExtra("fechaAlta"));
        txvRepFechaCierre.setText("Fecha de cierre: "+getIntent().getStringExtra("fechaCierre"));
        txvRepUsuario.setText("Reportado por: "+getIntent().getStringExtra("usuario"));

    }

    public void comentar(View v){
        String URL_POST="http://dogebox.ddns.net/pi/api/addComentario.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){
                            Toast.makeText(getApplication(),rec.getString("success"),Toast.LENGTH_SHORT).show();
                            getComentarios();
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
                User user = new User(DetallesReportesUniversitario.this);
                Map<String,String > params=new HashMap<String,String>();
                String comentario=txtComentario.getText().toString();

                params.put("comentario",comentario);
                params.put("idUsuario",user.getIdUser());
                params.put("idTicket",getIntent().getStringExtra("idTicket"));

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    public void getComentarios(){
        lstComentarios.setAdapter(null);
        String URL_POST="http://dogebox.ddns.net/pi/api/getComentarios.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("fecha")){
                            arComentarios.add(new Comentarios(
                                    rec.getString("fecha"),
                                    rec.getString("comentario"),
                                    rec.getString("nombreCompleto")
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    ComentariosAdapter comentariosAdapter = new ComentariosAdapter(DetallesReportesUniversitario.this, arComentarios);

                    lstComentarios.setAdapter(comentariosAdapter);
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
                params.put("idTicket",getIntent().getStringExtra("idTicket"));
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
