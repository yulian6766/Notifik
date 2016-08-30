package com.julian.notifikame;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Julian on 29/08/2016.
 */
public class GrupoAdapter extends ArrayAdapter<Grupo> {
    private Activity activity;

    public GrupoAdapter(Activity activity, ArrayList<Grupo> grupos) {
        super(activity, 0, grupos);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater()
                .inflate(R.layout.grupo_list_item, null);

        Grupo grupo = getItem(position);

        ((TextView)convertView.findViewById(R.id.lblgrupo))
                .setText("Grupo: "+(position+1));
        ((TextView)convertView.findViewById(R.id.lblnombregrupo))
                .setText(grupo.getNomGrupo());


        return convertView;
    }
}
