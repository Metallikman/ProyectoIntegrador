package customsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.metallikman.rebuc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ubaldo Torres Juárez on 12/11/2017.
 */

public class CustomDialogOpcionesTicketBiblio extends Dialog implements View.OnClickListener {
    private Activity c;
    private Dialog d;
    private Button yes;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String idTicket;


    /**
     * Constructor del customDialog mostrado en {@link com.example.metallikman.rebuc.DetallesUsuarioActivity}
     * <p>
     *  @param context Recibe el contexto de la actividad
     *                 donde se llama el customDialog.
     *  @param idTicket Recibe el id del ticket a cambiar el status.
     *
     */
    public CustomDialogOpcionesTicketBiblio(Activity context, String idTicket) {
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
        setContentView(R.layout.dialog_opciones_ticket_bibliotecario);
        yes = (Button) findViewById(R.id.cmdDCcalificar);
        radioGroup = (RadioGroup) findViewById(R.id.rdgDCCalificacion);
        yes.setOnClickListener(this);

    }

    /**
     * Controla el click del boton de modificar el status
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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
        }
        dismiss();
    }

    /**
     * Hace la llamada a la API para modificar el status
     * @param status Recibe '1' para activo o '2' para Cerrado.
     *
     */
    private void doStatusUpdate(final int status){

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);


        String URL_POST=c.getResources().getString(R.string.host)+"/pi/api/closeTicket.php";
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
                params.put("idTicket",idTicket);
                params.put("status",String.valueOf(status));
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(c);
        rq.add(sr);
    }
}
