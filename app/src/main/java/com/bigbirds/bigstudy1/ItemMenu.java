package com.bigbirds.bigstudy1;

/**
 * Created by Admin on 09/12/2015.
 */
public class ItemMenu {
    private String name;
    private int icon;

    public ItemMenu(String name, int icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }
}
