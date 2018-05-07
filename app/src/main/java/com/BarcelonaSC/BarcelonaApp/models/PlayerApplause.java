package com.BarcelonaSC.BarcelonaApp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Carlos on 12/10/2017.
 */

public class PlayerApplause {
    private String idjugador;
    private int idpartido;
    private String imei;
    @SerializedName("token")
    @Expose
    private String token;

    public PlayerApplause(String idjugador, int idpartido, String imei) {
        this.idjugador = idjugador;
        this.idpartido = idpartido;
        this.imei = imei;
    }

    public int getIdpartido() {
        return idpartido;
    }

    public void setIdpartido(int idpartido) {
        this.idpartido = idpartido;
    }

    public String getIdjugador() {
        return idjugador;
    }

    public void setIdjugador(String idjugador) {
        this.idjugador = idjugador;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "PlayerApplause{" +
                "idjugador='" + idjugador + '\'' +
                ", idpartido=" + idpartido +
                ", imei='" + imei + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
