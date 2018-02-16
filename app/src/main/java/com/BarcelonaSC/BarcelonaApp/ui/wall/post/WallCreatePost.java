package com.BarcelonaSC.BarcelonaApp.ui.wall.post;

/**
 * Created by Leonardojpr on 1/24/18.
 */

public class WallCreatePost {
    private String token;
    private String mensaje;
    private String foto;

    public WallCreatePost(String token, String mensaje, String foto) {
        this.token = token;
        this.mensaje = mensaje;
        this.foto = foto;
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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
