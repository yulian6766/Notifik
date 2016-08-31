package com.julian.notifikame;

/**
 * Created by JdRod on 8/31/2016.
 */
public class Notificacion {
    private int id;
    private String group;
    private String header;
    private String Description;

    public Notificacion(int id, String group, String header, String description) {
        this.id = id;
        this.group = group;
        this.header = header;
        Description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
