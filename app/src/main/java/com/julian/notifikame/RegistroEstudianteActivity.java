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

public class RegistroEstudianteActivity extends Activity {

    private EditText cod;
    private EditText nombre;
    private EditText usuario;
    private EditText password;
    private Button registrarse;
    private Button cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setContentView(R.layout.activity_registro_estudiante);

        cod=(EditText)findViewById(R.id.estudiante_codigo);
        nombre=(EditText)findViewById(R.id.estudiante_nombre);
        usuario=(EditText)findViewById(R.id.estudiante_usuario);
        password=(EditText)findViewById(R.id.estudiante_pass);
        //Insertamos los datos de la persona.
        registrarse=(Button)findViewById(R.id.registro_estudiante_registrarse);
        cancelar=(Button)findViewById(R.id.registro_estudiante_cancelar);
        registrarse.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(!cod.getText().toString().trim().equalsIgnoreCase("")||
                        !nombre.getText().toString().trim().equalsIgnoreCase("")||
                        !usuario.getText().toString().trim().equalsIgnoreCase("")||
                        !password.getText().toString().trim().equalsIgnoreCase(""))

                    new Insertar(RegistroEstudianteActivity.this).execute();

                else
                    Toast.makeText(RegistroEstudianteActivity.this, "Hay información por rellenar", Toast.LENGTH_LONG).show();
            }

        });
        cancelar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroEstudianteActivity.this, LoginActivity.class);
                startActivity(intent);
                RegistroEstudianteActivity.this.finish();
            }
        });
    }

    //Inserta los datos de las Personas en el servidor.
    private boolean insertar(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/registro_estudiante.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("cod_estudiante",cod.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nom_estudiante",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("user_estudiante",usuario.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pword_estudiante",password.getText().toString().trim()));

        try {
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpclient.execute(httppost);
            return true;
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    //AsyncTask para insertar Personas
    class Insertar extends AsyncTask<String,String,String> {

        private Activity context;

        Insertar(Activity context){
            this.context=context;
        }
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(insertar())
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Estudiante insertada con éxito", Toast.LENGTH_LONG).show();
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
                        Toast.makeText(context, "Estudiante no insertada con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }
}
