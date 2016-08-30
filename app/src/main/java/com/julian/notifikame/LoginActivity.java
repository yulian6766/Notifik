package com.julian.notifikame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

public class LoginActivity extends Activity {

    private SharedPreferences.Editor editor;
    private EditText ediTxtLogin;
    private EditText ediTxtPass;
    private CheckBox chkEstudiante;
    private CheckBox chkProfesor;
    private Validaciones val;
    private Conexion con;
    private String loginGuardado;
    private String passGuardado;
    private String codGuardado;
    private String tipoUsuario;
    private Button btnAceptar;
    private Button btnSalir;
    private Button btnRegistro;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SharedPreferences prefs = getSharedPreferences("DatosGuardados", Context.MODE_PRIVATE);
        editor = prefs.edit();
        loginGuardado = prefs.getString("login", "");
        passGuardado = prefs.getString("pass", "");
        codGuardado = prefs.getString("cod", "");
        tipoUsuario = prefs.getString("tipoUsuario", "");


        ediTxtLogin = (EditText) findViewById(R.id.lblLogin);
        ediTxtPass = (EditText) findViewById(R.id.lblPass);
        btnAceptar = (Button) findViewById(R.id.BtnAceptar);
        btnSalir = (Button) findViewById(R.id.BtnCancelar);
        btnRegistro = (Button) findViewById(R.id.btn_registro);
        chkEstudiante = (CheckBox) findViewById(R.id.chk_estudiante);
        chkProfesor = (CheckBox) findViewById(R.id.chk_profesor);

        val = new Validaciones();

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
            if (loginGuardado.equalsIgnoreCase("")) {

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(chkEstudiante.isChecked()) {
                            con = new Conexion();

                            String login = ediTxtLogin.getText().toString();
                            String pass = ediTxtPass.getText().toString();

                            if (val.validarCampo(login, pass)) {

                                //boolean prueba=true;
                                if (con.conexion(login, pass, 1)) {
                                    //if (prueba==true) {

                                    editor.putString("login", login);
                                    editor.putString("pass", pass);
                                    editor.putString("tipoUsuario", "1");                               //1 si es estudiante 2 profesor
                                    editor.putString("cod", DataSingleton.getInstance().getUserCode());
                                    editor.commit();

                                    Intent intent = new Intent(LoginActivity.this, EstudianteActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("login", loginGuardado);
                                    b.putString("pass", passGuardado);
                                    b.putString("tipoUsuario", tipoUsuario);
                                    b.putString("cod", codGuardado);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    LoginActivity.this.finish();

                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "El usuario digitado no existe", Toast.LENGTH_SHORT);
                                    toast.show();
                                    ediTxtLogin.setText("");
                                    ediTxtPass.setText("");
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "El campo de login solo puede tener numeros y letras", Toast.LENGTH_SHORT);
                                toast.show();
                            }
                        }else{
                            con = new Conexion();

                            String login = ediTxtLogin.getText().toString();
                            String pass = ediTxtPass.getText().toString();

                            if (val.validarCampo(login,pass)) {

                                //boolean prueba=true;
                                if (con.conexion(login, pass, 2)) {
                                    //if (prueba==true) {

                                    editor.putString("login", login);
                                    editor.putString("pass", pass);
                                    editor.putString("tipoUsuario", "2");
                                    editor.putString("cod", DataSingleton.getInstance().getUserCode());
                                    editor.commit();

                                    Intent intent = new Intent(LoginActivity.this, ProfesorActivity.class);
                                    Bundle b = new Bundle();
                                    b.putString("login", loginGuardado);
                                    b.putString("pass", passGuardado);
                                    b.putString("tipoUsuario", tipoUsuario);
                                    b.putString("cod", codGuardado);
                                    intent.putExtras(b);
                                    startActivity(intent);
                                    LoginActivity.this.finish();

                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "El usuario digitado no existe", Toast.LENGTH_SHORT);
                                    toast.show();
                                    ediTxtLogin.setText("");
                                    ediTxtPass.setText("");
                                }
                            } else {
                                Toast toast = Toast.makeText(getApplicationContext(), "El campo de login solo puede tener numeros y letras", Toast.LENGTH_SHORT);
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
                if(tipoUsuario.equalsIgnoreCase("1")) {

                    Intent intent = new Intent(LoginActivity.this, EstudianteActivity.class);
                    Bundle b = new Bundle();
                    b.putString("login", loginGuardado);
                    b.putString("pass", passGuardado);
                    b.putString("tipoUsuario", tipoUsuario);
                    b.putString("cod", codGuardado);
                    DataSingleton.getInstance().setUserCode(codGuardado);
                    intent.putExtras(b);
                    startActivity(intent);
                    LoginActivity.this.finish();
                }else{
                    Intent intent = new Intent(LoginActivity.this, ProfesorActivity.class);
                    Bundle b = new Bundle();
                    b.putString("login", loginGuardado);
                    b.putString("pass", passGuardado);
                    b.putString("tipoUsuario", tipoUsuario);
                    b.putString("cod", codGuardado);
                    DataSingleton.getInstance().setUserCode(codGuardado);
                    intent.putExtras(b);
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
