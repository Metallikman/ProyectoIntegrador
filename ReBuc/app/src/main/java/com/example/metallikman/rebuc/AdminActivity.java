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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
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

public class AdminActivity extends AppCompatActivity {

    private ArrayList tickets = new ArrayList<Tickets>();
    private ListView lstAdminTickets;
    private TextView txtAdminBuscarTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        setTitle("Bienvenido Administrador");

        lstAdminTickets = (ListView) findViewById(R.id.lstAdminTickets);
        txtAdminBuscarTicket = (EditText) findViewById(R.id.txtAdminBuscarTicket);
        getReportes();

        txtAdminBuscarTicket.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getReportesBusqueda();
            }
        });
    }

    /**
     * Usado al momento de iniciar el activity y carga el menú de opciones para el administrador
     * @param menu
     * @return
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_admin, menu);
        return true;
    }

    /**
     * Es llamado al dar click en algun elemento del menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcionLogoutAdmin:
                new User(AdminActivity.this).remove();
                intent = new Intent(AdminActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcionControlUsrAdmin:
                intent = new Intent(AdminActivity.this, ControlUsuarios.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("admin", true);
                startActivityIfNeeded(intent, 0);

                return true;
            case R.id.opcionEstadisticasAdmin:
                intent = new Intent(AdminActivity.this, EstadisticasActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityIfNeeded(intent, 0);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     *  Llamado cuando la actividad recupera el foco
     */
    @Override
    public void onResume() {
        super.onResume();
        getReportes();
    }

    /**
     *  Obtiene de la API la lista de reportes
     */
    private void getReportes() {

        String URL_POST = getResources().getString(R.string.host) + "getTickets.php";

        StringRequest sr = new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

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
                        if (rec.getString("calificacion") == "null") {
                            calificacion = "Sin calificar";
                        } else {
                            calificacion = rec.getString("calificacion");
                        }
                        if (rec.getString("fechaCierre") == "null") {
                            fechaCierre = "Sin fecha de cierre aún";
                        } else {
                            fechaCierre = rec.getString("fechaCierre");
                        }
                        if (rec.getString("nombreBibliotecario") == "null")
                            nombreBibliotecario = "Sin bibliotecario asignado";
                        else
                            nombreBibliotecario = rec.getString("nombreBibliotecario") + " " + rec.getString("apellidoBibliotecario");
                        if (rec.has("folio")) {
                            int status = R.drawable.reports;
                            if (rec.getString("status").equals("3")) {
                                status = R.drawable.st3;
                            } else if (rec.getString("status").equals("4")) {
                                status = R.drawable.st4;
                            } else if (rec.getString("status").equals("6")) {
                                status = R.drawable.st6;
                            }
                            tickets.add(new Tickets(
                                    rec.getInt("folio"),
                                    rec.getString("peticion"),
                                    calificacion,
                                    rec.getString("fechaAlta"),
                                    fechaCierre,
                                    rec.getString("nombreSolicitante") + " " + rec.getString("apellidoSolicitante"),
                                    status,
                                    nombreBibliotecario
                            ));
                        } else if (rec.has("error")) {
                            Toast.makeText(getApplication(), rec.getString("error"), Toast.LENGTH_SHORT).show();
                        }
                    }

                    ReportesAdapter reportesAdapter = new ReportesAdapter(AdminActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstAdminTickets.setAdapter(reportesAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplication(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<String, String>();
                params.put("getAllReports", "true");
                return params;
            }
        };
        RequestQueue rq = Volley.newRequestQueue(this);
        rq.add(sr);
    }


    /**
     * Obtiene de la API la lista de reportes segun el asunto por el que se busca
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

                    ReportesAdapter reportesAdapter = new ReportesAdapter(AdminActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstAdminTickets.setAdapter(reportesAdapter);
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
                params.put("busqueda",txtAdminBuscarTicket.getText().toString());
                params.put("getAllReports", "true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
