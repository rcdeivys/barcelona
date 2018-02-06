package com.BarcelonaSC.BarcelonaApp.ui.chat.groups;

/**
 * Created by Pedro Gomez on 16/01/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.BarcelonaSC.BarcelonaApp.ui.chat.friends.FriendsModelView;

import java.util.ArrayList;

public class GroupModelView{

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

    //TODO: insert an array list with user names and ids ¿¿¿????
    @SerializedName("grupoUsuarios")
    @Expose
    private ArrayList<FriendsModelView> groupMembers = new ArrayList<FriendsModelView>();

    public GroupModelView(String idGroup,String nameGroup, String imageGroup, ArrayList<FriendsModelView> groupMembers){
        this.idGroup = idGroup;
        this.nameGroup = nameGroup;
        this.imageGroup = imageGroup;
        this.groupMembers = groupMembers;
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
}
