package com.losingtimeapps.whitebrand.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 15/01/2018.
 */

public class SendChooseVote {


    @SerializedName("idencuesta")
    @Expose
    private int idEncuesta;
    @SerializedName("idrespuesta")
    @Expose
    private int idRespuesta;
    @SerializedName("token")
    @Expose
    private String token = null;

    public int getIdEncuesta() {
        return idEncuesta;
    }

    public void setIdEncuesta(int idEncuesta) {
        this.idEncuesta = idEncuesta;
    }

    public int getIdRespuesta() {
        return idRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        this.idRespuesta = idRespuesta;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "SendChooseVote{" +
                "idEncuesta=" + idEncuesta +
                ", idRespuesta=" + idRespuesta +
                ", token='" + token + '\'' +
                '}';
    }
}
