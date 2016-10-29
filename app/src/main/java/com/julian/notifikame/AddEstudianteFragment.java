package com.julian.notifikame;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Julian on 30/08/2016.
 */
public class AddEstudianteFragment extends Fragment {

    private Button btnAgregar;
    private Spinner spnGrupo;
    private TextView lblEstudiante;
    private ImageButton imgbtnBuscar;
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
        lblEstudiante=(TextView) v.findViewById(R.id.lbl_add_student_fragment);
        imgbtnBuscar=(ImageButton)v.findViewById(R.id.imgbtn_find_estudent);

        DataSingleton.getInstance().loadEstudiantes();
        DataSingleton.getInstance().loadTotalGrupos();
        arrayString=DataSingleton.getInstance().cargarnombresArray(2);
        ArrayAdapter<String> adp2 = new ArrayAdapter<String> (v.getContext(),android.R.layout.simple_spinner_dropdown_item,arrayString);
        spnGrupo.setAdapter(adp2);

        imgbtnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Img Button Pressed","filter student");
                BuscarEstudianteDialog dialog = new BuscarEstudianteDialog();
                dialog.setTargetFragment(AddEstudianteFragment.this,0);
                dialog.show(getFragmentManager(),"Create Dialog");
            }
        });

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    StrictMode.ThreadPolicy policy =
                            new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);
                    if(con.conectInsertAsignacion(DataSingleton.getInstance()
                                                    .searchCodEstudiante(lblEstudiante.getText().toString()),
                                                DataSingleton.getInstance()
                                                        .getCodGrupoNombre(spnGrupo.getSelectedItem().toString()),
                                                DataSingleton.getInstance()
                                                        .getUserCode())==""){
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


    public void filtrarTexto(String param) {
        String temp=DataSingleton.getInstance()
                .searchCodEstudiante(param);
        if(temp.equalsIgnoreCase("")) {
            Toast toast = Toast.makeText(this.getActivity().getBaseContext(), "El codigo del estudiante no existe", Toast.LENGTH_SHORT);
            toast.show();
            lblEstudiante.setText(R.string.student_name);
        }else{
            lblEstudiante.setText(temp);
        }
    }
}
