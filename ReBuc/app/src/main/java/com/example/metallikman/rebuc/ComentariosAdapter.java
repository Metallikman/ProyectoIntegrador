package com.example.metallikman.rebuc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 */

public class ComentariosAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Comentarios> items;

    public ComentariosAdapter(Activity activity, ArrayList<Comentarios> items) {
        this.activity = activity;
        this.items = items;
    }

    public void clear() {
        items.clear();
    }

    public void addAll(ArrayList<Comentarios> category) {
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
            view = inf.inflate(R.layout.comentarios_item, null);
        }

        Comentarios comentarios = items.get(i);

        TextView comentario = (TextView) view.findViewById(R.id.txvComComentario);
        TextView fecha = (TextView) view.findViewById(R.id.txvComHora);
        TextView usuario = (TextView) view.findViewById(R.id.txvComUsuario);


        comentario.setText(comentarios.getComentario());
        fecha.setText(comentarios.getFecha());
        usuario.setText(comentarios.getNombreCompleto());

        return view;
    }
}
