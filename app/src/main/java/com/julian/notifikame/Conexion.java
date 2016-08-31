package com.julian.notifikame;

import android.os.StrictMode;
import android.widget.EditText;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Julian on 11/08/2016.
 */


public class Conexion {
    private boolean resultado=false;
    private DBDataConverter converter = new DBDataConverter();
    private String data;

    protected boolean conexion(String login, String pass, int opcion){
            //Metodo que realiza el query y se ejecuta en un Thread unido al UI
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            resultado = conectDBLogin(login,pass,opcion);
        }catch (Exception e) {
            e.printStackTrace();
            //Retorna si la consulta es efectiva
        }
        return resultado;
    }

    private boolean conectDBLogin(String login, String pass, int opcion){

        if(opcion==2){
            data = conectComprobarLoginProfesor(login, pass);
            if(!data.equalsIgnoreCase("")){
                converter.filtrarDatosProfesor(data);
                return true;
            }
        }else{
            data = conectComprobarLoginEstudiante(login, pass);
            if(!data.equalsIgnoreCase("")){
                converter.filtrarDatosEstudiante(data);
                return true;
            }
        }

        return false;
    }


    //Funcion que envia datos encapsulados por metodo POST
    protected String conectComprobarLoginProfesor(String doc, String pass){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("login",doc.trim()));
        nameValuePairs.add(new BasicNameValuePair("pass",pass.trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","log_pro"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            request = httpclient.execute(httppost, responseHandler);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            request = converter.convertStreamToString(instream);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    //Funcion que envia datos encapsulados por metodo POST
    protected String conectComprobarLoginEstudiante(String doc, String pass){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("login",doc.trim()));
        nameValuePairs.add(new BasicNameValuePair("pass",pass.trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","log_est"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            request = httpclient.execute(httppost, responseHandler);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            request = converter.convertStreamToString(instream);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    //Inserta los datos de las Estudiante en el servidor.
    protected boolean insertarEstudiante(EditText cod, EditText nombre, EditText usuario, EditText password){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("cod_estudiante",cod.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nom_estudiante",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("user_estudiante",usuario.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pword_estudiante",password.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","reg_est"));

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

    //Inserta los datos de las Profesores en el servidor.
    protected boolean insertarProfesor(EditText cod, EditText nombre, EditText usuario, EditText password){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(5);
        nameValuePairs.add(new BasicNameValuePair("cod_profesor",cod.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nom_profesor",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("user_profesor",usuario.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("pword_profesor",password.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","reg_pro"));

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

    //Inserta los datos de las Estudiante en el servidor.
    protected boolean insertarGrupo(EditText cod, EditText nombre){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor
        //Añadimos nuestros datos
        nameValuePairs = new ArrayList<NameValuePair>(3);
        nameValuePairs.add(new BasicNameValuePair("cod_grupo",cod.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("nom_grupo",nombre.getText().toString().trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","reg_grp"));

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

    //Funcion que envia datos encapsulados por metodo POST
    protected String conectLoadGrupos(String cod){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("cod",cod.trim()));
        nameValuePairs.add(new BasicNameValuePair("accion","load_grupos"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            request = httpclient.execute(httppost, responseHandler);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            request = converter.convertStreamToString(instream);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    //Funcion que envia datos encapsulados por metodo POST
    protected String conectLoadEstudiantes(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("accion","load_est"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            request = httpclient.execute(httppost, responseHandler);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            request = converter.convertStreamToString(instream);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    //Funcion que envia datos encapsulados por metodo POST
    protected String conectLoadGrupos(){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(1);
        nameValuePairs.add(new BasicNameValuePair("accion","load_grp"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            ResponseHandler<String> responseHandler = new BasicResponseHandler();
            request = httpclient.execute(httppost, responseHandler);

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            InputStream instream = entity.getContent();
            request = converter.convertStreamToString(instream);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return request;
    }

    //Funcion que envia datos encapsulados por metodo POST
    protected boolean conectInsertAsignacion(String cod_est, String cod_grp, String cod_pro){
        HttpClient httpclient;
        List<NameValuePair> nameValuePairs;
        HttpPost httppost;
        httpclient=new DefaultHttpClient();
        httppost= new HttpPost("http://notifk.gzpot.com/notifik/notifik.php"); // Url del Servidor

        String request="";

        //HttpClient httpclient = new DefaultHttpClient();
        //HttpPost httppost = new HttpPost("http://aulavirtualcolpsic.com/disp_connect.php");
        //String resultado="";
        HttpResponse response;

        nameValuePairs = new ArrayList<NameValuePair>(4);
        nameValuePairs.add(new BasicNameValuePair("cod_profesor",cod_pro));
        nameValuePairs.add(new BasicNameValuePair("cod_estudiante",cod_est));
        nameValuePairs.add(new BasicNameValuePair("cod_grupo",cod_grp));
        nameValuePairs.add(new BasicNameValuePair("accion","insert_asig"));

        try {
            //Ejecutamos y obtenemos la respuestaa del servidor
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
        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
