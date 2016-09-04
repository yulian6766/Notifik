package com.julian.notifikame;

/**
 * Created by Julian on 29/08/2016.
 */
public class Usuario {
    private String codigo;
    private String nombre;
    private String usuario;
    private String password;

    public Usuario(String cod,String name, String user, String pass){
        this.codigo=cod;
        this.nombre=name;
        this.password=pass;
        this.usuario=user;
    }

    public Usuario(){}

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
