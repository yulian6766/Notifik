package com.julian.notifikame;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by JdRod on 8/31/2016.
 */
public class EstudianteAdapter extends ArrayAdapter {
    private Activity activity;
    public EstudianteAdapter(Activity activity, ArrayList<Usuario> students) {
        super(activity,0, students);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.item_lista_estudiantes, null);

        Usuario estudiante = (Usuario) getItem(position);

        ((TextView)convertView.findViewById(R.id.studentName)).setText(estudiante.getNombre());

        return convertView;
    }
}
