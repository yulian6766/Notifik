package com.julian.notifikame;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.ListFragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class ProfesorActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle drawerToggle;

    private CharSequence activityTitle;
    private CharSequence itemTitle;
    private String[] tagTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);
        itemTitle = activityTitle = getTitle();
        tagTitles = getResources().getStringArray(R.array.Tags);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);

        // Setear una sombra sobre el contenido principal cuando el drawer se despliegue
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        //Crear elementos de la lista
        ArrayList<DrawerItem> items = new ArrayList<DrawerItem>();
        items.add(new DrawerItem(tagTitles[0], R.drawable.ic_html));
        items.add(new DrawerItem(tagTitles[1], R.drawable.ic_css));
        items.add(new DrawerItem(tagTitles[2], R.drawable.ic_javascript));
        items.add(new DrawerItem(tagTitles[3], R.drawable.ic_angular));
        items.add(new DrawerItem(tagTitles[4], R.drawable.ic_python));
        items.add(new DrawerItem(tagTitles[5], R.drawable.ic_ruby));


        // Relacionar el adaptador y la escucha de la lista del drawer
        drawerList.setAdapter(new DrawerListAdapter(this, items));
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        // Habilitar el icono de la app por si hay algún estilo que lo deshabilitó
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Crear ActionBarDrawerToggle para la apertura y cierre
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(itemTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(activityTitle);

                /*Usa este método si vas a modificar la action bar
                con cada fragmento
                 */
                //invalidateOptionsMenu();
            }
        };
        //Seteamos la escucha
        drawerLayout.setDrawerListener(drawerToggle);

        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_notifik, menu);
        return super.onCreateOptionsMenu(menu);
    }



    /* La escucha del ListView en el Drawer */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //Objetos para crear y leer preferencias y lo guarda en variable de tipo String
        SharedPreferences prefs = getSharedPreferences("DatosGuardados", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        if (drawerToggle.onOptionsItemSelected(item)) {
            // Toma los eventos de selección del toggle aquí
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                //Log.i("ActionBar", "Nuevo!");
                return true;
            case R.id.action_logout:

                //Elimina el String del documento en el archivo de prefenrencias
                editor.remove("login");
                editor.remove("pass");
                editor.remove("tipoUsuario");
                editor.commit();

                //Referencia a la nueva activity que se va a ejecutar y la ejecuta
                Intent intent = new Intent(ProfesorActivity.this, LoginActivity.class);
                startActivity(intent);

                //Cierra la actividad despues de cerrar sesion
                ProfesorActivity.this.finish();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void selectItem(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        switch (position) {

            case 0:
                // Reemplazar el contenido del layout principal por un fragmento
                GrupoListFragment fragmentList = new GrupoListFragment();
                //Bundle args = new Bundle();
                //args.putInt(ProfesorFragment.ARG_ARTICLES_NUMBER, position);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentList).commit();
                break;

            case 1:
                // Reemplazar el contenido del layout principal por un fragmento
                CrearGrupoFragment fragmentCrearGrupo = new CrearGrupoFragment();
                //Bundle args = new Bundle();
                //args.putInt(ProfesorFragment.ARG_ARTICLES_NUMBER, position);
                //fragmentCrearGrupo.setArguments(args);

                fragmentManager.beginTransaction().replace(R.id.content_frame, fragmentCrearGrupo).commit();

                break;

            default:

                break;

        }
        // Se actualiza el item seleccionado y el título, después de cerrar el drawer
        drawerList.setItemChecked(position, true);
        setTitle(tagTitles[position]);
        drawerLayout.closeDrawer(drawerList);
    }

    /* Método auxiliar para setear el titulo de la action bar */
    @Override
    public void setTitle(CharSequence title) {
        itemTitle = title;
        getSupportActionBar().setTitle(itemTitle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sincronizar el estado del drawer
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Cambiar las configuraciones del drawer si hubo modificaciones
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
