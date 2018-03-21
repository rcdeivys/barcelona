package com.BarcelonaSC.BarcelonaApp.ui.wall.post;

/**
 * Created by Leonardojpr on 1/23/18.
 */

public class WallCreateClapPost {

    private String token;
    private String idpost;

    public WallCreateClapPost(String token, String idpost) {
        this.token = token;
        this.idpost = idpost;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdpost() {
        return idpost;
    }

    public void setIdpost(String idpost) {
        this.idpost = idpost;
    }

}
