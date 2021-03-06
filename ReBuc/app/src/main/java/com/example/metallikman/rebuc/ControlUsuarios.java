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
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapters.ReportesAdapter;
import adapters.UsuariosAdapter;
import modelos.Tickets;
import modelos.User;
import modelos.UserForAdapter;
import utilidades.StringWithTags;

public class ControlUsuarios extends AppCompatActivity {

    private ArrayList users = new ArrayList<UserForAdapter>();
    private TextView txtCUBusqueda;
    private ListView lstCUListaUsuarios;
    private User user;
    boolean isAdmin=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_usuarios);

        setTitle("Control de usuarios");
        txtCUBusqueda=(EditText)findViewById(R.id.txtCUBusqueda);
        lstCUListaUsuarios=(ListView)findViewById(R.id.lstCUListaUsuarios);

        user = new User(ControlUsuarios.this);
        getAllUsers();
        //usado para ver si es admin o no.
        isAdmin=getIntent().getBooleanExtra("admin",false);

        txtCUBusqueda.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getUsuariosBusqueda();
            }
        });

        lstCUListaUsuarios.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                UserForAdapter usuarioAMostar = (UserForAdapter) parent.getItemAtPosition(position);

                Intent intent = new Intent(getBaseContext(), DetallesUsuarioActivity.class);
                intent.putExtra("dependencia", usuarioAMostar.getDependencia());
                intent.putExtra("idDependencia",usuarioAMostar.getIdDependencia());
                intent.putExtra("email", usuarioAMostar.getEmail());
                intent.putExtra("nombre", usuarioAMostar.getNombre());
                intent.putExtra("apellido", usuarioAMostar.getApellido());
                intent.putExtra("status", usuarioAMostar.getStatus());
                intent.putExtra("rol",usuarioAMostar.getRol());
                intent.putExtra("idUsuario",String.valueOf(usuarioAMostar.getIdUsuario()));
                intent.putExtra("isAdmin",true);
                startActivity(intent);
            }
        });
    }

    /**
     * Seleccion de items del menu
     * <p>
     * responde segun al elemento clickeado en el menu de opciones
     *
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.opcionVerUsuariosResponsable:
                getAllUsers();
                return true;
            case R.id.opcionLogoutResponsable:
                new User(ControlUsuarios.this).remove();
                intent = new Intent(ControlUsuarios.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcionVerTicketsResponsable:
                intent = new Intent(ControlUsuarios.this, ResponsableActivity.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityIfNeeded(intent, 0);
                finish();
                return true;
            case R.id.opcionAgregarBiblioResponsable:
                intent = new Intent(ControlUsuarios.this, AgregarBibliotecarioActivity.class);
                startActivity(intent);
                return true;
            case R.id.opcionLogoutAdmin:
                new User(ControlUsuarios.this).remove();
                intent = new Intent(ControlUsuarios.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            case R.id.opcionControlUsrAdmin:
                getAllUsers();
                return true;
            case R.id.opcionEstadisticasAdmin:
                /*intent = new Intent(AdminActivity.this, EstadisticasActiviy.class);
                startActivity(intent);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivityIfNeeded(intent, 0);*/

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Seleccion de items del menu
     * <p>
     * responde segun al elemento clickeado en el menu de opciones
     *
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(isAdmin)
            inflater.inflate(R.menu.menu_general_admin, menu);
        else
            inflater.inflate(R.menu.menu_general_responsable, menu);
        return true;
    }

    /**
     * Seleccion de items del menu
     * <p>
     * responde segun al elemento clickeado en el menu de opciones
     *
     */
    @Override
    public void onResume() {
        super.onResume();
        getAllUsers();
    }

    /**
     * Obtiene los usuarios según el EditText de busqueda
     * <p>
     * Obtiene todos los usuarios de cierta dependencia.
     *
     */
    private void getUsuariosBusqueda(){
        String URL_POST=getResources().getString(R.string.host)+"getUsers.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    users.clear();
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("correo")){
                            users.add(new UserForAdapter(
                                    rec.getString("correo"),
                                    rec.getString("rol"),
                                    rec.getString("nombreRol"),
                                    rec.getString("nombre"),
                                    rec.getString("apellido"),
                                    rec.getString("idDependencia"),
                                    rec.getString("dependencia"),
                                    rec.getString("estado"),
                                    rec.getInt("idUsuario")
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                    UsuariosAdapter usuariosAdapter = new UsuariosAdapter(ControlUsuarios.this,users);
                    usuariosAdapter.notifyDataSetChanged();
                    lstCUListaUsuarios.setAdapter(usuariosAdapter);
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
                params.put("busqueda", txtCUBusqueda.getText().toString());
                params.put("idDependencia",user.getIdDependencia());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }

    /**
     * Recupera de todos los usuarios
     * <p>
     * Recupera de todos los usuarios segun su id de dependencia
     *
     */

    private void getAllUsers(){
        String URL_POST=getResources().getString(R.string.host)+"getUsers.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    users.clear();
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("correo")){
                            users.add(new UserForAdapter(
                                    rec.getString("correo"),
                                    rec.getString("rol"),
                                    rec.getString("nombreRol"),
                                    rec.getString("nombre"),
                                    rec.getString("apellido"),
                                    rec.getString("idDependencia"),
                                    rec.getString("dependencia"),
                                    rec.getString("estado"),
                                    rec.getInt("idUsuario")
                            ));
                        }else if(rec.has("error")){
                            Toast.makeText(getApplication(),rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }
                    UsuariosAdapter usuariosAdapter = new UsuariosAdapter(ControlUsuarios.this,users);
                    usuariosAdapter.notifyDataSetChanged();
                    lstCUListaUsuarios.setAdapter(usuariosAdapter);
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
                params.put("idDependencia", user.getIdDependencia());
                if(isAdmin)
                    params.put("admin","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(sr);
    }


}
