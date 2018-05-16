package com.BarcelonaSC.BarcelonaApp.ui.home.updatepopup;

/**
 * Created by makhnnar on 13/03/18.
 */

public enum UpdateConstants {

    TAG_CLOSE("close"),
    TAG_ACCEPT("accept");

    private String tag = "";

    UpdateConstants(String tag){
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
