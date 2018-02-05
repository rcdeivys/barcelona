package com.millonarios.MillonariosFC.ui.wall.comment;

/**
 * Created by Leonardojpr on 1/25/18.
 */

public class WallCommentCreate {

    private String token;
    private String idpost;
    private String comentario;
    private String foto;

    public WallCommentCreate(String token, String idpost, String comentario, String foto) {
        this.token = token;
        this.idpost = idpost;
        this.comentario = comentario;
        this.foto = foto;
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

    public String getcomentario() {
        return comentario;
    }

    public void setcomentario(String comentario) {
        this.comentario = comentario;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
