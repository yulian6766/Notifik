package com.julian.notifikame;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Julian on 29/08/2016.
 */
public class DBDataConverter {

    protected boolean filtrarDatosProfesor(String datos){
        if(!datos.equalsIgnoreCase("[]")){
            JSONObject json;
            try {
                json = new JSONObject(datos);
                JSONArray jsonArray = json.optJSONArray("profesor");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Usuario user =new Usuario();
                    JSONObject jsonArrayChild = jsonArray.getJSONObject(i);
                    user.setCodigo(jsonArrayChild.optString("cod_profesor"));
                    user.setNombre(jsonArrayChild.optString("nom_profesor"));
                    user.setUsuario(jsonArrayChild.optString("user_profesor"));
                    user.setPassword(jsonArrayChild.optString("pword_profesor"));
                    DataSingleton.getInstance().setUser(user);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }

    protected void filtrarDatosEstudiante(String datos){
        if(!datos.equalsIgnoreCase("[]")){
            JSONObject json;
            try {
                json = new JSONObject(datos);
                JSONArray jsonArray = json.optJSONArray("estudiante");
                for (int i = 0; i < jsonArray.length(); i++) {
                    Usuario user =new Usuario();
                    JSONObject jsonArrayChild = jsonArray.getJSONObject(i);
                    user.setCodigo(jsonArrayChild.optString("cod_profesor"));
                    user.setNombre(jsonArrayChild.optString("nom_profesor"));
                    user.setUsuario(jsonArrayChild.optString("user_profesor"));
                    user.setPassword(jsonArrayChild.optString("pword_profesor"));
                    DataSingleton.getInstance().setUser(user);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    protected String convertStreamToString(InputStream is) throws IOException {
        //Log.d("paso1","paso1");
        if (is != null) {
            StringBuilder sb = new StringBuilder();
            String line;
            try {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(is, "UTF-8"));
                //while ((line = reader.readLine()) != null) {
                //sb.append(line).append("\n");
                //Log.d("paso3",line);
                line = reader.readLine();
                sb.append(line);
                //}
            }
            finally {
                is.close();
            }
            return sb.toString().trim();
        } else {
            return "";
        }
    }
}
