package com.BarcelonaSC.BarcelonaApp.models;

/**
 * Created by root on 11/2/17.
 */

public class DrawerItem {

    private String key;
    private int icon;
    private String title;

    public DrawerItem(String key, int icon, String title) {
        this.key = key;
        this.icon = icon;
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
