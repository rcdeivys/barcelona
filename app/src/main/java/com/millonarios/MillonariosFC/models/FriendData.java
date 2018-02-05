package com.millonarios.MillonariosFC.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Pedro Gomez on 19/01/2018.
 */

public class FriendData implements Parcelable {

    @SerializedName("id_amigo")
    @Expose
    private String idFriend;
    @SerializedName("bloqueado")
    @Expose
    private boolean isBlocked;
    @SerializedName("fecha_amistad")
    @Expose
    private String dateFrienship;
    @SerializedName("id_conversacion")
    @Expose
    private String idConversation = "";
    @SerializedName("foto")
    @Expose
    private String friendImage = "";
    @SerializedName("estatus")
    @Expose
    private boolean isOnline = false;
    @SerializedName("apodo")
    @Expose
    private String apodoHincha = "";

    public FriendData(String idFriend, boolean isBlocked, String dateFrienship, String idConversation,boolean isOnline,String apodo) {
        this.idFriend = idFriend;
        this.isBlocked = isBlocked;
        this.dateFrienship = dateFrienship;
        this.idConversation = idConversation;
        this.isOnline = isOnline;
        this.apodoHincha = apodo;
    }

    public FriendData(Parcel parcel){
        idFriend = parcel.readString();
        if(parcel.readInt()==1)
            isBlocked = true;
        else
            isBlocked = false;
        dateFrienship = parcel.readString();
        idConversation = parcel.readString();
        friendImage = parcel.readString();
        if(parcel.readInt()==1)
            isOnline = true;
        else
            isOnline = false;
        apodoHincha = parcel.readString();
    }

    public String getIdFriend() {
        return idFriend;
    }

    public void setIdFriend(String idFriend) {
        this.idFriend = idFriend;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public String getDateFrienship() {
        return dateFrienship;
    }

    public void setDateFrienship(String dateFrienship) {
        this.dateFrienship = dateFrienship;
    }

    public String getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(String idConversation) {
        this.idConversation = idConversation;
    }

    public String getFriendImage() {
        return friendImage;
    }

    public void setFriendImage(String friendImage) {
        this.friendImage = friendImage;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public String getApodoHincha() {
        return apodoHincha;
    }

    public void setApodoHincha(String apodoHincha) {
        this.apodoHincha = apodoHincha;
    }

    public static final Parcelable.Creator<FriendData> CREATOR =
            new Parcelable.Creator<FriendData>(){

                @Override
                public FriendData createFromParcel(Parcel parcel) {
                    return new FriendData(parcel);
                }

                @Override
                public FriendData[] newArray(int i) {
                    return new FriendData[i];
                }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idFriend);
        if(isBlocked)
            parcel.writeInt(1);
        else
            parcel.writeInt(0);
        parcel.writeString(dateFrienship);
        parcel.writeString(idConversation);
        parcel.writeString(friendImage);
        if(isOnline)
            parcel.writeInt(1);
        else
            parcel.writeInt(0);
        parcel.writeString(apodoHincha);
    }
}
