package com.julian.notifikame;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Julian on 03/10/2016.
 */

public class NotificacionListFragment extends ListFragment {
    private ArrayList<Notificacion> notificaciones;

    @Override
    public void onResume() {
        super.onResume();

        ArrayAdapter<Notificacion> adapter = new NotificacionAdapter(getActivity(), notificaciones);
        setListAdapter(adapter);

        setHasOptionsMenu(true);
        ((NotificacionAdapter)getListAdapter()).notifyDataSetChanged();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificaciones();
        if(notificaciones==null){
            notificaciones = new ArrayList<Notificacion>();
            notificaciones.add(new Notificacion());
        }
        ArrayAdapter<Notificacion> adapter = new NotificacionAdapter(getActivity(), notificaciones);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

       /* Notificacion pregunta = ((NotificacionAdapter)getListAdapter()).getItem(position);

        Intent i = new Intent(getActivity(), PreguntaActivity.class);
        //posicion del clic
        i.putExtra("PreguntaIndex", position);
        startActivity(i);*/

    }

    private void createNotificaciones(){
        if (notificaciones == null)
            notificaciones = DataSingleton.getInstance().getArrayNotificaciones();
    }
}
