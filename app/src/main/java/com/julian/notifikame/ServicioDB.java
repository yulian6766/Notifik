package com.julian.notifikame;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.service.notification.NotificationListenerService;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Julian on 30/08/2016.
 */
@TargetApi(Build.VERSION_CODES.CUPCAKE)
public class ServicioDB extends IntentService implements AsyncResponse{
    // Variables de la notificacion
    static String ns = Context.NOTIFICATION_SERVICE;
    NotificationManager nm;
    Notification notif;
    private Conexion con=new Conexion();
    private String data;
    private ArrayList<Notificacion> notis = new ArrayList<>();
    private DBDataConverter dbConverter=new DBDataConverter();
    private ConsultarNoti asyncTask =new ConsultarNoti();

    //Defino los iconos de la notificacion en la barra de notificacion
    int icono_v = R.drawable.konradlogo;
    int icono_r = R.drawable.konradlogo;

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public ServicioDB() {
        super("Hola Mundo");
    }


    @Override
    public void onStart(Intent intent, int startId) {

        //this to set delegate/listener back to this class
        asyncTask.delegate=this;
        //execute the async task
         asyncTask.execute();




        if(data==null){}
        else {
            if (!(data.equalsIgnoreCase(""))) {
                for (int i = 0; i < notis.size(); i++) {
                    // Inicio el servicio de notificaciones accediendo al servicio
                    nm = (NotificationManager) getSystemService(ns);

                    // Realizo una notificacion por medio de un metodo hecho por mi
                    notificacion(icono_r, "NotifiK", notis.get(i).getHeader(), notis.get(i).getDescription());

                    // Lanzo la notificacion creada en el paso anterior
                    nm.notify(i + 1, notif);
                }

            }
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {    }


    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        data=output;
        notis = dbConverter.filtrarDatosNotificacion(data);
        DataSingleton.getInstance().setArrayNotificaciones(notis);
        NotificacionListFragment nl=new NotificacionListFragment();
        nl.loadNotificaciones();
        nl.onPause();
        nl.onResume();
        asyncTask.cancel(true);
    }

    //AsyncTask para insertar Personas
    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    class ConsultarNoti extends AsyncTask<String,String,String> {
        public AsyncResponse delegate = null;

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

        @TargetApi(Build.VERSION_CODES.CUPCAKE)
        @Override
        protected void onPostExecute(String datos) {
            delegate.processFinish(datos);

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
