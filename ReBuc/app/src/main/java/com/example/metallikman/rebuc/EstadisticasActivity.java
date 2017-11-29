package com.example.metallikman.rebuc;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapters.ReportesAdapter;
import modelos.Tickets;
import utilidades.StringWithTags;

public class EstadisticasActivity extends AppCompatActivity {
    private ArrayList tickets = new ArrayList<Tickets>();
    private EditText txtEstadisticasFechaInicio, txtEstadisticasFechaFin;
    private Calendar myCalendar=Calendar.getInstance();
    private DatePickerDialog.OnDateSetListener dateInicio;
    private DatePickerDialog.OnDateSetListener dateFin;
    private Spinner spnEstadisticasDependencia,spnEstadisticasBibliotecario,spnEstadisticasSolicitante;
    private ListView lstEstadisticas;
    private TextView txvEstadisticasTotales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estadisticas);

        txtEstadisticasFechaInicio= (EditText) findViewById(R.id.txtEstadisticasFechaInicio);
        txtEstadisticasFechaFin= (EditText) findViewById(R.id.txtEstadisticasFechaFin);
        spnEstadisticasDependencia=(Spinner)findViewById(R.id.spnEstadisticasDependencia);
        spnEstadisticasBibliotecario=(Spinner)findViewById(R.id.spnEstadisticasBibliotecario);
        spnEstadisticasSolicitante=(Spinner)findViewById(R.id.spnEstadisticasSolicitante);
        lstEstadisticas=(ListView)findViewById(R.id.lstEstadisticas);
        txvEstadisticasTotales=(TextView)findViewById(R.id.txvEstadisticasTotales);
        getDependencias();
        getBibliotecarios();
        getSolicitantes();
        dateInicio = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txtEstadisticasFechaInicio.setText(sdf.format(myCalendar.getTime()));
            }

        };
        dateFin = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "yyyy-MM-dd HH:mm:ss";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

                txtEstadisticasFechaFin.setText(sdf.format(myCalendar.getTime()));
            }

        };

        txtEstadisticasFechaInicio.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EstadisticasActivity.this, dateInicio, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        txtEstadisticasFechaFin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EstadisticasActivity.this, dateFin, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    public void getEstadisticas(View v) {
        String URL_POST=getResources().getString(R.string.host)+"getEstadisticas.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    tickets.clear();
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    int i=0;
                    for (i = 0; i < jArray.length(); i++) {
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
                                    status,
                                    rec.getString("nombreBibliotecario")+" "+rec.getString("apellidoBibliotecario")
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }
                    txvEstadisticasTotales.setText("Total de reportes: "+i);
                    txvEstadisticasTotales.setVisibility(View.VISIBLE);
                    txtEstadisticasFechaInicio.setText("");
                    txtEstadisticasFechaFin.setText("");
                    ReportesAdapter reportesAdapter = new ReportesAdapter(EstadisticasActivity.this, tickets);
                    reportesAdapter.notifyDataSetChanged();
                    lstEstadisticas.setAdapter(reportesAdapter);
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
                StringWithTags swtBiblio = (StringWithTags) spnEstadisticasBibliotecario.getItemAtPosition(spnEstadisticasBibliotecario.getSelectedItemPosition());
                StringWithTags swtDependencia = (StringWithTags) spnEstadisticasDependencia.getItemAtPosition(spnEstadisticasDependencia.getSelectedItemPosition());
                StringWithTags swtSolicitante = (StringWithTags) spnEstadisticasSolicitante.getItemAtPosition(spnEstadisticasSolicitante.getSelectedItemPosition());

                Map<String,String > params=new HashMap<String,String>();

                String fechaInicio=txtEstadisticasFechaInicio.getText().toString();
                String fechaFin=txtEstadisticasFechaFin.getText().toString();
                String bibliotecario = swtBiblio.tag.toString();
                String dependencia = swtDependencia.tag.toString();
                String solicitante = swtSolicitante.tag.toString();

                if(!dependencia.equals("null"))
                    params.put("dependencia",dependencia);
                if(!bibliotecario.equals("null"))
                    params.put("bibliotecario",bibliotecario);
                if(!solicitante.equals("null"))
                    params.put("solicitante",solicitante);
                if(!fechaInicio.equals(""))
                    params.put("fechaInicio",fechaInicio );
                if(!fechaFin.equals("")){
                    params.put("fechaFin", fechaFin);}
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    private void getDependencias() {
        String URL_POST=getResources().getString(R.string.host)+"getDependencias.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    List<StringWithTags> dependencias = new ArrayList<StringWithTags>();
                    dependencias.add(new StringWithTags("Seleccione una opción", "null"));
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        dependencias.add(new StringWithTags(rec.getString("dependencia"), rec.getString("de_id")));
                    }

                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (EstadisticasActivity.this, android.R.layout.simple_spinner_item, dependencias);
                    spnEstadisticasDependencia.setAdapter(adap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    private void getBibliotecarios() {
        String URL_POST=getResources().getString(R.string.host)+"getUsers.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    List<StringWithTags> bibliotecarios = new ArrayList<StringWithTags>();
                    bibliotecarios.add(new StringWithTags("Seleccione una opción","null"));
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        bibliotecarios.add(new StringWithTags(rec.getString("nombre") + " " + rec.getString("apellido"), rec.getString("idUsuario")));
                    }
                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (EstadisticasActivity.this, android.R.layout.simple_spinner_item, bibliotecarios);
                    spnEstadisticasBibliotecario.setAdapter(adap);
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

                Map<String,String > params=new HashMap<String,String>();
                params.put("getBibliotecarios","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    private void getSolicitantes() {
        String URL_POST=getResources().getString(R.string.host)+"getUsers.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    List<StringWithTags> solicitantes = new ArrayList<StringWithTags>();
                    solicitantes.add(new StringWithTags("Seleccione una opción","null"));
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        solicitantes.add(new StringWithTags(rec.getString("nombre")  + " " +  rec.getString("apellido"), rec.getString("idUsuario")));
                    }
                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (EstadisticasActivity.this, android.R.layout.simple_spinner_item, solicitantes);
                    spnEstadisticasSolicitante.setAdapter(adap);
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

                Map<String,String > params=new HashMap<String,String>();
                params.put("getSolicitantes","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }
}
