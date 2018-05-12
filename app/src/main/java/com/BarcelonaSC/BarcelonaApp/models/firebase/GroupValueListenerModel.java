package com.BarcelonaSC.BarcelonaApp.models.firebase;

/**
 * Created by Carlos on 11/05/2018.
 */

public class GroupValueListenerModel {

    String id;
    boolean isListener = true;


    public boolean isListener() {
        return isListener;
    }

    public void setListener(boolean listener) {
        isListener = listener;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
