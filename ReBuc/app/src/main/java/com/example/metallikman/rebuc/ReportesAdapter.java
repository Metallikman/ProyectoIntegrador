package com.example.metallikman.rebuc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Ubaldo Torres Juárez on 27/10/2017.
 * Adapter for Listview in UniversitarioActivity and BibliotecarioActivity
 */

public class ReportesAdapter extends BaseAdapter {
    protected Activity activity;
    protected ArrayList<Tickets> items;

    public ReportesAdapter (Activity activity, ArrayList<Tickets> items) {
        this.activity = activity;
        this.items = items;
    }

    public void clear() {
        items.clear();
        //this.notifyDataSetChanged();
    }

    public void addAll(ArrayList<Tickets> category) {
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
        //clear();

        if (view == null) {
            LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inf.inflate(R.layout.reportes_item, null);
        }

        Tickets tickets = items.get(i);

        TextView asunto = (TextView) view.findViewById(R.id.txvComUsuario);
        TextView fechaAlta = (TextView) view.findViewById(R.id.txvComComentario);
        TextView fechaCierre = (TextView) view.findViewById(R.id.txvFechaCierre);
        TextView txvUsuario = (TextView) view.findViewById(R.id.txvUsuario);
        ImageView imagen = (ImageView) view.findViewById(R.id.imgRepStatus);

        asunto.setText(tickets.getSolicitud());
        fechaAlta.setText(tickets.getFechaAlta());
        if(tickets.getFechaCierre()=="null"){
            fechaCierre.setText("Sin fecha de cierre aún");

        }else{
            fechaCierre.setText(tickets.getFechaCierre());
        }
        txvUsuario.setText(tickets.getSolictante());
        imagen.setImageResource(tickets.getStatus());

        return view;
    }
}
