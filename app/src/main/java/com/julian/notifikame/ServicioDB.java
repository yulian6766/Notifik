package com.julian.notifikame;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Julian on 30/08/2016.
 */
public class ServicioDB extends IntentService{
    // Variables de la notificacion
    static String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager nm;
    Notification notif;
    private Conexion con=new Conexion();
    private String data;
    private ArrayList<Notificacion> notis = new ArrayList<>();
    private DBDataConverter dbConverter=new DBDataConverter();

    //Defino los iconos de la notificacion en la barra de notificacion
    int icono_v = R.drawable.konradlogo;
    int icono_r = R.drawable.konradlogo;

    public ServicioDB() {
        super("Hola Mundo");
    }


    @Override
    public void onStart(Intent intent, int startId) {

            data=new ConsultarNoti().execute();





        if(!(data.equalsIgnoreCase(""))) {
            notis = dbConverter.filtrarDatosNotificacion(data);
            DataSingleton.getInstance().setArrayNotificaciones(notis);
            for (int i=0;i<notis.size();i++){
                // Inicio el servicio de notificaciones accediendo al servicio
                nm = (NotificationManager) getSystemService(ns);

                // Realizo una notificacion por medio de un metodo hecho por mi
                notificacion(icono_r, "NotifiK", notis.get(i).getHeader(), notis.get(i).getDescription());

                // Lanzo la notificacion creada en el paso anterior
                nm.notify(i+1, notif);
            }

        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {    }

    //AsyncTask para insertar Personas
    class ConsultarNoti extends AsyncTask<String,String,String> {

        private Activity context;
        private Conexion con= new Conexion();
        private String data;

        ConsultarNoti(){

        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            data=con.conectLoadNoti();
            return data;
        }

        @Override
        protected void onPostExecute(String datos) {
            super.onPostExecute(datos);
            //...

        }
    }

    public void notificacion(int icon, CharSequence textoEstado, CharSequence titulo, CharSequence texto) {
        // Capturo la hora del evento
        long hora = System.currentTimeMillis();

        // Definimos la accion de la pulsacion sobre la notificacion (esto es opcional)
        Context context = getApplicationContext();
        Intent notificationIntent = new Intent(this, ServicioDB.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        // Defino la notificacion, icono, texto y hora
        notif = new Notification(icon, textoEstado, hora);
        notif.setLatestEventInfo(context, titulo, texto, contentIntent);

        //Defino que la notificacion sea permamente
        //notif.flags = Notification.FLAG_ONGOING_EVENT;
    }
}
