package com.julian.notifikame;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Julian on 30/08/2016.
 */
public class AddEstudianteFragment extends Fragment {

    private Button btnAgregar;
    private Spinner spnGrupo;
    private Spinner spnEstudiante;
    private Conexion con = new Conexion();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializar Modelo
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.addestudiante_fragment_layout,container,false);    ///Cargar XML, Vista Padre, Si quiero dejarlo siempre

        ArrayList<String> arrayString = new ArrayList<String>();

        btnAgregar=(Button)v.findViewById(R.id.btn_agregar_estudiante);
        spnGrupo=(Spinner) v.findViewById(R.id.add_group_spinner);
        spnEstudiante=(Spinner) v.findViewById(R.id.add_group_student_spinner);

        DataSingleton.getInstance().loadEstudiantes();
        arrayString=DataSingleton.getInstance().cargarnombresArray(1);
        ArrayAdapter<String> adp1 = new ArrayAdapter<String> (v.getContext(),android.R.layout.simple_spinner_dropdown_item,arrayString);
        spnEstudiante.setAdapter(adp1);

        DataSingleton.getInstance().loadTotalGrupos();
        arrayString=DataSingleton.getInstance().cargarnombresArray(2);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String> (v.getContext(),android.R.layout.simple_spinner_dropdown_item,arrayString);
        spnGrupo.setAdapter(adp2);


        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if(con.conectInsertAsignacion(DataSingleton.getInstance()
                                                    .getCodEstudianteNombre(spnEstudiante.getSelectedItem().toString()),
                                                DataSingleton.getInstance()
                                                        .getCodGrupoNombre(spnGrupo.getSelectedItem().toString()),
                                                DataSingleton.getInstance()
                                                        .getUserCode())){
                        Toast.makeText(v.getContext(), "Registro estudiante con éxito", Toast.LENGTH_LONG).show();
                    }else{
                        Toast.makeText(v.getContext(), "No se pudo registrar el estudiante", Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                    //Retorna si la consulta es efectiva
                }

            }
        });



        return v;


    }
}
