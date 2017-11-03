package com.example.metallikman.rebuc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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

public class UniversitarioActivity extends AppCompatActivity {

    private ArrayList tickets = new ArrayList<Tickets>();
    private ListView lstReportes;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universitario);
        user= new User(UniversitarioActivity.this);

        setTitle("Bienvenido "+user.getNombreCompleto());

        getReportes();
        lstReportes=(ListView)findViewById(R.id.lstReportes);
        lstReportes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Tickets ticketAMostrar = (Tickets) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), DetallesReportesUniversitario.class);
                intent.putExtra("asunto", ticketAMostrar.getSolicitud());
                intent.putExtra("fechaAlta", ticketAMostrar.getFechaAlta());
                intent.putExtra("fechaCierre", ticketAMostrar.getFechaCierre());
                intent.putExtra("usuario", ticketAMostrar.getSolictante());
                intent.putExtra("idTicket",String.valueOf(ticketAMostrar.getFolio()));
                startActivity(intent);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_general_universitario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcionLogoutUniversitario:
                logout();
                return true;
            case R.id.opcionLevantarTicketUniversitario:
                Intent intent = new Intent(UniversitarioActivity.this, LevantarTicketActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        getReportes();
        super.onResume();

    }

    public void logout(){
        new User(UniversitarioActivity.this).remove();
        Intent intent = new Intent(UniversitarioActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    public void iniciarLevantarTicket(View v){
        Intent intent = new Intent(UniversitarioActivity.this, LevantarTicketActivity.class);
        startActivity(intent);
    }

    private void getReportes(){
        String URL_POST="http://dogebox.ddns.net/pi/api/getTickets.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        /*String calificacion;
                        String fechaCierre;
                        if(rec.getString("calificacion")=="null"){
                            calificacion="Sin calificación";
                        }else{
                            calificacion=rec.getString("calificacion");
                        }
                        if(rec.getString("fechaCierre")=="null"){
                            fechaCierre="Sin fecha de cierre aún";
                        }else{
                            fechaCierre=rec.getString("fechaCierre");
                        }*/
                        if(rec.has("folio")){
                            tickets.add(new Tickets(
                                    rec.getInt("folio"),
                                    rec.getString("peticion"),
                                    rec.getString("calificacion"),
                                    rec.getString("fechaAlta"),
                                    rec.getString("fechaCierre"),
                                    rec.getString("nombreSolicitante")+" "+rec.getString("apellidoSolicitante"),
                                    R.drawable.st2
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    ReportesAdapter reportesAdapter = new ReportesAdapter(UniversitarioActivity.this, tickets);
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
                //User user= new User(UniversitarioActivity.this);
                Map<String,String > params=new HashMap<String,String>();
                params.put("idUsuario",user.getIdUser());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
