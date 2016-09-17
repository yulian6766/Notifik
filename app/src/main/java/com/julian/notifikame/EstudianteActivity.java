package com.julian.notifikame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class EstudianteActivity extends ActionBarActivity {

    private ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);

        startService(new Intent(this, ServicioDB.class));
    }

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
