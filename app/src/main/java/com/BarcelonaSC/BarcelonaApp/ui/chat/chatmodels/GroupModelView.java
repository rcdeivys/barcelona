package com.BarcelonaSC.BarcelonaApp.ui.chat.chatmodels;

/**
 * Created by Pedro Gomez on 16/01/2018.
 */

import com.BarcelonaSC.BarcelonaApp.models.firebase.Grupo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GroupModelView {

    @SerializedName("id_grupo")
    @Expose
    private String idGroup;
    @SerializedName("nombre")
    @Expose
    private String nameGroup;
    @SerializedName("foto")
    @Expose
    private String imageGroup = "";
    @SerializedName("fecha_creacion")
    @Expose
    private String creationGroup;
    @SerializedName("ultimo_mensaje")
    @Expose
    private String ultimoMensaje;
    @SerializedName("fecha_ultimo_mensaje")
    @Expose
    private Long fechaUltimoMensaje;

    //TODO: insert an array list with user names and ids ¿¿¿????
    @SerializedName("grupoUsuarios")
    @Expose
    private ArrayList<FriendsModelView> groupMembers = new ArrayList<FriendsModelView>();

    public GroupModelView(String idGroup, String nameGroup, String imageGroup, ArrayList<FriendsModelView> groupMembers) {
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.imageGroup = imageGroup;
        this.groupMembers = groupMembers;
    }

    public GroupModelView(Grupo grupo, ArrayList<FriendsModelView> groupMembers) {
        if (grupo == null)
            return;
        this.idGroup = grupo.getKey();
        this.nameGroup = grupo.getNombre();
        this.imageGroup = grupo.getFoto();
        this.groupMembers = groupMembers;
        this.ultimoMensaje = grupo.getUltimo_mensaje();
        this.fechaUltimoMensaje = grupo.getFecha_ultimo_mensaje();
    }

    public String getNameGroup() {
        return nameGroup;
    }

    public void setNameGroup(String nameGroup) {
        this.nameGroup = nameGroup;
    }

    public int getQuantityGroup() {
        return groupMembers.size();
    }

    public String getImageGroup() {
        return imageGroup;
    }

    public void setImageGroup(String imageGroup) {
        this.imageGroup = imageGroup;
    }

    public String getIdGroup() {
        return idGroup;
    }

    public void setIdGroup(String idGroup) {
        this.idGroup = idGroup;
    }

    public String getCreationGroup() {
        return creationGroup;
    }

    public void setCreationGroup(String creationGroup) {
        this.creationGroup = creationGroup;
    }

    public ArrayList<FriendsModelView> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(ArrayList<FriendsModelView> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public String getUltimoMensaje() {
        return ultimoMensaje;
    }

    public Long getFechaUltimoMensaje() {
        return fechaUltimoMensaje;
    }
}
