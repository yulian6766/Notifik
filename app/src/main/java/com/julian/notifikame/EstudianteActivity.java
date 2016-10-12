package com.julian.notifikame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.Timer;
import java.util.TimerTask;

public class EstudianteActivity extends ActionBarActivity {

    private ActionBarDrawerToggle drawerToggle;
    private FragmentManager fragmentManager;

    @Override
    public void onBackPressed() {
        this.finish();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        //Lanzar Servicio
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startService(new Intent(getApplicationContext(), ServicioDB.class));
            }
        }, 0, 30000);//put here time 1000 milliseconds=1 second

        fragmentManager = getSupportFragmentManager();

        NotificacionListFragment fragmentList = new NotificacionListFragment();
        fragmentManager.beginTransaction().replace(R.id.estudiante_fragment_container, fragmentList).commit();

    }

  /*  @Override
    protected void onResume() {
        setContentView(R.layout.activity_estudiante);

        //Lanzar Servicio
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                startService(new Intent(getApplicationContext(), ServicioDB.class));
            }
        }, 0, 15000);//put here time 1000 milliseconds=1 second

        fragmentManager = getSupportFragmentManager();

        NotificacionListFragment fragmentList = new NotificacionListFragment();
        fragmentManager.beginTransaction().replace(R.id.estudiante_fragment_container, fragmentList).commit();
        super.onResume();
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notifik, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Objetos para crear y leer preferencias y lo guarda en variable de tipo String

        switch (item.getItemId()) {
            case R.id.action_settings:
                //Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_logout:

                //Elimina el String del documento en el archivo de prefenrencias
                DataSingleton.getInstance().removePreferences();

                //Referencia a la nueva activity que se va a ejecutar y la ejecuta
                Intent intent = new Intent(EstudianteActivity.this, LoginActivity.class);
                startActivity(intent);

                //Cierra la actividad despues de cerrar sesion
                EstudianteActivity.this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
