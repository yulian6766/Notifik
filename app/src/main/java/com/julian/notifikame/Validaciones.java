package com.julian.notifikame;

/**
 * Created by Julian on 11/08/2016.
 */
public class Validaciones {

    protected boolean validarCampo(String login, String pass){
        boolean resultadoCampo;
        if (!(login.equalsIgnoreCase("")&&pass.equalsIgnoreCase(""))){
            resultadoCampo = true;
        } else {
            resultadoCampo = false;
        }

        return resultadoCampo;
    }
}
