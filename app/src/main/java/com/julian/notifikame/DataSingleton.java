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
    private ArrayList<Grupo> arrayGruposProfesor;
    private ArrayList<Usuario> arrayUsuarios;
    private ArrayList<Grupo> arrayGrupos;
    private String data;

    public  void setUser(Usuario usuario){  user = usuario; }
    public  void setUserCode(String code){  user.setCodigo(code); }

    public String getUserCode(){    return user.getCodigo();    }

    public boolean loadEstudiantes(){
        boolean resultado=false;
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            data = con.conectLoadEstudiantes();
        }catch (Exception e) {
            e.printStackTrace();
            //Retorna si la consulta es efectiva
        }
        if(!(data.equalsIgnoreCase(""))) {
            arrayUsuarios = dbConveter.filtrarDatosEstudiantes(data);
            resultado=true;
        }
        return resultado;
    }

    public boolean loadTotalGrupos(){
        boolean resultado=false;
        try {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            data = con.conectLoadGrupos();
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

    //1 si es de tipo Estudiantes, otro valor Grupos
    public ArrayList<String> cargarnombresArray(int opc){
        ArrayList<String> array = new ArrayList<String>();;
        if(opc==1){
            for(int i=0; i<arrayUsuarios.size(); i++){
                array.add(arrayUsuarios.get(i).getNombre());
            }
        }else{
            for(int i=0; i<arrayGrupos.size(); i++){
                array.add(arrayGrupos.get(i).getNomGrupo());
            }
        }
        return array;
    }


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
            arrayGruposProfesor = dbConveter.filtrarDatosGrupos(data);
            resultado=true;
        }
        return resultado;
    }

    public String getCodEstudianteNombre(String nombre){
        for(int i=0;i<arrayUsuarios.size();i++){
            if(nombre.equalsIgnoreCase(arrayUsuarios.get(i).getNombre())){
                return arrayUsuarios.get(i).getCodigo();
            }
        }
        return "";
    }

    public String getCodGrupoNombre(String nombre){
        for(int i=0;i<arrayGrupos.size();i++){
            if(nombre.equalsIgnoreCase(arrayGrupos.get(i).getNomGrupo())){
                return arrayGrupos.get(i).getCodGrupo();
            }
        }
        return "";
    }

    public ArrayList<Grupo> obtenerGrupos() {
        return arrayGruposProfesor;
    }

    public static DataSingleton getInstance(){  return instance;    }
    public static void setIntances(DataSingleton instance){DataSingleton.instance = instance;}

}
