package customsDialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
 * Created by Ubaldo Torres Juárez on 30/10/2017.
 * @version     1.0
 * @since       1.0
 * Permite la creacion de un custom dialog para cerrar y calificar el ticket
 */

public class CustomDialogCalificacion extends Dialog implements android.view.View.OnClickListener{

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
     *  @param idTicket Recibe el id del ticket a cerrar y calificar.
     *
     */
    public CustomDialogCalificacion(Activity context, String idTicket) {
        super(context);
        // TODO Auto-generated constructor stub
        this.c = context;
        this.idTicket=idTicket;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_calificacion);
        yes = (Button) findViewById(R.id.cmdDCcalificar);
        yes = (Button) findViewById(R.id.cmdDCcalificar);
        radioGroup = (RadioGroup) findViewById(R.id.rdgDCCalificacion);
        yes.setOnClickListener(this);

    }

    /**
     * Controla el click del boton de cerrar y calificar
     *
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cmdDCcalificar:
                doCalificationUpdate();
                c.finish();
                break;
            default:
                break;
        }
        dismiss();
    }

    /**
     * Hace la llamada a la API para cerrar y calificar el ticket.
     *
     */
    private void doCalificationUpdate(){

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);


        String URL_POST=c.getResources().getString(R.string.host)+"closeTicket.php";
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
                params.put("calificacion",radioButton.getText().toString());
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(c);
        rq.add(sr);
    }

}
