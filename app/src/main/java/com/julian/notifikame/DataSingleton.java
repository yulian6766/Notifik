package com.julian.notifikame;

import android.os.StrictMode;

import java.util.ArrayList;

/**
 * Created by Julian on 29/08/2016.
 */
public class DataSingleton {

    private static DataSingleton instance = new DataSingleton();
    private DataSingleton(){    }

    private DBDataConverter dbConveter = new DBDataConverter();
    private Conexion con = new Conexion();
    private Usuario user = new Usuario();
    private ArrayList<Grupo> arrayGrupos;
    private String data;

    public  void setUser(Usuario usuario){  user = usuario; }
    public  void setUserCode(String code){  user.setCodigo(code); }

    public String getUserCode(){    return user.getCodigo();    }


    public boolean loadGrupos(){
        boolean resultado=false;
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            data = con.conectLoadGrupos(user.getCodigo());
        }catch (Exception e) {
            e.printStackTrace();
            //Retorna si la consulta es efectiva
        }
        if(!(data.equalsIgnoreCase(""))) {
            arrayGrupos = dbConveter.filtrarDatosGrupos(data);
            resultado=true;
        }
        return resultado;
    }

    public ArrayList<Grupo> obtenerGrupos() {
        return arrayGrupos;
    }

    public static DataSingleton getInstance(){  return instance;    }
    public static void setIntances(DataSingleton instance){DataSingleton.instance = instance;}

}
