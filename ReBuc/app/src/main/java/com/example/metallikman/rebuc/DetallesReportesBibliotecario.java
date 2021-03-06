package com.example.metallikman.rebuc;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
import customsDialogs.CustomDialogOpcionesTicketBiblio;
import customsDialogs.CustomDialogReasignarTicket;
import modelos.Comentarios;
import modelos.User;

public class DetallesReportesBibliotecario extends AppCompatActivity {

    private ArrayList arComentarios = new ArrayList<Comentarios>();
    private TextView txvDRBAsunto, txvDRBFechaAlta, txvDRBFechaCierre,txvDRBUsuario, txvDRBBibliotecario;
    private EditText txtDRBComentario;
    private ListView lstDRBComentarios;
    private ImageView imgDRBStatus;
    private Button cmdDRBComentar, cmdDRBTomarTicket;
    private ScrollView scrollView;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_reportes_bibliotecario);

        lstDRBComentarios=(ListView)findViewById(R.id.lstDRBComentarios);
        getComentarios();

        txtDRBComentario=(EditText)findViewById(R.id.txtDRBComentario);
        txvDRBBibliotecario=(TextView) findViewById(R.id.txvDRBBibliotecario);
        txvDRBAsunto=(TextView)findViewById(R.id.txvDRBAsunto);
        txvDRBFechaAlta=(TextView)findViewById(R.id.txvDRBFechaAlta);
        txvDRBFechaCierre=(TextView)findViewById(R.id.txvDRBFechaCierre);
        txvDRBUsuario=(TextView)findViewById(R.id.txvDRBUsuario);
        imgDRBStatus=(ImageView)findViewById(R.id.imgDRBStatus);
        cmdDRBComentar=(Button)findViewById(R.id.cmdDRBComentar);
        cmdDRBTomarTicket=(Button)findViewById(R.id.cmdDRBTomarTicket);
        scrollView=(ScrollView)findViewById(R.id.scrollDRB);
        String fechaCierre=getIntent().getStringExtra("fechaCierre");

        if(!fechaCierre.equals("Sin fecha de cierre aún")){
            txtDRBComentario.setEnabled(false);
            cmdDRBComentar.setEnabled(false);
        }

        txvDRBAsunto.setText(getIntent().getStringExtra("asunto"));
        txvDRBFechaAlta.setText("Fecha de alta: "+getIntent().getStringExtra("fechaAlta"));
        txvDRBFechaCierre.setText("Fecha de cierre: "+fechaCierre);
        txvDRBUsuario.setText("Reportado por: "+getIntent().getStringExtra("usuario"));
        txvDRBUsuario.setText("Reportado por: "+getIntent().getStringExtra("usuario"));
        imgDRBStatus.setImageResource(Integer.parseInt(getIntent().getStringExtra("status")));
        txvDRBBibliotecario.setText(getIntent().getStringExtra("bibliotecario"));

        if(!getIntent().getStringExtra("bibliotecario").equals("Sin bibliotecario asignado"))
            cmdDRBTomarTicket.setEnabled(false);

        scrollView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                lstDRBComentarios.getParent().requestDisallowInterceptTouchEvent(false);

                return false;
            }
        });

        lstDRBComentarios.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                // Disallow the touch request for parent scroll on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });
    }

    /**
     * Crea el menu de opciones del bibliotecario
     * <p>
     * Crea el menu de opciones del bibliotecario definido en
     * menus/opciones_ticket_bibliotecario
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(getIntent().getBooleanExtra("isResponsable",false))
            inflater.inflate(R.menu.opciones_ticket_responsable, menu);
        else
            inflater.inflate(R.menu.opciones_ticket_bibliotecario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcionesTicketBibliotecario:
                Toast.makeText(getApplicationContext(),"Si funciona el click del menú",Toast.LENGTH_LONG).show();
                return true;
            case R.id.opcionesTicketStatus:
                CustomDialogOpcionesTicketBiblio cdot=new CustomDialogOpcionesTicketBiblio(DetallesReportesBibliotecario.this,getIntent().getStringExtra("idTicket"));
                cdot.show();
                return true;
            case R.id.opcionesTicketReasignar:
                CustomDialogReasignarTicket cdrt=new CustomDialogReasignarTicket(DetallesReportesBibliotecario.this,getIntent().getStringExtra("idTicket"));
                cdrt.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Agrega comentarios a un ticket                          (1)
     * <p>
     * Agrega un comentario realizado por el bibliotecario hacia
     * un determinado ticket
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
                User user = new User(DetallesReportesBibliotecario.this);
                Map<String,String > params=new HashMap<String,String>();
                String comentario=txtDRBComentario.getText().toString();

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
     * Obtiene los comentarios de un ticket                          (1)
     * <p>
     * Obitiene todos los comentarios de un ticket y los añade
     * a una listview personalizable.
     *
     */
    public void getComentarios(){
        lstDRBComentarios.setAdapter(null);
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

                    ComentariosAdapter comentariosAdapter = new ComentariosAdapter(DetallesReportesBibliotecario.this, arComentarios);
                    comentariosAdapter.notifyDataSetChanged();

                    lstDRBComentarios.setAdapter(comentariosAdapter);
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

    public void setBibliotecario(View v){
        String URL_POST=getResources().getString(R.string.host)+"takeTicket.php";
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
                User usr=new User (DetallesReportesBibliotecario.this);
                Map<String,String > params=new HashMap<String,String>();
                params.put("idTicket",getIntent().getStringExtra("idTicket"));
                params.put("idUser",usr.getIdUser());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

}
