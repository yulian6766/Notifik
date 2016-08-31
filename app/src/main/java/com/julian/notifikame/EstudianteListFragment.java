package com.julian.notifikame;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by JdRod on 8/26/2016.
 */
public class EstudianteListFragment extends ListFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayAdapter<Usuario> adapter = new EstudianteAdapter(getActivity(), getStudents());
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        //Metodo que permitira crear el Dialog para poder borrar al estudiante
        //super.onListItemClick(l, v, position, id);
    }

    public ArrayList<Usuario> getStudents(){
        return null;
        //Codigo que develve los estudiantes de la base de datos

    }
}
