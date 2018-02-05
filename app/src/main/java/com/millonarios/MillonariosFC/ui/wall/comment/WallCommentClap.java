package com.millonarios.MillonariosFC.ui.wall.comment;

/**
 * Created by Leonardojpr on 1/25/18.
 */

public class WallCommentClap {

    private String token;
    private String idcomentario;

    public WallCommentClap(String token, String idcomentario) {
        this.token = token;
        this.idcomentario = idcomentario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getIdcomentario() {
        return idcomentario;
    }

    public void setIdcomentario(String idcomentario) {
        this.idcomentario = idcomentario;
    }
}
