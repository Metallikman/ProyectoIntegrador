package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
import com.example.metallikman.rebuc.BibliotecarioActivity;
import com.example.metallikman.rebuc.DetallesReportesBibliotecario;
import com.example.metallikman.rebuc.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import modelos.Comentarios;
import modelos.User;
import modelos.UserForAdapter;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * @since 1.0
 * @version 1.0
 *
 * Usado para llenar el listView de usuarios de un responsable o administrador.
 */

public class UsuariosAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<UserForAdapter> items;

    public  UsuariosAdapter(Activity activity){
        this.activity = activity;
    }

    public UsuariosAdapter(Activity activity, ArrayList<UserForAdapter> items) {
        this.activity = activity;
        this.items = items;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<UserForAdapter> category) {
        for (int i =0 ; i < category.size(); i++) {
            items.add(category.get(i));
        }
    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.usuarios_item, null);
        }

        final UserForAdapter users = items.get(i);

        TextView nombreCompleto = (TextView) view.findViewById(R.id.txvUINombre);
        TextView correo = (TextView) view.findViewById(R.id.txvUICorreo);
        TextView rol = (TextView) view.findViewById(R.id.txvUIRol);
        TextView dependencia = (TextView) view.findViewById(R.id.txvUIDependencia);
        final Switch swUIStatus = (Switch) view.findViewById(R.id.swUIStatus);

        swUIStatus.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updateStatus(swUIStatus,users.getIdUsuario());
            }
        });


        nombreCompleto.setText(users.getNombreCompleto());
        correo.setText(users.getEmail());
        rol.setText(users.getRol());
        dependencia.setText(users.getDependencia());
        if(users.getStatus().equals("1")){
            swUIStatus.setChecked(true);
        }else if (users.getStatus().equals("2")){
            swUIStatus.setChecked(false);
        }

        return view;
    }

    private void updateStatus(final Switch s, final int idUsuario){
        String URL_POST=activity.getResources().getString(R.string.host)+"updateStatusUser.php";
        StringRequest sr=new StringRequest(Request.Method.POST, URL_POST, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jArray = new JSONArray(response);
                    for (int i = 0; i < jArray.length(); i++) {
                        JSONObject rec = jArray.getJSONObject(i);
                        if(rec.has("success")){

                        }else if(rec.has("error")){
                            Toast.makeText(activity,rec.getString("error"),Toast.LENGTH_SHORT).show();
                        }
                    }
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
                //User user = new User(activity);
                Map<String,String > params=new HashMap<String,String>();
                String status;
                if(s.isChecked()){
                    status="1";
                }else {
                    status="2";
                }
                params.put("status",status);
                params.put("idUsuario",String.valueOf(idUsuario));

                return params;
            }
        };
        RequestQueue rq= Volley.newRequestQueue(activity);
        rq.add(sr);
    }
}
