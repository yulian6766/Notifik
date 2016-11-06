package com.julian.notifikame;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Julian on 16/09/2016.
 */
public class CrearNotificacionFragment extends Fragment {

    private Button btnAgregar;
    private EditText txtGrupo;
    private EditText txtHeader;
    private EditText txtDescripcion;

    private Validaciones val=new Validaciones();
    private Conexion con=new Conexion();
    private CheckInternet checkInet= new CheckInternet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Inicializar Modelo
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.crear_notificacion_fragment_layout,container,false);    ///Cargar XML, Vista Padre, Si quiero dejarlo siempre

        btnAgregar=(Button)v.findViewById(R.id.btn_crear_notificacion);
        txtGrupo=(EditText) v.findViewById(R.id.txt_cod_grupo_notificacion_fragment);
        txtHeader=(EditText) v.findViewById(R.id.txt_header_notificacion);
        txtDescripcion=(EditText) v.findViewById(R.id.txt_descripcion_notificacion);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val.validarNoNull(txtGrupo.getText().toString().trim())&&
                        val.validarNoNull(txtHeader.getText().toString().trim())&&
                        val.validarNoNull(txtDescripcion.getText().toString().trim())) {
                    if(checkInet.isOnlineNet()) {
                        try {
                            StrictMode.ThreadPolicy policy =
                                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            if (con.conectInsertNotificacion(txtGrupo.getText().toString().trim(),
                                    txtHeader.getText().toString().trim(),
                                    txtDescripcion.getText().toString().trim())) {
                                Toast toast = Toast.makeText(v.getContext(), "Notificacion creada con exito", Toast.LENGTH_SHORT);
                                toast.show();
                            } else {
                                Toast toast = Toast.makeText(v.getContext(), "No se crear la notificacion", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            //Retorna si la consulta es efectiva
                        }
                    }else{
                        Toast toast = Toast.makeText(v.getContext(), "Error de conexion, comprueba tu internet", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast toast = Toast.makeText(v.getContext(), "Los campos no pueden ser vacios", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });



        return v;


    }
}
