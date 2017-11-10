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
 * Created by Ubaldo Torres Juárez on 30/10/2017.
 */

public class CustomDialogCalificacion extends Dialog implements android.view.View.OnClickListener{

    private Activity c;
    private Dialog d;
    private Button yes;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private String idTicket;


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

    private void doCalificationUpdate(){

        int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selectedId);


        String URL_POST="http://dogebox.ddns.net/pi/api/closeTicket.php";
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
