package com.millonarios.MillonariosFC.ui.chat.requests;

import com.millonarios.MillonariosFC.models.firebase.Miembro;
import com.millonarios.MillonariosFC.models.firebase.Usuario;

/**
 * Created by Cesar on 25/01/2018.
 */

public class RequestModelView {

    private Long id;
    private String apodo;
    private String nombre;
    private String photo;
    private String type;
    private boolean isSend;


    public RequestModelView(Long id) {
        this.id = id;

    }

    public RequestModelView(Long id, String apodo, String photo, String nombre, boolean isSend) {
        this.id = id;
        this.apodo = apodo;
        this.photo = photo;
        this.isSend = isSend;
        this.nombre = nombre;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApodo() {
        if (apodo == null)
            return nombre;
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

    public boolean isSend() {
        return isSend;
    }

    public void setSend(boolean send) {
        isSend = send;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
