package com.julian.notifikame;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class GrupoEstudiantesActivity extends ActionBarActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_estudiantes);

        fragmentManager = getSupportFragmentManager();
        EstudianteListFragment fragmentList = new EstudianteListFragment();

        fragmentManager.beginTransaction().replace(R.id.estudiantes_grupo_fragment_container, fragmentList).commit();

    }
}
