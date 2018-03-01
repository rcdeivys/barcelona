package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Pedro Gomez on 22/01/2018.
 */

public class GroupMember {

    @SerializedName("id_usuario")
    @Expose
    private String idUserMember;
    @SerializedName("nombre")
    @Expose
    private String nameUserMember;

    public GroupMember(String idUserMember, String nameUserMember) {
        this.idUserMember = idUserMember;
        this.nameUserMember = nameUserMember;
    }

    public String getIdUserMember() {
        return idUserMember;
    }

    public void setIdUserMember(String idUserMember) {
        this.idUserMember = idUserMember;
    }

    public String getNameUserMember() {
        return nameUserMember;
    }

    public void setNameUserMember(String nameUserMember) {
        this.nameUserMember = nameUserMember;
    }
}
