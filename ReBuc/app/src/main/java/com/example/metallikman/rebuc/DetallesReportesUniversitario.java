package com.example.metallikman.rebuc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
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
    private TextView txvRepAsunto, txvRepFechaAlta, txvRepFechaCierre,txvRepUsuario,txvDRUBibliotecario;
    private EditText txtComentario;
    private ListView lstComentarios;
    private Button cmdDRUComentar,cmdDRUCerrarTicket ;
    private ImageView imgDRUStatus;
    private ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reportes_universitario);

        lstComentarios=(ListView)findViewById(R.id.lstComentarios);
        getComentarios();

        //Declaracion de botones y textviews
        txvDRUBibliotecario=(TextView)findViewById(R.id.txvDRUBibliotecario);
        txtComentario=(EditText)findViewById(R.id.txtComentario);
        txvRepAsunto=(TextView)findViewById(R.id.txvComUsuario);
        txvRepFechaAlta=(TextView)findViewById(R.id.txvComComentario);
        txvRepFechaCierre=(TextView)findViewById(R.id.txvRepFechaCierre);
        txvRepUsuario=(TextView)findViewById(R.id.txvRepUsuario);
        cmdDRUComentar = (Button)findViewById(R.id.cmdDRUComentar);
        cmdDRUCerrarTicket = (Button)findViewById(R.id.cmdDRUCerrarTicket);
        imgDRUStatus=(ImageView)findViewById(R.id.imgDRUStatus);
        scrollView=(ScrollView)findViewById(R.id.scrollDRU);


        if(!getIntent().getStringExtra("fechaCierre").equals("Sin fecha de cierre aún")){
            txtComentario.setEnabled(false);
            cmdDRUComentar.setEnabled(false);
            cmdDRUCerrarTicket.setEnabled(false);
        }
        txvRepAsunto.setText(getIntent().getStringExtra("asunto"));
        txvRepFechaAlta.setText("Fecha de alta: "+getIntent().getStringExtra("fechaAlta"));
        txvRepFechaCierre.setText("Fecha de cierre: "+getIntent().getStringExtra("fechaCierre"));
        txvRepUsuario.setText("Reportado por: "+getIntent().getStringExtra("usuario"));
        imgDRUStatus.setImageResource(Integer.parseInt(getIntent().getStringExtra("status")));
        txvDRUBibliotecario.setText(getIntent().getStringExtra("bibliotecario"));

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lstComentarios.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        lstComentarios.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    /**
     * Crea el menu de opciones
     * <p>
     * Crea el menu de opciones para un universiario.
     * se obtiene de res/menu/menu_general_universitario
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_universitario, menu);
        return true;
    }

    /**
     * Permite agregar un comentario a un ticket
     * <p>
     * Agrega un comentario agregado por el universitario
     * segun el id del ticket.
     *
     */
    public void comentar(View v){
        String URL_POST=getResources().getString(R.string.host)+"addComentario.php";
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

    /**
     * Obtiene todos los comentarios
     * <p>
     * Recupera los comentarios del ticket y los
     * muestra en un Custom ListView
     *
     */
    public void getComentarios(){
        lstComentarios.setAdapter(null);
        String URL_POST=getResources().getString(R.string.host)+"getComentarios.php";
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

    /**
     * Abre el customDialog de calificar.
     * <p>
     * El custom dialog permite cerrar y calificar el ticket.
     *
     */
    public void abrirCalificar(View view){
        CustomDialogCalificacion cdd=new CustomDialogCalificacion(DetallesReportesUniversitario.this,getIntent().getStringExtra("idTicket"));
        cdd.show();
    }
}
