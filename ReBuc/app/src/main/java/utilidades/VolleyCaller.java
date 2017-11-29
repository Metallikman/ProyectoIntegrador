package utilidades;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;



/**
 * Created by Ubaldo Torres Juárez on 28/11/2017.
 */

public class VolleyCaller {

    private String URL_POST="";
    private String respuesta="";
    private Map<String,String> params;
    private Context contextAplication;


    /**
     * Realiza llamadas post segun donde se apunte.
     * @param URL_POST URL donde se va a apuntar
     * @param params Mapa de parametros que se van a enviar <String,String>
     * @param contextAplication Aplicacion de donde se habla
     */
    public VolleyCaller(String URL_POST, Map<String, String> params, Context contextAplication) {
        this.URL_POST = URL_POST;
        this.params = params;
        this.contextAplication = contextAplication;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void postCall(){

        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                respuesta=response;
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               respuesta=error.toString();
            }
        }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(contextAplication);
        rq.add(sr);

    }


}
