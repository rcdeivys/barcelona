package com.BarcelonaSC.BarcelonaApp.ui.chat.requests;

import com.BarcelonaSC.BarcelonaApp.models.firebase.Usuario;

/**
 * Created by Cesar on 25/01/2018.
 */

public class SuggestModelView {

    private String id;
    private String apodo;
    private String photo;
    private String nombre;
    private boolean isSend;
    private Usuario usuario;

    public SuggestModelView(String id, String apodo, String photo, String nombre , Usuario usuario , boolean isSend) {
        this.id = id;
        this.apodo = apodo;
        this.photo = photo;
        this.isSend = isSend;
        this.nombre = nombre;
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
