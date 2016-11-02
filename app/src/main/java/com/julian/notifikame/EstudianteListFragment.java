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
    private ArrayList<Usuario> estudiantes;

    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter<Usuario> adapter = new EstudianteAdapter(getActivity(), estudiantes);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
        ((EstudianteAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //DataSingleton.getInstance().loadEstudiantesGrupo();
        createGrupos();
        if(estudiantes==null){
            estudiantes = new ArrayList<Usuario>();
            estudiantes.add(new Usuario());
        }
        ArrayAdapter<Grupo> adapter = new EstudianteAdapter(getActivity(), estudiantes);
        setListAdapter(adapter);
    }

    /*@Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Grupo grupo = ((GrupoAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), GrupoEstudiantesActivity.class);
        //posicion del clic
        i.putExtra("PreguntaIndex", position);
        startActivity(i);

    }*/

    private void createGrupos(){
        if (estudiantes == null)
            estudiantes = DataSingleton.getInstance().obtenerGEstudiantes();
    }
}
