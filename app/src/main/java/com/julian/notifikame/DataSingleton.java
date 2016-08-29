package com.julian.notifikame;

/**
 * Created by Julian on 29/08/2016.
 */
public class DataSingleton {

    private static DataSingleton instance = new DataSingleton();
    private DataSingleton(){    }

    private Usuario user;

    public  void setUser(Usuario usuario){
        user = usuario;
    }

    public String getUserCode(){    return user.getCodigo();    }

    public static DataSingleton getInstance(){return instance;}
    public static void setIntances(DataSingleton instance){DataSingleton.instance = instance;}

}
