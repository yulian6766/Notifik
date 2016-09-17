package com.julian.notifikame;

/**
 * Created by Julian on 11/08/2016.
 */
public class Validaciones {

    protected boolean validarCampo(String login, String pass){
        boolean resultadoCampo=true;
        if (login.equalsIgnoreCase("")){

            resultadoCampo = false;
        }
        if(pass.equalsIgnoreCase("")) {
            resultadoCampo = false;
        }

        return resultadoCampo;
    }

    protected boolean validarNoNull(String text){
        boolean resultadoCampo=false;
        if (!(text.equalsIgnoreCase(""))){
            resultadoCampo = true;
        }

        return resultadoCampo;
    }
}
