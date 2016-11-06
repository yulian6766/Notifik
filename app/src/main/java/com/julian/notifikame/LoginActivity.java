package com.julian.notifikame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends Activity {

    private EditText ediTxtLogin;
    private EditText ediTxtPass;
    private CheckBox chkEstudiante;
    private CheckBox chkProfesor;
    private Button btnAceptar;
    private Button btnSalir;
    private Button btnRegistro;

    private Validaciones val = new Validaciones();;
    private Conexion con  = new Conexion();
    private CheckInternet checkInet= new CheckInternet();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DataSingleton.getInstance().setDataContext(this.getApplicationContext());
        DataSingleton.getInstance().loadPreferences();

        ediTxtLogin = (EditText) findViewById(R.id.lblLogin);
        ediTxtPass = (EditText) findViewById(R.id.lblPass);
        btnAceptar = (Button) findViewById(R.id.BtnAceptar);
        btnSalir = (Button) findViewById(R.id.BtnCancelar);
        btnRegistro = (Button) findViewById(R.id.btn_registro);
        chkEstudiante = (CheckBox) findViewById(R.id.chk_estudiante);
        chkProfesor = (CheckBox) findViewById(R.id.chk_profesor);

        chkEstudiante.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    chkProfesor.setChecked(false);
                }else{
                    chkProfesor.setChecked(true);
                }
            }
        });

        chkProfesor.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    chkEstudiante.setChecked(false);
                }else{
                    chkEstudiante.setChecked(true);
                }
            }
        });


        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(chkEstudiante.isChecked()) {
                    Intent intent = new Intent(LoginActivity.this, RegistroEstudianteActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Intent intent = new Intent(LoginActivity.this, RegistroProfesorActivity.class);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }
        });
            if (DataSingleton.getInstance().getPrefLog().equalsIgnoreCase("")&&
                    DataSingleton.getInstance().getPrefCod().equalsIgnoreCase("")&&
                    DataSingleton.getInstance().getPrefpass().equalsIgnoreCase("")&&
                    DataSingleton.getInstance().getPrefTipo().equalsIgnoreCase("")) {

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(chkEstudiante.isChecked()) {

                            if (val.validarCampo(ediTxtLogin.getText().toString().trim(), ediTxtPass.getText().toString().trim())) {

                                if(checkInet.isOnlineNet()) {
                                    //1 Estudiante 2 Profesor
                                    if (con.conexion(ediTxtLogin.getText().toString().trim(), ediTxtPass.getText().toString().trim(), 1)) {


                                        DataSingleton.getInstance().setPreferences(
                                                ediTxtLogin.getText().toString(),
                                                ediTxtPass.getText().toString(), "1");

                                        Intent intent = new Intent(LoginActivity.this, EstudianteActivity.class);
                                        startActivity(intent);
                                        LoginActivity.this.finish();

                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Los campos de login no son correctos", Toast.LENGTH_SHORT);
                                        toast.show();
                                        ediTxtLogin.setText("");
                                        ediTxtPass.setText("");
                                    }
                                }else{
                                    Toast toast = Toast.makeText(getApplicationContext(), "Error de conexion, comprueba tu internet", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Los campos de login no son correctos", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else{

                            if (val.validarCampo(ediTxtLogin.getText().toString().trim(), ediTxtPass.getText().toString().trim())) {

                                if(checkInet.isOnlineNet()) {
                                    //1 Estudiante 2 Profesor
                                    if (con.conexion(ediTxtLogin.getText().toString().trim(), ediTxtPass.getText().toString().trim(), 2)) {


                                        DataSingleton.getInstance().setPreferences(
                                                ediTxtLogin.getText().toString(),
                                                ediTxtPass.getText().toString(), "2");

                                        Intent intent = new Intent(LoginActivity.this, ProfesorActivity.class);
                                        startActivity(intent);
                                        LoginActivity.this.finish();

                                    } else {
                                        Toast toast = Toast.makeText(getApplicationContext(), "Los campos de login no son correctos", Toast.LENGTH_SHORT);
                                        toast.show();
                                        ediTxtLogin.setText("");
                                        ediTxtPass.setText("");
                                    }
                                }else{
                                    Toast toast = Toast.makeText(getApplicationContext(), "Error de conexion, comprueba tu internet", Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "Los campos de login no son correctos", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }
                    }
                });


                btnSalir.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        LoginActivity.this.finish();
                    }
                });
            } else {
                if(DataSingleton.getInstance().getPrefTipo().equalsIgnoreCase("1")) {

                    Intent intent = new Intent(LoginActivity.this, EstudianteActivity.class);
                    DataSingleton.getInstance().loadUser();
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Intent intent = new Intent(LoginActivity.this, ProfesorActivity.class);
                    DataSingleton.getInstance().loadUser();
                    startActivity(intent);
                    LoginActivity.this.finish();
                }
            }




        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.julian.notifikame/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Login Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.julian.notifikame/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
