package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
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

import adapters.ComentariosAdapter;
import customsDialogs.CustomDialogCalificacion;
import modelos.Comentarios;
import modelos.User;

public class DetallesReportesUniversitario extends AppCompatActivity {

    private ArrayList arComentarios = new ArrayList<Comentarios>();
    private TextView txvRepAsunto, txvRepFechaAlta, txvRepFechaCierre,txvRepUsuario;
    private EditText txtComentario;
    private ListView lstComentarios;
    private Button cmdDRUComentar,cmdDRUCerrarTicket ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reportes_universitario);

        lstComentarios=(ListView)findViewById(R.id.lstComentarios);
        getComentarios();

        //Declaracion de botones y textviews
        txtComentario=(EditText)findViewById(R.id.txtComentario);
        txvRepAsunto=(TextView)findViewById(R.id.txvComUsuario);
        txvRepFechaAlta=(TextView)findViewById(R.id.txvComComentario);
        txvRepFechaCierre=(TextView)findViewById(R.id.txvRepFechaCierre);
        txvRepUsuario=(TextView)findViewById(R.id.txvRepUsuario);
        cmdDRUComentar = (Button)findViewById(R.id.cmdDRUComentar);
        cmdDRUCerrarTicket = (Button)findViewById(R.id.cmdDRUCerrarTicket);


        if(!getIntent().getStringExtra("fechaCierre").equals("null")){
            txtComentario.setEnabled(false);
            cmdDRUComentar.setEnabled(false);
            cmdDRUCerrarTicket.setEnabled(false);
        }
        txvRepAsunto.setText(getIntent().getStringExtra("asunto"));
        txvRepFechaAlta.setText("Fecha de alta: "+getIntent().getStringExtra("fechaAlta"));
        txvRepFechaCierre.setText("Fecha de cierre: "+getIntent().getStringExtra("fechaCierre"));
        txvRepUsuario.setText("Reportado por: "+getIntent().getStringExtra("usuario"));



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_universitario, menu);
        return true;
    }

    public void comentar(View v){
        String URL_POST=getResources().getString(R.string.host)+"/pi/api/addComentario.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){
                            txtComentario.setText("");
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
                params.put("rol",user.getRol());
                params.put("idTicket",getIntent().getStringExtra("idTicket"));

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    public void getComentarios(){
        lstComentarios.setAdapter(null);
        String URL_POST=getResources().getString(R.string.host)+"/pi/api/getComentarios.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    arComentarios.clear();
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
                    comentariosAdapter.notifyDataSetChanged();
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

    public void abrirCalificar(View view){
        CustomDialogCalificacion cdd=new CustomDialogCalificacion(DetallesReportesUniversitario.this,getIntent().getStringExtra("idTicket"));
        cdd.show();
    }
}
