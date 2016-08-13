package com.julian.notifikame;

/**
 * Created by Jpatino on 13/05/2016.
 */
public class DrawerItem {
    private String name;
    private int iconId;

    public DrawerItem(String name, int iconId) {
        this.name = name;
        this.iconId = iconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }
}
