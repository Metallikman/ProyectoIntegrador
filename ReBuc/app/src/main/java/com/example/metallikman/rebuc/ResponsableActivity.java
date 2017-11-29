package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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

import adapters.ReportesAdapter;
import modelos.Tickets;
import modelos.User;

public class ResponsableActivity extends AppCompatActivity {
    private ArrayList tickets = new ArrayList<Tickets>();
    private ListView lstRespTickets;
    private TextView txtRespBusquedaTicket;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_responsable);
        user= new User(ResponsableActivity.this);
        setTitle("Bienvenido " + user.getNombreCompleto());
        lstRespTickets=(ListView)findViewById(R.id.lstRespTickets);
        txtRespBusquedaTicket=(EditText)findViewById(R.id.txtRespBusquedaTicket);

        getReportes();

        lstRespTickets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Tickets ticketAMostrar = (Tickets) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), DetallesReportesBibliotecario.class);
                intent.putExtra("asunto", ticketAMostrar.getSolicitud());
                intent.putExtra("fechaAlta", ticketAMostrar.getFechaAlta());
                intent.putExtra("fechaCierre", ticketAMostrar.getFechaCierre());
                intent.putExtra("usuario", ticketAMostrar.getSolictante());
                intent.putExtra("idTicket",String.valueOf(ticketAMostrar.getFolio()));
                intent.putExtra("status",String.valueOf(ticketAMostrar.getStatus()));
                intent.putExtra("bibliotecario",ticketAMostrar.getBibliotecario());
                intent.putExtra("isResponsable",true);
                startActivity(intent);

            }
        });

        txtRespBusquedaTicket.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getReportesBusqueda();
            }
        });
    }

    /**
     * Recupera nuevamente todos los reportes
     * cuando la actividad tiene de nuevo el foco
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        getReportes();
    }

    /**
     * Controla los clicks del menu
     * <p>
     * Abre las actividades segun el elemento clickeado en
     * el menu del responsable
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcionVerUsuariosResponsable:
                intent = new Intent(ResponsableActivity.this, ControlUsuarios.class);
                startActivity(intent);
                return true;
            case R.id.opcionLogoutResponsable:
                new User(ResponsableActivity.this).remove();
                intent = new Intent(ResponsableActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcionAgregarBiblioResponsable:
                intent = new Intent(ResponsableActivity.this, AgregarBibliotecarioActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Crea el menu de opciones del responsable
     *
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_responsable, menu);
        return true;
    }

    /**
     * Permite el cierre de sesion
     */
    public void logout(View v){
        new User(ResponsableActivity.this).remove();
        Intent intent = new Intent(ResponsableActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

    /**
     * Obtiene todos los reportes
     * <p>
     * Obtiene todos los reportes y los muestra en un custom Listview.
     *
     */
    private void getReportes(){
        String URL_POST=getResources().getString(R.string.host)+"getTickets.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    tickets.clear();
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        String calificacion;
                        String fechaCierre;
                        String nombreBibliotecario;
                        if(rec.getString("calificacion")=="null"){
                            calificacion="Sin calificar";
                        }else{
                            calificacion=rec.getString("calificacion");
                        }
                        if(rec.getString("fechaCierre")=="null"){
                            fechaCierre="Sin fecha de cierre aún";
                        }else{
                            fechaCierre=rec.getString("fechaCierre");
                        }
                        if (rec.getString("nombreBibliotecario")=="null")
                            nombreBibliotecario="Sin bibliotecario asignado";
                        else
                            nombreBibliotecario=rec.getString("nombreBibliotecario")+" "+rec.getString("apellidoBibliotecario");
                        if(rec.has("folio")){
                            int status=R.drawable.reports;
                            String statusString=rec.getString("status");
                            if(statusString.equals("3")){
                                status=R.drawable.st3;
                            }else if(statusString.equals("4")){
                                status=R.drawable.st4;
                            }else if(statusString.equals("6")){
                                status=R.drawable.st6;
                            }else if(statusString.equals("7")){
                                status=R.drawable.st7;
                            }

                            tickets.add(new Tickets(
                                    rec.getInt("folio"),
                                    rec.getString("peticion"),
                                    calificacion,
                                    rec.getString("fechaAlta"),
                                    fechaCierre,
                                    rec.getString("nombreSolicitante")+" "+rec.getString("apellidoSolicitante"),
                                    status,
                                    nombreBibliotecario
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    ReportesAdapter reportesAdapter = new ReportesAdapter(ResponsableActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstRespTickets.setAdapter(reportesAdapter);
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
                User usr= new User(ResponsableActivity.this);
                Map<String,String > params=new HashMap<String,String>();
                params.put("idDependencia",usr.getIdDependencia());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    /**
     * Obtiene los reportes buscados
     * <p>
     * Obtiene todos los reportes segun el campo de busqueda
     * y los muestra en un custom Listview.
     *
     */
    private void getReportesBusqueda(){
        String URL_POST=getResources().getString(R.string.host)+"getTickets.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    tickets.clear();
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        String calificacion;
                        String fechaCierre;
                        String nombreBibliotecario;
                        if(rec.getString("calificacion")=="null"){
                            calificacion="Sin calificar";
                        }else{
                            calificacion=rec.getString("calificacion");
                        }
                        if(rec.getString("fechaCierre")=="null"){
                            fechaCierre="Sin fecha de cierre aún";
                        }else{
                            fechaCierre=rec.getString("fechaCierre");
                        }
                        if (rec.getString("nombreBibliotecario")=="null")
                            nombreBibliotecario="Sin bibliotecario asignado";
                        else
                            nombreBibliotecario=rec.getString("nombreBibliotecario")+" "+rec.getString("apellidoBibliotecario");
                        if(rec.has("folio")){
                            int status=R.drawable.reports;
                            if(rec.getString("status").equals("3")){
                                status=R.drawable.st3;
                            }else if(rec.getString("status").equals("4")){
                                status=R.drawable.st4;
                            }else if(rec.getString("status").equals("6")){
                                status=R.drawable.st6;
                            }
                            tickets.add(new Tickets(
                                    rec.getInt("folio"),
                                    rec.getString("peticion"),
                                    calificacion,
                                    rec.getString("fechaAlta"),
                                    fechaCierre,
                                    rec.getString("nombreSolicitante")+" "+rec.getString("apellidoSolicitante"),
                                    status,
                                    nombreBibliotecario
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    ReportesAdapter reportesAdapter = new ReportesAdapter(ResponsableActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstRespTickets.setAdapter(reportesAdapter);
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

                User usr= new User(ResponsableActivity.this);
                Map<String,String > params=new HashMap<String,String>();
                params.put("idDependencia",usr.getIdDependencia());
                params.put("busqueda",txtRespBusquedaTicket.getText().toString());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
