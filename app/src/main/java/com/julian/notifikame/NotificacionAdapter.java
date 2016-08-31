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
public class NotificacionAdapter extends ArrayAdapter {
    private Activity activity;
    public NotificacionAdapter(Activity activity, ArrayList<Notificacion> notificaciones) {
        super(activity,0, notificaciones);
        this.activity = activity;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = activity.getLayoutInflater().inflate(R.layout.item_lista_notificacion_estudiante, null);

        Notificacion notificacion = (Notificacion)getItem(position);

        ((TextView)convertView.findViewById(R.id.notification_header)).setText(notificacion.getHeader());
        ((TextView)convertView.findViewById(R.id.notification_description)).setText(notificacion.getDescription());
        ((TextView)convertView.findViewById(R.id.class_notification_header)).setText(notificacion.getGroup());
        return convertView;
    }
}
