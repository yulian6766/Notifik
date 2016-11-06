package com.julian.notifikame;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistroProfesorActivity extends Activity {

    private EditText cod;
    private EditText nombre;
    private EditText usuario;
    private EditText password;
    private Button registrarse;
    private Button cancelar;

    private CheckInternet checkInet= new CheckInternet();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_registro_profesor);

        cod=(EditText)findViewById(R.id.profesor_codigo);
        nombre=(EditText)findViewById(R.id.profesor_nombre);
        usuario=(EditText)findViewById(R.id.profesor_usuario);
        password=(EditText)findViewById(R.id.profesor_pass);
        //Insertamos los datos de la persona.
        registrarse=(Button)findViewById(R.id.registro_profesor_registrarse);
        cancelar=(Button)findViewById(R.id.registro_profesor_cancelar);
        registrarse.setOnClickListener(new OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (!cod.getText().toString().trim().equalsIgnoreCase("") ||
                        !nombre.getText().toString().trim().equalsIgnoreCase("") ||
                        !usuario.getText().toString().trim().equalsIgnoreCase("") ||
                        !password.getText().toString().trim().equalsIgnoreCase("")) {

                    if(checkInet.isOnlineNet()) {
                        new Insertar(RegistroProfesorActivity.this).execute();
                        Intent intent = new Intent(RegistroProfesorActivity.this, LoginActivity.class);
                        startActivity(intent);
                        RegistroProfesorActivity.this.finish();
                    }else{
                        Toast toast = Toast.makeText(getApplicationContext(), "Error de conexion, comprueba tu internet", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                } else {
                    Toast.makeText(RegistroProfesorActivity.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
                }
            }

        });
        cancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroProfesorActivity.this, LoginActivity.class);
                startActivity(intent);
                RegistroProfesorActivity.this.finish();
            }
        });
    }


    //AsyncTask para insertar Profesores
    class Insertar extends AsyncTask<String,String,String>{

        private Activity context;
        private Conexion con = new Conexion();

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(con.insertarProfesor(cod,nombre,usuario,password)=="")
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Profesor insertada con éxito", Toast.LENGTH_LONG).show();
                        nombre.setText("");
                        cod.setText("");
                        usuario.setText("");
                        password.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Profesor no insertada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(RegistroProfesorActivity.this, LoginActivity.class);
        startActivity(intent);
        RegistroProfesorActivity.this.finish();
    }
}
