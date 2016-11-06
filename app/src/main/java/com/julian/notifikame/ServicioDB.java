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
import java.util.Timer;
import java.util.TimerTask;

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

    static boolean activeActivity=false;
    private ArrayList<Notificacion> notis = new ArrayList<>();
    private ArrayList<Notificacion> notisLoaded = new ArrayList<>();
    private DBDataConverter dbConverter=new DBDataConverter();
    private final String LOG_TAG = ServicioDB.class.getSimpleName();
    private Timer time= new Timer();

    //Defino los iconos de la notificacion en la barra de notificacion
    int icono_v = R.drawable.konradlogo;
    int icono_r = R.drawable.konradlogo;

    public static void setActiveActivity(boolean activeActivity) {
        ServicioDB.activeActivity = activeActivity;
    }

    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    public ServicioDB() {
        super("Noti Services");
    }

    @Override
    public void onDestroy() {
        stopSelf();
        time.cancel();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(LOG_TAG,"Notifik Service Init");
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Log.i(LOG_TAG,"Notifik Service Query Thread Init");

                ConsultarNoti asyncTask =new ConsultarNoti();
                //this to set delegate/listener back to this class
                asyncTask.delegate=ServicioDB.this;
                //execute the async task
                asyncTask.execute();

                if(data==null){
                    Log.i(LOG_TAG,"Data null");
                }
                else {
                    if (!(data.equalsIgnoreCase(""))) {
                        if(notis.size()<notisLoaded.size()) {
                            for (int i = notis.size(); i < notisLoaded.size(); i++) {
                                // Inicio el servicio de notificaciones accediendo al servicio
                                nm = (NotificationManager) getSystemService(ns);

                                // Realizo una notificacion por medio de un metodo hecho por mi
                                notificacion(icono_r, "NotifiK", notisLoaded.get(i).getHeader(), notisLoaded.get(i).getDescription());

                                // Lanzo la notificacion creada en el paso anterior
                                nm.notify(i + 1, notif);
                                notis=notisLoaded;
                            }
                        }else{
                            Log.i(LOG_TAG,"No notis to load");
                        }

                    }else{
                        Log.i(LOG_TAG,"Data empty");
                    }

                }
            }
        }, 0, 30000);//put here time 1000 milliseconds=1 second

        return START_STICKY;
    }

   /* @Override
    public void onStart(Intent intent, int startId) {
        //startForeground(1,notif);
        Log.i(LOG_TAG,"Notifik Service Init");
        time.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                Log.i(LOG_TAG,"Notifik Service Query Thread Init");

                ConsultarNoti asyncTask =new ConsultarNoti();
                //this to set delegate/listener back to this class
                asyncTask.delegate=ServicioDB.this;
                //execute the async task
                asyncTask.execute();




                if(data==null){
                    Log.i(LOG_TAG,"Data null");
                }
                else {
                    if (!(data.equalsIgnoreCase(""))) {
                        if(notis.size()<notisLoaded.size()) {
                            for (int i = notis.size(); i < notisLoaded.size(); i++) {
                                // Inicio el servicio de notificaciones accediendo al servicio
                                nm = (NotificationManager) getSystemService(ns);

                                // Realizo una notificacion por medio de un metodo hecho por mi
                                notificacion(icono_r, "NotifiK", notisLoaded.get(i).getHeader(), notisLoaded.get(i).getDescription());

                                // Lanzo la notificacion creada en el paso anterior
                                nm.notify(i + 1, notif);
                                notis=notisLoaded;
                            }
                        }else{
                            Log.i(LOG_TAG,"No notis to load");
                        }

                    }else{
                        Log.i(LOG_TAG,"Data empty");
                    }

                }
            }
        }, 0, 30000);//put here time 1000 milliseconds=1 second



    }*/

    @Override
    protected void onHandleIntent(Intent intent) {    }


    //this override the implemented method from asyncTask
    @Override
    public void processFinish(String output) {
        //Here you will receive the result fired from async class
        //of onPostExecute(result) method.
        if(output==null||output==""){}else {
            data=output;
            notisLoaded = dbConverter.filtrarDatosNotificacion(data);
            if(activeActivity){
                DataSingleton.getInstance().compareArrayNotificaciones(notisLoaded);
            }
        }
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
            data=con.conectLoadNoti(DataSingleton.getInstance().getPrefCod());
            Log.i(LOG_TAG,"Data fetch");
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

    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(LOG_TAG, "Service onStartCommand");

        for (int i = 0; i < 3; i++)
        {
            long endTime = System.currentTimeMillis() + 10*1000;
            while (System.currentTimeMillis() < endTime) {
                synchronized (this) {
                    try {
                        wait(endTime - System.currentTimeMillis());
                    } catch (Exception e) {
                    }
                }
            }
            Log.i(LOG_TAG, "Service running");
        }
        return ServicioDB.START_STICKY;
    }*/

}
