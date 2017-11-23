package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
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

public class BibliotecarioActivity extends AppCompatActivity {
    private ArrayList tickets = new ArrayList<Tickets>();
    private ListView lstReportes;
    private Switch swStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bibliotecario);
        setTitle("Bienvenido Bibliotecario");
        lstReportes=(ListView)findViewById(R.id.lstReportesBiblio);
        swStatus = (Switch)findViewById(R.id.swUIStatus);
        getReportes();

        lstReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                intent.putExtra("status",String.valueOf(ticketAMostrar.getStatus()));
                startActivity(intent);
            }
        });
    }

    /**
     *
     * <p>
     * Crea el menu de opciones del bibliotecario definido en
     * menus/menu_general_bibliotecario
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_bibliotecario, menu);
        return true;
    }

    /**
     * Seleccion de items del menu
     * <p>
     * responde segun al elemento clickeado en el menu de opciones
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        Intent intent;
        switch (item.getItemId()) {
            case R.id.opcionLogoutBibliotecario:
                new User(BibliotecarioActivity.this).remove();
                intent = new Intent(BibliotecarioActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcionVerTicketsBibliotecario:
                getReportes();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * OnResume para actualizar los reportes
     */
    @Override
    public void onResume() {
        super.onResume();
        getReportes();
    }

    /**
     * Cerrar sesión
     * <p>
     * Permnite el cierre de sesión de la aplicacion
     *
     */
    public void logout(View v){
        new User(BibliotecarioActivity.this).remove();
        Intent intent = new Intent(BibliotecarioActivity.this, LoginActivity.class);
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
        String URL_POST=getResources().getString(R.string.host)+"/pi/api/getTickets.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    tickets.clear();
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);

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
                        if(rec.has("folio")){
                            tickets.add(new Tickets(
                                    rec.getInt("folio"),
                                    rec.getString("peticion"),
                                    rec.getString("calificacion"),
                                    rec.getString("fechaAlta"),
                                    rec.getString("fechaCierre"),
                                    rec.getString("nombreSolicitante")+" "+rec.getString("apellidoSolicitante"),
                                    status
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    ReportesAdapter reportesAdapter = new ReportesAdapter(BibliotecarioActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstReportes.setAdapter(reportesAdapter);
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
                params.put("getAllReports", "true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
