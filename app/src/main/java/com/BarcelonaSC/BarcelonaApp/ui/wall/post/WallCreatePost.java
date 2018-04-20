package com.BarcelonaSC.BarcelonaApp.ui.wall.post;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallCreatePost {
    private String token;
    private String mensaje;
    private String tipo_post;
    private String foto;
    private String thumbnail;

    public WallCreatePost(String token, String mensaje, String tipo_post, String foto, String thumbnail) {
        this.token = token;
        this.mensaje = mensaje;
        this.tipo_post = tipo_post;
        this.foto = foto;
        this.thumbnail = thumbnail;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipo_post() {
        return tipo_post;
    }

    public void setTipo_post(String tipo_post) {
        this.tipo_post = tipo_post;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
