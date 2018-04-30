package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by makhnnar on 30/04/18.
 */

public class SendReasonCancelation {


    @SerializedName("otro_texto ")
    @Expose
    private String other;
    @SerializedName("id_razon")
    @Expose
    private String idRazon;
    @SerializedName("token")
    @Expose
    private String token ;


    public String getIdRazon() {
        return idRazon;
    }

    public void setIdRazon(String idRazon) {
        this.idRazon = idRazon;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SendReasonCancelation{" +
                "idRazon=" + idRazon +
                ", token='" + token + '\'' +
                '}';
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
