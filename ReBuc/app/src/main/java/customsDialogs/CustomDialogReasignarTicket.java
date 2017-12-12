package customsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.metallikman.rebuc.R;
import com.example.metallikman.rebuc.RegistroActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import modelos.User;
import utilidades.StringWithTags;

/**
 * Created by Ubaldo Torres Juárez on 28/11/2017.
 */

public class CustomDialogReasignarTicket extends Dialog implements View.OnClickListener {
    private Activity c;
    private Dialog d;
    private Button yes;
    private RadioGroup radioGroup;
    private Spinner spinnerUsuario;
    private String idTicket;


    /**
     * Constructor del customDialog mostrado en {@link com.example.metallikman.rebuc.DetallesUsuarioActivity}
     * <p>
     *  @param context Recibe el contexto de la actividad
     *                 donde se llama el customDialog.
     *  @param idTicket Recibe el id del ticket a cambiar el status.
     *
     */
    public CustomDialogReasignarTicket(Activity context, String idTicket) {
        super(context);
        // TODO Auto-generated constructor stub
        this.c = context;
        this.idTicket=idTicket;
    }

    /**
     * Constructor del customDialog mostrado en {@link com.example.metallikman.rebuc.DetallesReportesBibliotecario}
     * <p>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_reasignar_ticket);
        yes = (Button) findViewById(R.id.cmdReasignarTicket);
        spinnerUsuario = (Spinner) findViewById(R.id.spnReasignarUsuarios);
        yes.setOnClickListener(this);
        getUsers();

    }

    /**
     * Controla el click del boton de modificar el status
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cmdReasignarTicket:
                doBibliotecarioUpdate();
                c.finish();
                break;
            default:
                break;
        }
        /*switch (v.getId()) {
            case R.id.rdbOTBEnProceso:
                doStatusUpdate(1);
                c.finish();
                break;
            case R.id.rdbOTBCerrado:
                doStatusUpdate(2);
                c.finish();
                break;
            default:
                break;
        }*/
        dismiss();
    }

    /**
     * Hace la llamada a la API para modificar el encargado
     *
     *
     */
    private void doBibliotecarioUpdate(){

        //int selectedId = radioGroup.getCheckedRadioButtonId();
        //radioButton = (RadioButton) findViewById(selectedId);


        String URL_POST=c.getResources().getString(R.string.host)+"updateTicketUser.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    //Tickets tickets;
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){
                            Toast.makeText(c,rec.getString("success"),Toast.LENGTH_SHORT).show();
                        }else if(rec.has("error")){
                            Toast.makeText(c,rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {

                Map<String,String > params=new HashMap<String,String>();
                StringWithTags s = (StringWithTags) spinnerUsuario.getItemAtPosition(spinnerUsuario.getSelectedItemPosition());
                String idUsuario = s.tag.toString();
                params.put("idTicket",idTicket);
                params.put("idUser",idUsuario);
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(c);
        rq.add(sr);
    }

    /**
     * Obitene la lista de usuarios bibliotecarios para poder seleccionar
     * el bibliotecario encargado de un ticket.
     */
    private void getUsers(){

        String URL_POST=c.getResources().getString(R.string.host)+"getUsers.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    List<StringWithTags> usuarios = new ArrayList<StringWithTags>();
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        //dependencias.add("" + rec.getString("dependencia"));
                        usuarios.add(new StringWithTags(rec.getString("nombreCompleto"), rec.getString("idUsuario")));
                    }

                    ArrayAdapter<StringWithTags> adap = new ArrayAdapter<StringWithTags> (c, android.R.layout.simple_spinner_item, usuarios);
                    spinnerUsuario.setAdapter(adap);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(c,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                User user = new User(c);
                Map<String,String > params=new HashMap<String,String>();
                params.put("idDependencia",user.getIdDependencia());
                params.put("isSpinner","true");
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(c);
        rq.add(sr);
    }
}
