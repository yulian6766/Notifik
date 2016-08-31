package com.julian.notifikame;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Julian on 29/08/2016.
 */
public class GrupoListFragment extends ListFragment {

    private ArrayList<Grupo> grupos;

    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter<Grupo> adapter = new GrupoAdapter(getActivity(), grupos);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
        ((GrupoAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataSingleton.getInstance().loadGrupos();
        createGrupos();
        ArrayAdapter<Grupo> adapter = new GrupoAdapter(getActivity(), grupos);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        Grupo grupo = ((GrupoAdapter)getListAdapter()).getItem(position);

        //Intent i = new Intent(getActivity(), PreguntaActivity.class);
        //posicion del clic
       // i.putExtra("PreguntaIndex", position);
        //startActivity(i);

    }

    private void createGrupos(){
        if (grupos == null)
            grupos = DataSingleton.getInstance().obtenerGrupos();
    }

}
