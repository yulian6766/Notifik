package com.julian.notifikame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Julian on 01/10/2016.
 */

public class BuscarEstudianteDialog extends DialogFragment {
    private EditText stundentCode;
    private String param;


    public static  BuscarEstudianteDialog createDialog(){
        BuscarEstudianteDialog dialog = new BuscarEstudianteDialog();
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View dialogView = getActivity().getLayoutInflater()
                .inflate(R.layout.buscar_estudiante_dialog, null);

        stundentCode=(EditText) dialogView.findViewById(R.id.txt_filtro_estudiante);



        return new AlertDialog.Builder(getActivity())
                .setTitle(R.string.buscar_student)
                .setView(dialogView)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        filtrarTexto();
                    }
                })
                .create();
    }

    public void filtrarTexto() {
        if (stundentCode.getText().toString().equalsIgnoreCase("")){
        }else{
            param=stundentCode.getText().toString();
            ((AddEstudianteFragment)getTargetFragment()).filtrarTexto(param);
        }

    }



}
